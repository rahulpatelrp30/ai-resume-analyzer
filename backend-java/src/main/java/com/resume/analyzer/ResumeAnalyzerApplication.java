package com.resume.analyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class ResumeAnalyzerApplication {
    
    private static final Logger logger = LoggerFactory.getLogger(ResumeAnalyzerApplication.class);
    
    public static void main(String[] args) {
        logger.info("🚀 Starting AI Resume Analyzer API...");
        SpringApplication.run(ResumeAnalyzerApplication.class, args);
        logger.info("✅ AI Resume Analyzer API started successfully!");
    }
}
