package com.insights.api.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudySessionRequest {

    @NotBlank(message = "Subject is required")
    private String subject;

    private String topic;

    @NotNull(message = "Minutes is required")
    @Positive(message = "Minutes must be positive")
    private Integer minutes;

    @NotNull(message = "Focus score is required")
    @Min(value = 1, message = "Focus score must be between 1 and 10")
    @Max(value = 10, message = "Focus score must be between 1 and 10")
    private Integer focusScore;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;
}