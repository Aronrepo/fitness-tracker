package org.codecool.fitnesstracker.fitnesstracker.controller.dto;

import java.time.LocalDate;

public record AnalyticDailyDTO(int requiredCalorie, int dailyCalorieConsumption, LocalDate activityDate) {
}
