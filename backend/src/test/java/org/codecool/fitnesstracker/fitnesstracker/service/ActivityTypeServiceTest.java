package org.codecool.fitnesstracker.fitnesstracker.service;

import org.codecool.fitnesstracker.fitnesstracker.controller.dto.ActivityTypeDTO;
import org.codecool.fitnesstracker.fitnesstracker.dao.model.ActivityType;
import org.codecool.fitnesstracker.fitnesstracker.exceptions.NoSuchActivityTypeException;
import org.codecool.fitnesstracker.fitnesstracker.repositories.ActivityTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTypeServiceTest {

    @Mock
    ActivityTypeRepository activityTypeRepository;

    @InjectMocks
    private ActivityTypeService activityTypeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void getSearchedActivityType() {
        String searchQuery = "Running";
        ActivityType activityType1 = new ActivityType(1L, "Running", 100.0, new HashSet<>());
        ActivityType activityType2 = new ActivityType(2L, "Trail Running", 120.0, new HashSet<>());

        List<ActivityType> mockedActivityTypes = Arrays.asList(activityType1, activityType2);

        when(activityTypeRepository.findActivityTypeByActivityTypeIsLikeIgnoreCase(any(String.class)))
                .thenReturn(mockedActivityTypes);

        List<ActivityTypeDTO> result = activityTypeService.getSearchedActivityType(searchQuery);

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).id());
        assertEquals("Running", result.get(0).name());
        assertEquals(100.0, result.get(0).calorie());

        assertEquals(2L, result.get(1).id());
        assertEquals("Trail Running", result.get(1).name());
        assertEquals(120.0, result.get(1).calorie());

        verify(activityTypeRepository, times(1)).findActivityTypeByActivityTypeIsLikeIgnoreCase(any(String.class));


    }

    @Test
    void testGetSearchedActivityTypeNoResult() {
        String searchQuery = "NonexistentActivity";

        when(activityTypeRepository.findActivityTypeByActivityTypeIsLikeIgnoreCase(searchQuery))
                .thenReturn(Arrays.asList());

        assertThrows(NoSuchActivityTypeException.class, () -> activityTypeService.getSearchedActivityType(searchQuery));
    }
}