package org.codecool.fitnesstracker.fitnesstracker.service;

import org.codecool.fitnesstracker.fitnesstracker.controller.dto.FoodTypeDTO;
import org.codecool.fitnesstracker.fitnesstracker.dao.model.FoodType;
import org.codecool.fitnesstracker.fitnesstracker.repositories.FoodTypeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodTypeService {

    FoodTypeRepository foodTypeRepository;

    public List<FoodTypeDTO> getSearchedFoodType(String foodType) {
        List<FoodType> foodTypeList = foodTypeRepository.findCalorieTypeByFoodTypeIsLikeIgnoreCase(foodType);

        List<FoodTypeDTO> foodTypeDTOS = new ArrayList<>();
        for(FoodType calorieType : foodTypeList) {
            foodTypeDTOS.add(new FoodTypeDTO(calorieType.getCalories(), calorieType.getProtein(), calorieType.getCarbohydrate(), calorieType.getFat()));
        }
        return foodTypeDTOS;
    }
}
