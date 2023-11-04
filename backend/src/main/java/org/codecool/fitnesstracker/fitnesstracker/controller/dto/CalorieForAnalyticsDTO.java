package org.codecool.fitnesstracker.fitnesstracker.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

public record CalorieForAnalyticsDTO(double calorie, LocalDateTime consumptionTime) {
}
