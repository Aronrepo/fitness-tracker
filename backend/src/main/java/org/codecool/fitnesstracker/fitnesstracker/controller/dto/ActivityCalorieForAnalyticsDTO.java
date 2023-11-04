package org.codecool.fitnesstracker.fitnesstracker.controller.dto;

import java.time.LocalDateTime;

public record ActivityCalorieForAnalyticsDTO(double calorie, LocalDateTime activityTime) {
}
