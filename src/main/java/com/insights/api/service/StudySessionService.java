package com.insights.api.service;

import com.insights.api.dto.StudySessionRequest;
import com.insights.api.dto.StudySessionResponse;
import com.insights.api.model.StudySession;
import com.insights.api.repository.StudySessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudySessionService {

    private final StudySessionRepository repository;

    @Transactional
    public StudySessionResponse createSession(StudySessionRequest request) {
        StudySession session = new StudySession();
        session.setSubject(request.getSubject());
        session.setTopic(request.getTopic());
        session.setMinutes(request.getMinutes());
        session.setFocusScore(request.getFocusScore());
        session.setStartTime(request.getStartTime());

        StudySession saved = repository.save(session);
        return toResponse(saved);
    }

    public List<StudySessionResponse> getAllSessions() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public StudySessionResponse getSessionById(UUID id) {
        StudySession session = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        return toResponse(session);
    }

    private StudySessionResponse toResponse(StudySession session) {
        return new StudySessionResponse(
                session.getId(),
                session.getSubject(),
                session.getTopic(),
                session.getMinutes(),
                session.getFocusScore(),
                session.getStartTime(),
                session.getEndTime(),
                session.getCreatedAt()
        );
    }
}