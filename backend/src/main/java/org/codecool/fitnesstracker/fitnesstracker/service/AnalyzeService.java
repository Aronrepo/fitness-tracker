package org.codecool.fitnesstracker.fitnesstracker.service;

import org.codecool.fitnesstracker.fitnesstracker.controller.dto.AnalyticDailyDTO;
import org.codecool.fitnesstracker.fitnesstracker.dao.model.Activity;
import org.codecool.fitnesstracker.fitnesstracker.dao.model.Calorie;
import org.codecool.fitnesstracker.fitnesstracker.user.User;
import org.codecool.fitnesstracker.fitnesstracker.service.analytics.AnalyticDuration;
import org.codecool.fitnesstracker.fitnesstracker.service.analytics.DailyAnalytics;
import org.codecool.fitnesstracker.fitnesstracker.service.analytics.WeeklyAnalytics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
public class AnalyzeService {
    ActivityService activityService;
    CalorieService calorieService;
    UserService userService;

    Set<AnalyticDuration> durations;

    @Autowired
    public AnalyzeService(ActivityService activityService, CalorieService calorieService, UserService userService) {
        this.activityService = activityService;
        this.calorieService = calorieService;
        this.userService = userService;
        durations = new HashSet<>();
        durations.add(new DailyAnalytics(calorieService, activityService));
    }

    public List<AnalyticDailyDTO> listAnalyticForPeriod(String duration, String userEmail){
        User user = userService.findUserByEmail(userEmail);
        int userBaseLineCalorieRequirement = getUserBaseLineCalorieRequirement(user);
        return durations.stream()
                .filter(analyticDuration -> analyticDuration.getDuration()
                .equals(duration))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(duration))
                .getAnalytics(userBaseLineCalorieRequirement, user);
    }

    private int getUserBaseLineCalorieRequirement(User user) {
        if (user.getHeight() == 0) {
            return 0;
        }
        int age = Period.between(user.getBirthDate(), LocalDate.now()).getYears();
        if (user.getGender().equals("Woman")) {
            return (int) Math.round(655 + (9.6 * user.getWeight()) + (1.8 * user.getHeight()) - (4.7 * age));
        } else {
            return (int) Math.round(66 + (13.7 * user.getWeight()) + (5 * user.getHeight()) - (6.8 * age));
        }
    }
}
