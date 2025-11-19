package com.insights.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsightsResponse {

    private Map<String, Object> weeklyStats;
    private Map<String, Integer> minutesBySubject;
    private String mostStudiedSubject;
    private String neglectedSubject;
    private Map<String, Object> bestStudyTime;
    private Integer productivityScore;
    private String recommendation;
}