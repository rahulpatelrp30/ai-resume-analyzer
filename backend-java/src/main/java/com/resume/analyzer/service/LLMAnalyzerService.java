package com.resume.analyzer.service;

import com.resume.analyzer.model.LLMAnalysis;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LLMAnalyzerService {
    
    private static final Logger logger = LoggerFactory.getLogger(LLMAnalyzerService.class);
    
    @Autowired(required = false)
    private OpenAiService openAiService;
    
    @Value("${openai.model}")
    private String model;
    
    @Value("${openai.max.tokens}")
    private Integer maxTokens;
    
    @Value("${openai.temperature}")
    private Double temperature;
    
    public LLMAnalysis analyzeWithContext(
            String resumeText,
            String jobDescription,
            double matchScore,
            List<String> matchedSkills,
            List<String> missingSkills,
            Map<String, Double> atsScore
    ) {
        logger.info("🤖 Performing LLM analysis with few-shot learning...");
        
        if (openAiService == null) {
            return createBasicAnalysis(matchScore, matchedSkills, missingSkills, atsScore);
        }
        
        try {
            String prompt = buildFewShotPrompt(
                resumeText, jobDescription, matchScore, 
                matchedSkills, missingSkills, atsScore
            );
            
            ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model(model)
                .messages(Arrays.asList(
                    new ChatMessage(ChatMessageRole.SYSTEM.value(), getSystemPrompt()),
                    new ChatMessage(ChatMessageRole.USER.value(), prompt)
                ))
                .maxTokens(1500)
                .temperature(temperature)
                .build();
            
            var completion = openAiService.createChatCompletion(request);
            String response = completion.getChoices().get(0).getMessage().getContent();
            
            return parseAnalysisResponse(response, matchScore, matchedSkills, missingSkills);
            
        } catch (Exception e) {
            logger.error("❌ LLM analysis failed", e);
            return createBasicAnalysis(matchScore, matchedSkills, missingSkills, atsScore);
        }
    }
    
    private String getSystemPrompt() {
        return "You are an expert technical recruiter with 15+ years of experience in resume analysis " +
               "and candidate evaluation. You provide actionable, specific, and honest feedback.";
    }
    
    private String buildFewShotPrompt(
            String resumeText,
            String jobDescription,
            double matchScore,
            List<String> matchedSkills,
            List<String> missingSkills,
            Map<String, Double> atsScore
    ) {
        StringBuilder prompt = new StringBuilder();
        
        // Add few-shot examples
        prompt.append(getFewShotExamples());
        
        // Add current analysis
        prompt.append("\n\n=== ANALYZE THIS RESUME ===\n\n");
        prompt.append("JOB DESCRIPTION:\n").append(jobDescription).append("\n\n");
        prompt.append("RESUME (first 2000 chars):\n")
              .append(resumeText.substring(0, Math.min(resumeText.length(), 2000)))
              .append("\n\n");
        
        prompt.append("CURRENT METRICS:\n");
        prompt.append("- Match Score: ").append(matchScore).append("/100\n");
        prompt.append("- ATS Score: ").append(atsScore.get("overall_score")).append("/100\n");
        prompt.append("- Matched Skills (").append(matchedSkills.size()).append("): ")
              .append(String.join(", ", matchedSkills.subList(0, Math.min(10, matchedSkills.size()))))
              .append("\n");
        prompt.append("- Missing Skills (").append(missingSkills.size()).append("): ")
              .append(String.join(", ", missingSkills.subList(0, Math.min(10, missingSkills.size()))))
              .append("\n\n");
        
        prompt.append("Provide detailed analysis in this format:\n");
        prompt.append("OVERALL: [2-3 sentence assessment]\n");
        prompt.append("MATCH_REASONING: [Why this match score makes sense]\n");
        prompt.append("STRENGTHS:\n- [Strength 1]\n- [Strength 2]\n- [Strength 3]\n");
        prompt.append("IMPROVEMENTS:\n- [Critical improvement 1]\n- [Critical improvement 2]\n- [Critical improvement 3]\n");
        prompt.append("ATS_OPTIMIZATION:\nformat: [specific format tips]\n");
        prompt.append("keywords: [keyword optimization tips]\n");
        prompt.append("sections: [section completeness tips]\n");
        prompt.append("SKILL_PRIORITY:\n- [Top skill to develop]\n- [Second priority skill]\n- [Third priority skill]\n");
        prompt.append("ACTIONS:\n- [Immediate action 1]\n- [Immediate action 2]\n- [Immediate action 3]\n");
        
        return prompt.toString();
    }
    
    private String getFewShotExamples() {
        return """
            === EXAMPLE 1: HIGH MATCH ===
            Match Score: 85/100, ATS Score: 78/100
            Matched Skills: Java, Spring Boot, Microservices, Docker, Kubernetes, AWS, PostgreSQL
            Missing Skills: GraphQL, Redis
            
            ANALYSIS:
            OVERALL: Strong candidate with excellent alignment. Core competencies match perfectly, missing skills are nice-to-have.
            STRENGTHS:
            - Deep expertise in required tech stack (Java/Spring ecosystem)
            - Production microservices experience clearly demonstrated
            - Strong DevOps background (Docker, K8s, AWS)
            IMPROVEMENTS:
            - Add specific metrics to achievements (throughput, latency)
            - Include GraphQL projects if available
            - Highlight system design experience more prominently
            
            === EXAMPLE 2: MEDIUM MATCH ===
            Match Score: 58/100, ATS Score: 65/100
            Matched Skills: Python, Django, REST API
            Missing Skills: FastAPI, Celery, Redis, Docker, AWS, Microservices
            
            ANALYSIS:
            OVERALL: Moderate fit. Has Python foundation but lacks modern deployment skills. Significant skill gaps in infrastructure.
            STRENGTHS:
            - Solid Python programming experience
            - REST API development background
            - Django framework knowledge
            IMPROVEMENTS:
            - Learn containerization (Docker) immediately - critical for role
            - Add cloud deployment experience (AWS/Azure)
            - Study microservices architecture patterns
            
            === EXAMPLE 3: LOW MATCH ===
            Match Score: 32/100, ATS Score: 45/100
            Matched Skills: Git, Agile
            Missing Skills: Java, Spring Boot, Microservices, Kafka, Docker, Kubernetes, AWS, PostgreSQL
            
            ANALYSIS:
            OVERALL: Significant skill gap. Resume lacks most technical requirements. Not ready for this role without substantial upskilling.
            STRENGTHS:
            - Familiar with version control (Git)
            - Agile methodology experience
            - Shows learning willingness
            IMPROVEMENTS:
            - Focus on Java/Spring Boot mastery (6-12 months minimum)
            - Build portfolio with microservices projects
            - Get AWS certification to demonstrate cloud capability
            """;
    }
    
    private LLMAnalysis parseAnalysisResponse(
            String response,
            double matchScore,
            List<String> matchedSkills,
            List<String> missingSkills
    ) {
        LLMAnalysis.LLMAnalysisBuilder builder = LLMAnalysis.builder();
        
        try {
            Map<String, String> sections = extractSections(response);
            
            builder.overallAssessment(sections.getOrDefault("OVERALL", "Analysis completed."));
            builder.matchScoreReasoning(sections.getOrDefault("MATCH_REASONING", 
                "Match score based on skill overlap and experience relevance."));
            
            builder.strengths(extractListItems(sections.getOrDefault("STRENGTHS", "")));
            builder.criticalImprovements(extractListItems(sections.getOrDefault("IMPROVEMENTS", "")));
            builder.skillDevelopmentPriority(extractListItems(sections.getOrDefault("SKILL_PRIORITY", "")));
            builder.immediateActions(extractListItems(sections.getOrDefault("ACTIONS", "")));
            
            Map<String, String> atsOpt = new LinkedHashMap<>();
            String atsSection = sections.getOrDefault("ATS_OPTIMIZATION", "");
            if (atsSection.contains("format:")) {
                String[] parts = atsSection.split("keywords:");
                atsOpt.put("format", parts[0].replace("format:", "").trim());
                if (parts.length > 1) {
                    String[] remaining = parts[1].split("sections:");
                    atsOpt.put("keywords", remaining[0].trim());
                    if (remaining.length > 1) {
                        atsOpt.put("sections", remaining[1].trim());
                    }
                }
            }
            builder.atsOptimization(atsOpt);
            
        } catch (Exception e) {
            logger.warn("⚠️ Failed to parse LLM response, using defaults", e);
            return createBasicAnalysis(matchScore, matchedSkills, missingSkills, null);
        }
        
        return builder.build();
    }
    
    private Map<String, String> extractSections(String response) {
        Map<String, String> sections = new HashMap<>();
        String[] lines = response.split("\n");
        String currentSection = null;
        StringBuilder content = new StringBuilder();
        
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.endsWith(":") && trimmed.matches("^[A-Z_]+:$")) {
                if (currentSection != null) {
                    sections.put(currentSection, content.toString().trim());
                }
                currentSection = trimmed.substring(0, trimmed.length() - 1);
                content = new StringBuilder();
            } else if (currentSection != null) {
                content.append(line).append("\n");
            }
        }
        
        if (currentSection != null) {
            sections.put(currentSection, content.toString().trim());
        }
        
        return sections;
    }
    
    private List<String> extractListItems(String text) {
        List<String> items = new ArrayList<>();
        String[] lines = text.split("\n");
        
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.startsWith("-") || trimmed.startsWith("•") || trimmed.startsWith("*")) {
                items.add(trimmed.substring(1).trim());
            } else if (!trimmed.isEmpty() && items.isEmpty()) {
                items.add(trimmed);
            }
        }
        
        return items.isEmpty() ? Arrays.asList("No specific items identified") : items;
    }
    
    private LLMAnalysis createBasicAnalysis(
            double matchScore,
            List<String> matchedSkills,
            List<String> missingSkills,
            Map<String, Double> atsScore
    ) {
        String assessment;
        List<String> improvements;
        
        if (matchScore >= 70) {
            assessment = "Strong match. Your resume aligns well with the job requirements.";
            improvements = Arrays.asList(
                "Add quantifiable achievements to demonstrate impact",
                "Ensure all matched skills are prominently featured",
                "Consider adding projects showcasing the missing skills"
            );
        } else if (matchScore >= 50) {
            assessment = "Moderate match. You have a foundation but significant skill gaps exist.";
            improvements = Arrays.asList(
                "Focus on developing the missing critical skills",
                "Add relevant projects or certifications to fill gaps",
                "Restructure resume to highlight matched skills first"
            );
        } else {
            assessment = "Limited match. Consider building more relevant experience before applying.";
            improvements = Arrays.asList(
                "Invest time in learning the key missing technologies",
                "Build portfolio projects demonstrating required skills",
                "Consider junior or training roles to gain experience"
            );
        }
        
        return LLMAnalysis.builder()
            .overallAssessment(assessment)
            .matchScoreReasoning("Based on " + matchedSkills.size() + " matched skills out of " + 
                                (matchedSkills.size() + missingSkills.size()) + " total job requirements.")
            .strengths(matchedSkills.subList(0, Math.min(3, matchedSkills.size())))
            .criticalImprovements(improvements)
            .atsOptimization(Map.of(
                "format", "Ensure contact info is clear and resume is ATS-friendly",
                "keywords", "Include more job description keywords naturally",
                "sections", "Add standard sections: Summary, Experience, Education, Skills"
            ))
            .skillDevelopmentPriority(missingSkills.subList(0, Math.min(3, missingSkills.size())))
            .immediateActions(Arrays.asList(
                "Update resume with missing skills if you have them",
                "Quantify achievements with metrics",
                "Tailor resume to match job description keywords"
            ))
            .build();
    }
}
