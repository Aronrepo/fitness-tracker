package org.codecool.fitnesstracker.fitnesstracker.controller;

import org.codecool.fitnesstracker.fitnesstracker.controller.dto.CalorieDTO;
import org.codecool.fitnesstracker.fitnesstracker.controller.dto.ReceivedNewCalorieDTO;
import org.codecool.fitnesstracker.fitnesstracker.service.CalorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("calories")
public class CalorieController {
    public final CalorieService calorieService;

    @Autowired
    public CalorieController(CalorieService calorieService) {
        this.calorieService = calorieService;
    }

    @GetMapping("/")
    public List<CalorieDTO> getAllCalories(@CurrentSecurityContext(expression = "authentication")
                                           Authentication authentication) {
        return calorieService.getAllCalories(authentication.getName());
    }

    @GetMapping("/daily")
    public List<CalorieDTO> getDailyCalories(@CurrentSecurityContext(expression = "authentication")
                                                 Authentication authentication) {
        return calorieService.getDailyCalories(authentication.getName());
    }

    @PostMapping("/")
    public ResponseEntity<ReceivedNewCalorieDTO> addNewMeal(
            @RequestBody ReceivedNewCalorieDTO meal,
            @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        calorieService.addNewMeal(meal, authentication.getName());
        return new ResponseEntity<>(meal, HttpStatus.CREATED);
    }
}
