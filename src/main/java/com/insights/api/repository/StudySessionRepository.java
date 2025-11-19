package com.insights.api.repository;

import com.insights.api.model.StudySession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface StudySessionRepository extends JpaRepository<StudySession, UUID> {

    List<StudySession> findBySubject(String subject);

    List<StudySession> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT s.subject, SUM(s.minutes) as total FROM StudySession s GROUP BY s.subject ORDER BY total DESC")
    List<Object[]> findTotalMinutesBySubject();

    @Query("SELECT s.subject FROM StudySession s GROUP BY s.subject ORDER BY MAX(s.startTime) ASC")
    List<String> findNeglectedSubjects();
}