package org.codecool.fitnesstracker.fitnesstracker.service;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.codecool.fitnesstracker.fitnesstracker.controller.dto.ActivityDTO;
import org.codecool.fitnesstracker.fitnesstracker.controller.dto.NewActivityDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ActivityServiceTest {
    ActivityService activityService;

    @Autowired
    public ActivityServiceTest(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Test
    void test_getAllActivities() {
        activityService.addNewActivity(new NewActivityDTO("Running", 2));
        List<ActivityDTO> userActivity = new ArrayList<>();
        userActivity.add(new ActivityDTO("Running", 2, LocalDateTime.now()));

        List<ActivityDTO> actual = activityService.getAllActivities();
        List<ActivityDTO> expected = userActivity;
        assertIterableEquals(expected, actual);
    }

}
