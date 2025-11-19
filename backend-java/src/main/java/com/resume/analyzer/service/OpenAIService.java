package com.resume.analyzer.service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.embedding.EmbeddingRequest;
import com.theokanning.openai.service.OpenAiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpenAIService {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenAIService.class);
    
    @Autowired(required = false)
    private OpenAiService openAiService;
    
    @Value("${openai.model}")
    private String model;
    
    @Value("${openai.embedding.model}")
    private String embeddingModel;
    
    @Value("${openai.max.tokens}")
    private Integer maxTokens;
    
    @Value("${openai.temperature}")
    private Double temperature;
    
    public boolean isAvailable() {
        return openAiService != null;
    }
    
    public List<Double> getEmbedding(String text) {
        if (!isAvailable()) {
            logger.warn("OpenAI service not available");
            return null;
        }
        
        try {
            // Truncate text if too long
            String truncated = text.length() > 8000 ? text.substring(0, 8000) : text;
            
            EmbeddingRequest request = EmbeddingRequest.builder()
                .model(embeddingModel)
                .input(Arrays.asList(truncated))
                .build();
            
            var result = openAiService.createEmbeddings(request);
            return result.getData().get(0).getEmbedding();
            
        } catch (Exception e) {
            logger.error("❌ Failed to get embedding", e);
            return null;
        }
    }
    
    public String generateFeedback(
            String resumeText,
            String jobDescription,
            List<String> matchedSkills,
            List<String> missingSkills
    ) {
        if (!isAvailable()) {
            return generateBasicFeedback(matchedSkills, missingSkills);
        }
        
        try {
            String prompt = buildFeedbackPrompt(resumeText, jobDescription, matchedSkills, missingSkills);
            
            ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model(model)
                .messages(Arrays.asList(
                    new ChatMessage(ChatMessageRole.SYSTEM.value(), 
                        "You are an expert resume reviewer and career advisor with 15+ years of experience."),
                    new ChatMessage(ChatMessageRole.USER.value(), prompt)
                ))
                .maxTokens(maxTokens)
                .temperature(temperature)
                .build();
            
            var completion = openAiService.createChatCompletion(request);
            return completion.getChoices().get(0).getMessage().getContent();
            
        } catch (Exception e) {
            logger.error("❌ Failed to generate AI feedback", e);
            return generateBasicFeedback(matchedSkills, missingSkills);
        }
    }
    
    private String buildFeedbackPrompt(
            String resumeText,
            String jobDescription,
            List<String> matchedSkills,
            List<String> missingSkills
    ) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Analyze this resume against the job description and provide specific, actionable feedback.\n\n");
        prompt.append("JOB DESCRIPTION:\n").append(jobDescription).append("\n\n");
        prompt.append("RESUME:\n").append(resumeText.substring(0, Math.min(resumeText.length(), 2000))).append("\n\n");
        prompt.append("MATCHED SKILLS: ").append(String.join(", ", matchedSkills)).append("\n");
        prompt.append("MISSING SKILLS: ").append(String.join(", ", missingSkills)).append("\n\n");
        prompt.append("Provide:\n");
        prompt.append("1. Overall assessment (2-3 sentences)\n");
        prompt.append("2. Top 3 strengths\n");
        prompt.append("3. Top 3 areas for improvement\n");
        prompt.append("4. Specific recommendations to increase match score\n");
        
        return prompt.toString();
    }
    
    private String generateBasicFeedback(List<String> matchedSkills, List<String> missingSkills) {
        StringBuilder feedback = new StringBuilder();
        feedback.append("RESUME ANALYSIS\n\n");
        
        feedback.append("✅ MATCHED SKILLS (").append(matchedSkills.size()).append("):\n");
        feedback.append(String.join(", ", matchedSkills)).append("\n\n");
        
        if (!missingSkills.isEmpty()) {
            feedback.append("⚠️ MISSING SKILLS (").append(missingSkills.size()).append("):\n");
            feedback.append(String.join(", ", missingSkills)).append("\n\n");
            feedback.append("RECOMMENDATIONS:\n");
            feedback.append("• Add the missing skills to your resume if you have experience with them\n");
            feedback.append("• Highlight relevant projects that demonstrate these skills\n");
            feedback.append("• Consider taking courses or certifications in key missing areas\n");
        } else {
            feedback.append("🎉 Great! Your resume includes all the key skills from the job description.\n");
        }
        
        return feedback.toString();
    }
}
