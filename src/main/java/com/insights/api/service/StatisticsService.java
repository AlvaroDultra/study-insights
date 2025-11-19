package com.insights.api.service;

import com.insights.api.model.StudySession;
import com.insights.api.repository.StudySessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final StudySessionRepository repository;

    public Map<String, Object> getWeeklyStats() {
        LocalDateTime weekAgo = LocalDateTime.now().minusWeeks(1);
        List<StudySession> sessions = repository.findByStartTimeBetween(weekAgo, LocalDateTime.now());

        int totalMinutes = sessions.stream().mapToInt(StudySession::getMinutes).sum();
        double avgFocus = sessions.stream().mapToInt(StudySession::getFocusScore).average().orElse(0.0);

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalMinutes", totalMinutes);
        stats.put("totalHours", totalMinutes / 60.0);
        stats.put("averageFocusScore", Math.round(avgFocus * 10) / 10.0);
        stats.put("sessionsCount", sessions.size());

        return stats;
    }

    public Map<String, Integer> getMinutesBySubject() {
        List<StudySession> sessions = repository.findAll();

        return sessions.stream()
                .collect(Collectors.groupingBy(
                        StudySession::getSubject,
                        Collectors.summingInt(StudySession::getMinutes)
                ));
    }

    public String getMostStudiedSubject() {
        Map<String, Integer> bySubject = getMinutesBySubject();

        return bySubject.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Nenhuma disciplina estudada ainda");
    }

    public String getNeglectedSubject() {
        List<String> subjects = repository.findNeglectedSubjects();
        return subjects.isEmpty() ? "Nenhuma disciplina negligenciada" : subjects.get(0);
    }

    public Map<String, Object> getBestStudyTime() {
        List<StudySession> sessions = repository.findAll();

        Map<Integer, Double> scoreByHour = sessions.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getStartTime().getHour(),
                        Collectors.averagingDouble(s -> s.getFocusScore() * s.getMinutes())
                ));

        Integer bestHour = scoreByHour.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(9);

        Map<String, Object> result = new HashMap<>();
        result.put("bestHour", bestHour);
        result.put("recommendation", String.format("Seu melhor horário de estudo é entre %dh e %dh", bestHour, bestHour + 1));

        return result;
    }
}