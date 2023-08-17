package org.codecool.fitnesstracker.fitnesstracker.service.analytics;

import org.codecool.fitnesstracker.fitnesstracker.controller.dto.AnalyticDailyDTO;
import org.codecool.fitnesstracker.fitnesstracker.dao.model.User;
import org.codecool.fitnesstracker.fitnesstracker.service.CalorieService;

import java.time.LocalDate;
import java.util.List;

public class WeeklyAnalytics implements AnalyticDuration {
    private static final String DURATION = "weekly";
    @Override
    public String getDuration() {
        return DURATION;
    }

    @Override
    public LocalDate getStartingDate() {
        return LocalDate.now();
    }

    @Override
    public List<AnalyticDailyDTO> getAnalytics(CalorieService calorieService, int userBaseLineCalorieRequirement, User user) {
        return null;
    }
}
