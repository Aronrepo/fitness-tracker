package org.codecool.fitnesstracker.fitnesstracker.service;

import org.codecool.fitnesstracker.fitnesstracker.controller.dto.ActivityDTO;
import org.codecool.fitnesstracker.fitnesstracker.controller.dto.NewActivityDTO;
import org.codecool.fitnesstracker.fitnesstracker.dao.model.Activity;
import org.codecool.fitnesstracker.fitnesstracker.dao.model.ActivityType;
import org.codecool.fitnesstracker.fitnesstracker.exceptions.ZeroInputException;
import org.codecool.fitnesstracker.fitnesstracker.repositories.ActivityRepository;
import org.codecool.fitnesstracker.fitnesstracker.repositories.ActivityTypeRepository;
import org.codecool.fitnesstracker.fitnesstracker.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActivityServiceTest {

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private ActivityTypeRepository activityTypeRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private ActivityService activityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_getAllActivities() {
        String userEmail = "test@example.com";
        User user = new User();
        user.setEmail(userEmail);

        List<Activity> mockedActivities = new ArrayList<>();
        mockedActivities.add(new Activity(new ActivityType(), 30, LocalDateTime.now(), user));

        when(activityRepository.findByUserEmail(userEmail)).thenReturn(mockedActivities);

        List<ActivityDTO> result = activityService.getAllActivities(userEmail);

        assertEquals(1, result.size());
        assertEquals(30, result.get(0).activityDuration());

        verify(activityRepository, times(1)).findByUserEmail(any(String.class));

    }

    @Test
    void testAddNewActivity() {
        NewActivityDTO newActivityDTO = new NewActivityDTO(1L, 60);
        String userEmail = "test@example.com";
        User user = new User();
        user.setEmail(userEmail);

        when(userService.findUserByEmail(userEmail)).thenReturn(user);
        when(activityTypeRepository.findActivityTypeById(1L)).thenReturn(new ActivityType());
        when(activityRepository.save(new Activity())).thenReturn(new Activity());

        assertDoesNotThrow(() -> activityService.addNewActivity(newActivityDTO, userEmail));
    }

    @Test
    void testAddNewActivityWithZeroDuration() {
        // Mocking data
        NewActivityDTO newActivityDTO = new NewActivityDTO(1L, 0);
        String userEmail = "test@example.com";

        // Calling the method to test and expecting an exception
        assertThrows(ZeroInputException.class, () -> activityService.addNewActivity(newActivityDTO, userEmail));
    }

}