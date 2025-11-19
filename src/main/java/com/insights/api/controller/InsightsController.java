package com.insights.api.controller;

import com.insights.api.dto.InsightsResponse;
import com.insights.api.service.InsightsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/insights")
@RequiredArgsConstructor
public class InsightsController {

    private final InsightsService insightsService;

    @GetMapping
    public ResponseEntity<InsightsResponse> getInsights() {
        InsightsResponse insights = insightsService.generateInsights();
        return ResponseEntity.ok(insights);
    }
}