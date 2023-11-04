package org.codecool.fitnesstracker.fitnesstracker.service.analytics;

import org.codecool.fitnesstracker.fitnesstracker.controller.dto.AnalyticDailyDTO;
import org.codecool.fitnesstracker.fitnesstracker.user.User;
import org.codecool.fitnesstracker.fitnesstracker.service.CalorieService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DailyAnalytics implements AnalyticDuration {

    private static final String DURATION = "daily";
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
        double sumDailyCalorie = calorieService.getCalorieFromDate(getStartingDate(), user).stream().mapToDouble(cal -> cal.calorie()).sum();
        List<AnalyticDailyDTO> analyticDailyDTOS = new ArrayList<>();

        analyticDailyDTOS.add(new AnalyticDailyDTO(userBaseLineCalorieRequirement, sumDailyCalorie, LocalDate.now()));
        return analyticDailyDTOS;
    }
}
