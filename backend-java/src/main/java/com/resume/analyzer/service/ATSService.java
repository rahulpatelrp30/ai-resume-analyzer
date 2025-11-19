package com.resume.analyzer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class ATSService {
    
    private static final Logger logger = LoggerFactory.getLogger(ATSService.class);
    
    private static final Set<String> EXPECTED_SECTIONS = new HashSet<>(Arrays.asList(
        "contact", "email", "phone", "summary", "objective", "profile",
        "experience", "work experience", "employment", "education", "degree",
        "skills", "technical skills", "certifications", "certificates"
    ));
    
    public Map<String, Double> calculateAtsScore(
            String resumeText,
            String jobDescription,
            List<String> matchedSkills,
            List<String> missingSkills
    ) {
        logger.info("🎯 Calculating ATS score...");
        
        double formatScore = calculateFormatScore(resumeText);
        double sectionScore = calculateSectionScore(resumeText);
        double keywordScore = calculateKeywordDensity(matchedSkills, missingSkills);
        
        double overallScore = (formatScore * 0.3 + sectionScore * 0.3 + keywordScore * 0.4);
        
        Map<String, Double> result = new LinkedHashMap<>();
        result.put("overall_score", Math.round(overallScore * 10.0) / 10.0);
        result.put("format_score", Math.round(formatScore * 10.0) / 10.0);
        result.put("keyword_density", Math.round(keywordScore * 10.0) / 10.0);
        result.put("section_completeness", Math.round(sectionScore * 10.0) / 10.0);
        
        logger.info("✅ ATS score calculated: {}/100", result.get("overall_score"));
        return result;
    }
    
    private double calculateFormatScore(String resumeText) {
        double score = 100.0;
        
        // Check for email
        Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
        if (!emailPattern.matcher(resumeText).find()) {
            score -= 15;
        }
        
        // Check for phone
        Pattern phonePattern = Pattern.compile("\\+?\\d{1,3}?[-.\\s]?\\(?\\d{1,4}\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}");
        if (!phonePattern.matcher(resumeText).find()) {
            score -= 15;
        }
        
        // Check for dates (indicates structured experience)
        Pattern datePattern = Pattern.compile("(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec|January|February|March|April|May|June|July|August|September|October|November|December)\\s+\\d{4}");
        if (!datePattern.matcher(resumeText).find()) {
            score -= 10;
        }
        
        return Math.max(0, score);
    }
    
    private double calculateSectionScore(String resumeText) {
        String lowerText = resumeText.toLowerCase();
        int foundSections = 0;
        int criticalSections = 0;
        
        // Critical sections
        String[] critical = {"experience", "education", "skills"};
        for (String section : critical) {
            if (lowerText.contains(section)) {
                criticalSections++;
            }
        }
        
        // All expected sections
        for (String section : EXPECTED_SECTIONS) {
            if (lowerText.contains(section)) {
                foundSections++;
            }
        }
        
        // Critical sections are mandatory
        double criticalScore = (criticalSections * 100.0) / critical.length;
        double generalScore = (foundSections * 100.0) / EXPECTED_SECTIONS.size();
        
        return (criticalScore * 0.7 + generalScore * 0.3);
    }
    
    private double calculateKeywordDensity(List<String> matchedSkills, List<String> missingSkills) {
        int totalSkills = matchedSkills.size() + missingSkills.size();
        if (totalSkills == 0) {
            return 0.0;
        }
        
        return (matchedSkills.size() * 100.0) / totalSkills;
    }
}
