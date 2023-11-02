package org.codecool.fitnesstracker.fitnesstracker.service;

import org.codecool.fitnesstracker.fitnesstracker.controller.dto.ActivityCalorieForAnalyticsDTO;
import org.codecool.fitnesstracker.fitnesstracker.controller.dto.ActivityDTO;
import org.codecool.fitnesstracker.fitnesstracker.controller.dto.NewActivityDTO;
import org.codecool.fitnesstracker.fitnesstracker.dao.model.Activity;
import org.codecool.fitnesstracker.fitnesstracker.dao.model.Calorie;
import org.codecool.fitnesstracker.fitnesstracker.user.User;
import org.codecool.fitnesstracker.fitnesstracker.repositories.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityService {
    ActivityRepository activityRepository;

    UserService userService;
    @Autowired
    public ActivityService(ActivityRepository activityRepository, UserService userService) {
        this.activityRepository = activityRepository;
        this.userService = userService;
    }


    public List<ActivityDTO> getAllActivities(String userEmail) {
        List<Activity> activityList = activityRepository.findByUserEmail(userEmail);
        List<ActivityDTO> activityDTOS = new ArrayList<>();
        for (Activity activity : activityList) {
            activityDTOS.add(new ActivityDTO(activity.getActivityType(), activity.getCalories(), activity.getActivityDateTime()));
        }
        return activityDTOS;
    }

    public void addNewActivity(NewActivityDTO activity, String userEmail) {
        LocalDateTime localDateTime = LocalDateTime.now();
        User user = userService.findUserByEmail(userEmail);
        ActivityDTO addedActivity = new ActivityDTO(activity.activity(), activity.calories(), localDateTime);
        Activity newActivity = new Activity(addedActivity.activity(), addedActivity.calories(), localDateTime, user);
        activityRepository.save(newActivity);
    }

    public List<ActivityCalorieForAnalyticsDTO> getActivityFromDate(LocalDate startingDate, User user) {
        LocalTime startTime = LocalTime.MIDNIGHT;
        LocalDateTime startOfDay = LocalDateTime.of(startingDate, startTime);
        List<Activity> activities = activityRepository.findByUserAndActivityDateTimeAfter(user, startOfDay);

        return activities.stream()
                .map(activity -> new ActivityCalorieForAnalyticsDTO(activity.getCalories(), activity.getActivityDateTime()))
                .toList();
    }
}
