package org.codecool.fitnesstracker.fitnesstracker.service;

import org.codecool.fitnesstracker.fitnesstracker.controller.dto.AnalyticDailyDTO;
import org.codecool.fitnesstracker.fitnesstracker.service.analytics.AnalyticDuration;
import org.codecool.fitnesstracker.fitnesstracker.service.analytics.DailyAnalytics;
import org.codecool.fitnesstracker.fitnesstracker.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnalyzeServiceTest {

    @Mock
    private ActivityService activityService;

    @Mock
    private CalorieService calorieService;

    @Mock
    private UserService userService;

    @Mock
    private AnalyticDuration analyticDuration;

    private AnalyzeService analyzeService;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Set<AnalyticDuration> durations = Collections.singleton(analyticDuration);
        analyzeService = new AnalyzeService(activityService, calorieService, userService, durations);
    }

    @Test
    void listAnalyticForPeriod_test() {
        User user = new User();
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        user.setEmail("test@test.com");
        user.setGender("male");
        user.setWeight(70);
        user.setHeight(170);
        String duration = "daily";
        String userEmail = "test@test.com";
        int baseLineCalorie = 1651;


        when(userService.findUserByEmail(userEmail)).thenReturn(user);
        when(analyticDuration.getDuration()).thenReturn(duration);
        when(analyticDuration.getAnalytics(anyInt(), any(User.class)))
                .thenReturn(List.of(new AnalyticDailyDTO(1651, 1800, 200, LocalDate.now())));

        List<AnalyticDailyDTO> result = analyzeService.listAnalyticForPeriod(duration, userEmail);

        assertEquals(1, result.size());
        assertEquals(LocalDate.now(), result.get(0).activityDate());
        assertEquals(1651, result.get(0).requiredCalorie());
        assertEquals(1800, result.get(0).dailyCalorieConsumption());
        assertEquals(200, result.get(0).dailyActivityCalorie());

        verify(userService, times(1)).findUserByEmail(any(String.class));
        verify(analyticDuration, times(1)).getAnalytics(anyInt(), any(User.class));
    }
}