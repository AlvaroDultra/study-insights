package com.insights.api.service;

import com.insights.api.dto.InsightsResponse;
import com.insights.api.model.StudySession;
import com.insights.api.repository.StudySessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InsightsService {

    private final StatisticsService statisticsService;
    private final StudySessionRepository repository;

    public InsightsResponse generateInsights() {
        Map<String, Object> weeklyStats = statisticsService.getWeeklyStats();
        Map<String, Integer> minutesBySubject = statisticsService.getMinutesBySubject();
        String mostStudied = statisticsService.getMostStudiedSubject();
        String neglected = statisticsService.getNeglectedSubject();
        Map<String, Object> bestTime = statisticsService.getBestStudyTime();

        Integer productivityScore = calculateProductivityScore();
        String recommendation = generateRecommendation(neglected, bestTime);

        return InsightsResponse.builder()
                .weeklyStats(weeklyStats)
                .minutesBySubject(minutesBySubject)
                .mostStudiedSubject(mostStudied)
                .neglectedSubject(neglected)
                .bestStudyTime(bestTime)
                .productivityScore(productivityScore)
                .recommendation(recommendation)
                .build();
    }

    private Integer calculateProductivityScore() {
        LocalDateTime weekAgo = LocalDateTime.now().minusWeeks(1);
        List<StudySession> sessions = repository.findByStartTimeBetween(weekAgo, LocalDateTime.now());

        if (sessions.isEmpty()) return 0;

        double avgFocus = sessions.stream()
                .mapToInt(StudySession::getFocusScore)
                .average()
                .orElse(0.0);

        int totalMinutes = sessions.stream()
                .mapToInt(StudySession::getMinutes)
                .sum();

        int consistency = Math.min(sessions.size() * 10, 40);
        double timeScore = Math.min(totalMinutes / 10.0, 40);
        double focusScore = avgFocus * 2;

        return (int) Math.round(consistency + timeScore + focusScore);
    }

    private String generateRecommendation(String neglected, Map<String, Object> bestTime) {
        StringBuilder rec = new StringBuilder();

        rec.append("🔥 INSIGHTS DO DIA:\n");
        rec.append(bestTime.get("recommendation")).append("\n");

        if (!neglected.equals("Nenhuma disciplina negligenciada")) {
            rec.append("⚠️ Disciplina negligenciada: ").append(neglected).append("\n");
            rec.append("💡 Sugestão: Dedique pelo menos 30 minutos hoje para revisar.\n");
        }

        return rec.toString();
    }
}