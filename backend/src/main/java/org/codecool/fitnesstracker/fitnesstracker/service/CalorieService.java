package org.codecool.fitnesstracker.fitnesstracker.service;

import org.codecool.fitnesstracker.fitnesstracker.controller.dto.CalorieDTO;
import org.codecool.fitnesstracker.fitnesstracker.controller.dto.CalorieForAnalyticsDTO;
import org.codecool.fitnesstracker.fitnesstracker.controller.dto.ReceivedNewCalorieDTO;
import org.codecool.fitnesstracker.fitnesstracker.dao.model.Calorie;
import org.codecool.fitnesstracker.fitnesstracker.dao.model.FoodType;
import org.codecool.fitnesstracker.fitnesstracker.exceptions.ZeroInputException;
import org.codecool.fitnesstracker.fitnesstracker.repositories.CalorieRepository;
import org.codecool.fitnesstracker.fitnesstracker.repositories.FoodTypeRepository;
import org.codecool.fitnesstracker.fitnesstracker.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class CalorieService {
    public static final int ONE_GRAM = 100;
    private final CalorieRepository calorieRepository;
    private final FoodTypeRepository foodTypeRepository;
    private final UserService userService;

    @Autowired
    public CalorieService(CalorieRepository calorieRepository, UserService userService, FoodTypeRepository foodTypeRepository) {
        this.calorieRepository = calorieRepository;
        this.userService = userService;
        this.foodTypeRepository = foodTypeRepository;
    }

    public List<CalorieDTO> getAllCalories(String userEmail) {
        List<Calorie> calorieList = calorieRepository.findByUserEmail(userEmail);
        List<CalorieDTO> calorieDTOS = new ArrayList<>();
        for (Calorie calorie : calorieList) {
            double allCalories = calorie.getConsumption() * calorie.getFoodType().getCalories()/ ONE_GRAM;
            double allCarbohydrate = calorie.getConsumption() * calorie.getFoodType().getCarbohydrate()/ ONE_GRAM;
            double allProtein = calorie.getConsumption() * calorie.getFoodType().getProtein()/ ONE_GRAM;
            double allFat = calorie.getConsumption() * calorie.getFoodType().getFat()/ ONE_GRAM;
            calorieDTOS.add(new CalorieDTO(calorie.getFoodType().getFoodType(), allCalories, allCarbohydrate, allProtein, allFat, calorie.getConsumption(), calorie.getMealDateTime()));
        }
        return calorieDTOS;
    }

    public void addNewMeal(ReceivedNewCalorieDTO meal, String userEmail) {
        if(meal.consumption() <= 0) {
            throw new ZeroInputException("Input must be a positive integer!");
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        User user = userService.findUserByEmail(userEmail);
        FoodType foodType = foodTypeRepository.findFoodTypeByApiId(meal.apiId());
        Calorie newCalorie = new Calorie(foodType, meal.consumption(), localDateTime, user);
        calorieRepository.save(newCalorie);
        System.out.println("added new meal");
    }

    public List<CalorieForAnalyticsDTO> getCalorieFromDate(LocalDate startingDate, User user) {
        LocalTime startTime = LocalTime.MIDNIGHT;
        LocalDateTime startOfDay = LocalDateTime.of(startingDate, startTime);
        List<Calorie> calories = calorieRepository.findByUserAndMealDateTimeAfter(user, startOfDay);

        return calories.stream()
                .map(calorie -> new CalorieForAnalyticsDTO(calorie.getConsumption() * calorie.getFoodType().getCalories()/100, calorie.getMealDateTime()))
                .toList();
    }

    public List<CalorieDTO> getDailyCalories(String userEmail) {
        LocalTime startTime = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now(ZoneId.of("Europe/Berlin"));
        LocalDateTime startOfDay = LocalDateTime.of(today, startTime);
        List<Calorie> calories = calorieRepository.findByUserEmailAndMealDateTimeAfter(userEmail, startOfDay);

        List<CalorieDTO> calorieDTOS = new ArrayList<>();
        for (Calorie calorie : calories) {
            double allCalories = calorie.getConsumption() * calorie.getFoodType().getCalories()/ ONE_GRAM;
            double allCarbohydrate = calorie.getConsumption() * calorie.getFoodType().getCarbohydrate()/ ONE_GRAM;
            double allProtein = calorie.getConsumption() * calorie.getFoodType().getProtein()/ ONE_GRAM;
            double allFat = calorie.getConsumption() * calorie.getFoodType().getFat()/ ONE_GRAM;
            calorieDTOS.add(new CalorieDTO(calorie.getFoodType().getFoodType(), allCalories, allCarbohydrate, allProtein, allFat, calorie.getConsumption(), calorie.getMealDateTime()));
        }
        return calorieDTOS;
    }
}
