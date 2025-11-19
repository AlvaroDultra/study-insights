package com.insights.api.controller;

import com.insights.api.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/weekly")
    public ResponseEntity<Map<String, Object>> getWeeklyStats() {
        return ResponseEntity.ok(statisticsService.getWeeklyStats());
    }

    @GetMapping("/by-subject")
    public ResponseEntity<Map<String, Integer>> getMinutesBySubject() {
        return ResponseEntity.ok(statisticsService.getMinutesBySubject());
    }

    @GetMapping("/best-time")
    public ResponseEntity<Map<String, Object>> getBestStudyTime() {
        return ResponseEntity.ok(statisticsService.getBestStudyTime());
    }
}