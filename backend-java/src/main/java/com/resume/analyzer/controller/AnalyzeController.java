package com.resume.analyzer.controller;

import com.resume.analyzer.model.AnalysisResponse;
import com.resume.analyzer.model.HealthResponse;
import com.resume.analyzer.model.LLMAnalysis;
import com.resume.analyzer.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class AnalyzeController {
    
    private static final Logger logger = LoggerFactory.getLogger(AnalyzeController.class);
    
    @Autowired
    private PDFService pdfService;
    
    @Autowired
    private SkillsService skillsService;
    
    @Autowired
    private ATSService atsService;
    
    @Autowired
    private OpenAIService openAIService;
    
    @Autowired
    private LLMAnalyzerService llmAnalyzerService;
    
    @GetMapping("/health")
    public ResponseEntity<HealthResponse> health() {
        HealthResponse response = HealthResponse.builder()
            .status("healthy")
            .service("Resume Analyzer API (Java Spring Boot)")
            .openaiConfigured(openAIService.isAvailable())
            .build();
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/analyze")
    public ResponseEntity<AnalysisResponse> analyze(
            @RequestParam("resume") MultipartFile resume,
            @RequestParam("job_description") String jobDescription
    ) {
        logger.info("📥 Received analysis request - Resume: {}, Job Description length: {}", 
                   resume.getOriginalFilename(), jobDescription.length());
        
        try {
            // Step 1: Validate file
            pdfService.validateFile(resume);
            
            // Step 2: Extract text from PDF
            String resumeText = pdfService.extractText(resume);
            logger.info("✅ Extracted {} characters from resume", resumeText.length());
            
            // Step 3: Extract skills
            Set<String> resumeSkills = skillsService.extractSkills(resumeText);
            Set<String> jobSkills = skillsService.extractSkills(jobDescription);
            
            // Step 4: Calculate matched and missing skills
            Set<String> matchedSkills = new HashSet<>(resumeSkills);
            matchedSkills.retainAll(jobSkills);
            
            Set<String> missingSkills = new HashSet<>(jobSkills);
            missingSkills.removeAll(resumeSkills);
            
            List<String> matchedList = new ArrayList<>(matchedSkills);
            List<String> missingList = new ArrayList<>(missingSkills);
            
            logger.info("📊 Skills - Matched: {}, Missing: {}", matchedList.size(), missingList.size());
            
            // Step 5: Calculate keyword score
            double keywordScore = skillsService.calculateKeywordScore(matchedSkills, jobSkills);
            
            // Step 6: Calculate semantic score (if OpenAI available)
            double semanticScore = 0.0;
            if (openAIService.isAvailable()) {
                List<Double> resumeEmbedding = openAIService.getEmbedding(resumeText);
                List<Double> jobEmbedding = openAIService.getEmbedding(jobDescription);
                
                if (resumeEmbedding != null && jobEmbedding != null) {
                    semanticScore = skillsService.cosineSimilarity(resumeEmbedding, jobEmbedding) * 100;
                    logger.info("🔍 Semantic similarity: {}", semanticScore);
                }
            }
            
            // Step 7: Calculate base match score
            double baseMatchScore = (keywordScore * 0.6 + semanticScore * 0.4);
            
            // Step 8: Get LLM analysis and adjusted score
            LLMAnalysis llmAnalysis = llmAnalyzerService.analyzeWithContext(
                resumeText, jobDescription, baseMatchScore,
                matchedList, missingList, null
            );
            
            // Step 9: Calculate ATS score
            Map<String, Double> atsScore = atsService.calculateAtsScore(
                resumeText, jobDescription, matchedList, missingList
            );
            
            // Step 10: Final match score (blend base + LLM insights)
            double finalMatchScore = Math.round(baseMatchScore * 10.0) / 10.0;
            
            // Step 11: Build response
            AnalysisResponse response = AnalysisResponse.builder()
                .matchScore(finalMatchScore)
                .keywordScore(Math.round(keywordScore * 10.0) / 10.0)
                .semanticScore(Math.round(semanticScore * 10.0) / 10.0)
                .matchedSkills(matchedList)
                .missingSkills(missingList)
                .totalResumeSkills(resumeSkills.size())
                .totalJobSkills(jobSkills.size())
                .improvementAdvice(generateImprovementAdvice(matchedList, missingList))
                .resumeFilename(resume.getOriginalFilename())
                .atsScore(atsScore)
                .llmAnalysis(llmAnalysis)
                .scoreReasoning(llmAnalysis.getMatchScoreReasoning())
                .build();
            
            logger.info("✅ Analysis complete - Match Score: {}/100", finalMatchScore);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("❌ Analysis failed", e);
            throw e;
        }
    }
    
    private String generateImprovementAdvice(List<String> matched, List<String> missing) {
        StringBuilder advice = new StringBuilder();
        
        if (missing.isEmpty()) {
            advice.append("✅ Excellent! Your resume covers all key skills from the job description.\n\n");
            advice.append("Focus on:\n");
            advice.append("- Adding quantifiable achievements\n");
            advice.append("- Highlighting relevant projects\n");
            advice.append("- Ensuring ATS-friendly formatting\n");
        } else {
            advice.append("To improve your match score:\n\n");
            advice.append("1. Add these missing skills if you have them:\n   ");
            advice.append(String.join(", ", missing.subList(0, Math.min(5, missing.size()))));
            advice.append("\n\n");
            advice.append("2. Strengthen your matched skills:\n   ");
            advice.append(String.join(", ", matched.subList(0, Math.min(5, matched.size()))));
            advice.append("\n\n");
            advice.append("3. Consider taking courses or building projects in missing areas\n");
        }
        
        return advice.toString();
    }
}
