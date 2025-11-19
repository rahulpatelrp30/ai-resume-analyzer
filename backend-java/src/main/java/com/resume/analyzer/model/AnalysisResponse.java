package com.resume.analyzer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisResponse {
    private Double matchScore;
    private Double keywordScore;
    private Double semanticScore;
    private List<String> matchedSkills;
    private List<String> missingSkills;
    private Integer totalResumeSkills;
    private Integer totalJobSkills;
    private String improvementAdvice;
    private String resumeFilename;
    private Map<String, Double> atsScore;
    private LLMAnalysis llmAnalysis;
    private String scoreReasoning;
}
