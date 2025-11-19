package com.resume.analyzer.config;

import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

@Configuration
public class OpenAIConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenAIConfig.class);
    
    @Value("${openai.api.key}")
    private String apiKey;
    
    @Bean
    public OpenAiService openAiService() {
        if (apiKey == null || apiKey.trim().isEmpty() || apiKey.contains("your-key-here")) {
            logger.warn("⚠️  OpenAI API key not configured - AI features will be limited");
            return null;
        }
        
        logger.info("✅ OpenAI service configured");
        return new OpenAiService(apiKey, Duration.ofSeconds(60));
    }
    
    @Bean
    public boolean hasOpenAiKey() {
        return apiKey != null && !apiKey.trim().isEmpty() && !apiKey.contains("your-key-here");
    }
}
