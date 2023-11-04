package org.codecool.fitnesstracker.fitnesstracker.service;

import org.codecool.fitnesstracker.fitnesstracker.controller.dto.ActivityCalorieForAnalyticsDTO;
import org.codecool.fitnesstracker.fitnesstracker.controller.dto.ActivityDTO;
import org.codecool.fitnesstracker.fitnesstracker.controller.dto.NewActivityDTO;
import org.codecool.fitnesstracker.fitnesstracker.dao.model.Activity;
import org.codecool.fitnesstracker.fitnesstracker.dao.model.ActivityType;
import org.codecool.fitnesstracker.fitnesstracker.dao.model.Calorie;
import org.codecool.fitnesstracker.fitnesstracker.exceptions.ZeroInputException;
import org.codecool.fitnesstracker.fitnesstracker.repositories.ActivityTypeRepository;
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
    private final ActivityRepository activityRepository;

    private final ActivityTypeRepository activityTypeRepository;

    UserService userService;
    @Autowired
    public ActivityService(ActivityRepository activityRepository, ActivityTypeRepository activityTypeRepository, UserService userService) {
        this.activityRepository = activityRepository;
        this.activityTypeRepository = activityTypeRepository;
        this.userService = userService;
    }


    public List<ActivityDTO> getAllActivities(String userEmail) {
        List<Activity> activityList = activityRepository.findByUserEmail(userEmail);
        List<ActivityDTO> activityDTOS = new ArrayList<>();
        for (Activity activity : activityList) {
            activityDTOS.add(new ActivityDTO(activity.getActivityType().getActivityType(), activity.getMinutesOfExercise(), activity.getActivityType().getCalories() * activity.getMinutesOfExercise(), activity.getActivityDateTime()));
        }
        return activityDTOS;
    }

    public void addNewActivity(NewActivityDTO activity, String userEmail) {
        if(activity.duration() <= 0) {
            throw new ZeroInputException("Input must be a positive integer!");
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        User user = userService.findUserByEmail(userEmail);
        ActivityType activityType = activityTypeRepository.findActivityTypeById(activity.activityTypeId());
        Activity newActivity = new Activity(activityType, activity.duration(), localDateTime, user);
        activityRepository.save(newActivity);
        System.out.println("Added a new activity!");
    }

    public List<ActivityCalorieForAnalyticsDTO> getActivityFromDate(LocalDate startingDate, User user) {
        LocalTime startTime = LocalTime.MIDNIGHT;
        LocalDateTime startOfDay = LocalDateTime.of(startingDate, startTime);
        List<Activity> activities = activityRepository.findByUserAndActivityDateTimeAfter(user, startOfDay);

        return activities.stream()
                .map(activity -> new ActivityCalorieForAnalyticsDTO(activity.getMinutesOfExercise() * activity.getActivityType().getCalories(), activity.getActivityDateTime()))
                .toList();
    }
}
