package com.insights.api.controller;

import com.insights.api.dto.StudySessionRequest;
import com.insights.api.dto.StudySessionResponse;
import com.insights.api.service.StudySessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class StudySessionController {

    private final StudySessionService service;

    @PostMapping
    public ResponseEntity<StudySessionResponse> createSession(@Valid @RequestBody StudySessionRequest request) {
        StudySessionResponse response = service.createSession(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<StudySessionResponse>> getAllSessions() {
        List<StudySessionResponse> sessions = service.getAllSessions();
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudySessionResponse> getSessionById(@PathVariable UUID id) {
        StudySessionResponse response = service.getSessionById(id);
        return ResponseEntity.ok(response);
    }
}