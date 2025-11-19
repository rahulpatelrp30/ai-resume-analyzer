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
public class LLMAnalysis {
    private String overallAssessment;
    private String matchScoreReasoning;
    private List<String> strengths;
    private List<String> criticalImprovements;
    private Map<String, String> atsOptimization;
    private List<String> skillDevelopmentPriority;
    private List<String> immediateActions;
}
