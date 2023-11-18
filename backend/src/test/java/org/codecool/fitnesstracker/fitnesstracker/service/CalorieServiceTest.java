package org.codecool.fitnesstracker.fitnesstracker.service;

import org.codecool.fitnesstracker.fitnesstracker.controller.dto.CalorieDTO;
import org.codecool.fitnesstracker.fitnesstracker.controller.dto.NewCalorieDTO;
import org.codecool.fitnesstracker.fitnesstracker.controller.dto.ReceivedNewCalorieDTO;
import org.codecool.fitnesstracker.fitnesstracker.dao.model.Calorie;
import org.codecool.fitnesstracker.fitnesstracker.dao.model.FoodType;
import org.codecool.fitnesstracker.fitnesstracker.exceptions.ZeroInputException;
import org.codecool.fitnesstracker.fitnesstracker.repositories.CalorieRepository;
import org.codecool.fitnesstracker.fitnesstracker.repositories.FoodTypeRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CalorieServiceTest {

    @Mock
    private CalorieRepository calorieRepository;

    @Mock
    private FoodTypeRepository foodTypeRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private CalorieService calorieService;

    private String userEmail;
    private User user;

    @BeforeEach
    void setUp() {
        userEmail = "test@example.com";
        user = new User();
        user.setEmail(userEmail);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCalories_test() {
        List<Calorie> mockedCalories = new ArrayList<>();
        mockedCalories.add(new Calorie(new FoodType(), 10, LocalDateTime.now(), user));

        when(calorieRepository.findByUserEmail(any(String.class))).thenReturn(mockedCalories);

        List<CalorieDTO> result = calorieService.getAllCalories(userEmail);

        assertEquals(1, result.size());
        assertEquals(10, result.get(0).consumption());

        verify(calorieRepository, times(1)).findByUserEmail(any(String.class));

    }

    @Test
    void addNewMeal_test() {
        ReceivedNewCalorieDTO newCalorieDTO = new ReceivedNewCalorieDTO(1L, 10);

        when(userService.findUserByEmail(any(String.class))).thenReturn(user);
        when(foodTypeRepository.findFoodTypeByApiId(anyLong())).thenReturn(new FoodType());
        when(calorieRepository.save(any(Calorie.class))).thenReturn(new Calorie());

        assertDoesNotThrow(() -> calorieService.addNewMeal(newCalorieDTO, userEmail));

        verify(calorieRepository, times(1)).save(any(Calorie.class));
    }

    @Test
    void testAddNewMealWithZeroConsumption() {
        ReceivedNewCalorieDTO newCalorieDTO = new ReceivedNewCalorieDTO(1L, 0);

        assertThrows(ZeroInputException.class, () -> calorieService.addNewMeal(newCalorieDTO, userEmail));
    }

    @Test
    void getCalorieFromDate_test() {
    }

    @Test
    void getDailyCalories_test() {
    }
}