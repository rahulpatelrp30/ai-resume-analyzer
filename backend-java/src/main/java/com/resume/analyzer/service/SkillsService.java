package com.resume.analyzer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class SkillsService {
    
    private static final Logger logger = LoggerFactory.getLogger(SkillsService.class);
    
    // Common technical skills
    private static final Set<String> COMMON_SKILLS = new HashSet<>(Arrays.asList(
        // Programming Languages
        "java", "python", "javascript", "typescript", "c++", "c#", "ruby", "go", "rust", "php",
        "swift", "kotlin", "scala", "r", "matlab", "perl", "shell", "bash",
        
        // Web Technologies
        "html", "css", "react", "angular", "vue", "node.js", "express", "django", "flask",
        "spring boot", "spring", "fastapi", "asp.net", "laravel", "rails",
        
        // Databases
        "sql", "mysql", "postgresql", "mongodb", "redis", "elasticsearch", "cassandra",
        "oracle", "sql server", "dynamodb", "firebase",
        
        // Cloud & DevOps
        "aws", "azure", "gcp", "docker", "kubernetes", "jenkins", "git", "github", "gitlab",
        "ci/cd", "terraform", "ansible", "linux", "unix",
        
        // Data & AI
        "machine learning", "deep learning", "tensorflow", "pytorch", "scikit-learn",
        "pandas", "numpy", "spark", "hadoop", "kafka", "airflow",
        
        // Other
        "rest api", "graphql", "microservices", "agile", "scrum", "jira", "api",
        "testing", "junit", "selenium", "jest"
    ));
    
    public Set<String> extractSkills(String text) {
        if (text == null || text.trim().isEmpty()) {
            return new HashSet<>();
        }
        
        String lowerText = text.toLowerCase();
        Set<String> foundSkills = new HashSet<>();
        
        for (String skill : COMMON_SKILLS) {
            // Word boundary matching
            Pattern pattern = Pattern.compile("\\b" + Pattern.quote(skill) + "\\b", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(lowerText);
            
            if (matcher.find()) {
                foundSkills.add(skill);
            }
        }
        
        logger.info("🔍 Extracted {} skills", foundSkills.size());
        return foundSkills;
    }
    
    public double calculateKeywordScore(Set<String> resumeSkills, Set<String> jobSkills) {
        if (jobSkills.isEmpty()) {
            return 0.0;
        }
        
        Set<String> matched = new HashSet<>(resumeSkills);
        matched.retainAll(jobSkills);
        
        return (matched.size() * 100.0) / jobSkills.size();
    }
    
    public double cosineSimilarity(List<Double> vec1, List<Double> vec2) {
        if (vec1.size() != vec2.size()) {
            return 0.0;
        }
        
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;
        
        for (int i = 0; i < vec1.size(); i++) {
            dotProduct += vec1.get(i) * vec2.get(i);
            norm1 += vec1.get(i) * vec1.get(i);
            norm2 += vec2.get(i) * vec2.get(i);
        }
        
        if (norm1 == 0.0 || norm2 == 0.0) {
            return 0.0;
        }
        
        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }
}
