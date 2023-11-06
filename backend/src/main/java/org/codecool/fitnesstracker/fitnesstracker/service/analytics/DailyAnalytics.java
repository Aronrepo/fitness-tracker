package org.codecool.fitnesstracker.fitnesstracker.service.analytics;

import org.codecool.fitnesstracker.fitnesstracker.controller.dto.AnalyticDailyDTO;
import org.codecool.fitnesstracker.fitnesstracker.service.ActivityService;
import org.codecool.fitnesstracker.fitnesstracker.user.User;
import org.codecool.fitnesstracker.fitnesstracker.service.CalorieService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DailyAnalytics implements AnalyticDuration {

    private static final String DURATION = "daily";
    CalorieService calorieService;
    ActivityService activityService;
    public DailyAnalytics(CalorieService calorieService, ActivityService activityService) {
        this.calorieService = calorieService;
        this.activityService = activityService;
    }

    @Override
    public String getDuration() {
        return DURATION;
    }

    @Override
    public LocalDate getStartingDate() {
        return LocalDate.now();
    }

    @Override
    public List<AnalyticDailyDTO> getAnalytics(int userBaseLineCalorieRequirement, User user) {
        double sumDailyCalorie = calorieService.getCalorieFromDate(getStartingDate(), user).stream().mapToDouble(cal -> cal.calorie()).sum();
        double sumDailyActivity = activityService.getActivityFromDate(getStartingDate(), user).stream().mapToDouble(cal -> cal.calorie()).sum();
        List<AnalyticDailyDTO> analyticDailyDTOS = new ArrayList<>();

        analyticDailyDTOS.add(new AnalyticDailyDTO(userBaseLineCalorieRequirement, sumDailyCalorie, sumDailyActivity, LocalDate.now()));
        return analyticDailyDTOS;
    }
}
