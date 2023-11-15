package org.codecool.fitnesstracker.fitnesstracker.service.analytics;

import org.codecool.fitnesstracker.fitnesstracker.controller.dto.ActivityCalorieForAnalyticsDTO;
import org.codecool.fitnesstracker.fitnesstracker.controller.dto.AnalyticDailyDTO;
import org.codecool.fitnesstracker.fitnesstracker.controller.dto.CalorieForAnalyticsDTO;
import org.codecool.fitnesstracker.fitnesstracker.service.ActivityService;
import org.codecool.fitnesstracker.fitnesstracker.service.CalorieService;
import org.codecool.fitnesstracker.fitnesstracker.user.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.ArgumentMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DailyAnalyticsTest {

    @Mock
    private CalorieService calorieService;

    @Mock
    private ActivityService activityService;

    @InjectMocks
    private DailyAnalytics dailyAnalytics;

    public DailyAnalyticsTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDuration() {
        assertEquals("daily", dailyAnalytics.getDuration());
    }

    @Test
    void getStartingDate() {
        assertEquals(LocalDate.now(), dailyAnalytics.getStartingDate());
    }

    @Test
    void getAnalytics() {
        int userBaseLineCalorieRequirement = 2000;

        User user = new User();
        when(calorieService.getCalorieFromDate(any(LocalDate.class), any(User.class)))
                .thenReturn(List.of(new CalorieForAnalyticsDTO(300, LocalDateTime.now())));

        when(activityService.getActivityFromDate(any(LocalDate.class), any(User.class)))
                .thenReturn(List.of(new ActivityCalorieForAnalyticsDTO(150, LocalDateTime.now())));

        List<AnalyticDailyDTO> result = dailyAnalytics.getAnalytics(userBaseLineCalorieRequirement, user);

        assertEquals(1, result.size());
        AnalyticDailyDTO analyticDailyDTO = result.get(0);
        assertEquals(userBaseLineCalorieRequirement, analyticDailyDTO.requiredCalorie());
        assertEquals(300, analyticDailyDTO.dailyCalorieConsumption());
        assertEquals(150, analyticDailyDTO.dailyActivityCalorie());
        assertEquals(LocalDate.now(), analyticDailyDTO.activityDate());

        verify(calorieService, times(1)).getCalorieFromDate(any(LocalDate.class), any(User.class));
        verify(activityService, times(1)).getActivityFromDate(any(LocalDate.class), any(User.class));




    }
}