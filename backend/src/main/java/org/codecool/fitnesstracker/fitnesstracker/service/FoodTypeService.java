package org.codecool.fitnesstracker.fitnesstracker.service;

import org.codecool.fitnesstracker.fitnesstracker.controller.dto.FoodTypeDTO;
import org.codecool.fitnesstracker.fitnesstracker.dao.model.FoodType;
import org.codecool.fitnesstracker.fitnesstracker.data.FoodTypeInfo;
import org.codecool.fitnesstracker.fitnesstracker.exceptions.NoSuchFoodTypeException;
import org.codecool.fitnesstracker.fitnesstracker.repositories.FoodTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodTypeService {
    public static final char PERCENTAGE_SYMBOL = '%';
    private final FoodTypeRepository foodTypeRepository;
    private final SpoonApiService spoonApiService;

    @Autowired
    public FoodTypeService(FoodTypeRepository foodTypeRepository, SpoonApiService spoonApiService) {
        this.foodTypeRepository = foodTypeRepository;
        this.spoonApiService = spoonApiService;
    }
    @Transactional
    public List<FoodTypeDTO> getSearchedFoodType(String foodType) {
        List<FoodTypeDTO> foodTypeDTOS = new ArrayList<>();
        List<FoodType> foodTypeList;
        foodTypeList = foodTypeRepository.findCalorieTypeByFoodTypeIsLikeIgnoreCase(foodType + PERCENTAGE_SYMBOL);
        if(foodTypeList.size() == 0) {
            Optional<List<FoodTypeInfo>> optionalFoodTypeInfos = spoonApiService.getSearchedFoodTypeFromApi(foodType);
            if(optionalFoodTypeInfos.isEmpty()) {
                throw new NoSuchFoodTypeException("There is no result with this foodType");
            } else {
                foodTypeDTOS = optionalFoodTypeInfos.get().stream().map(foodTypeInfos -> new FoodTypeDTO(foodTypeInfos.name(),
                        foodTypeInfos.nutrition().nutrients().stream().filter(nutrients -> nutrients.name().equals("Calories")).findFirst().map(FoodTypeInfo.Nutrition.Nutrients::amount).orElse(0d),
                        foodTypeInfos.nutrition().nutrients().stream().filter(nutrients -> nutrients.name().equals("Protein")).findFirst().map(FoodTypeInfo.Nutrition.Nutrients::amount).orElse(0d),
                        foodTypeInfos.nutrition().nutrients().stream().filter(nutrients -> nutrients.name().equals("Carbohydrates")).findFirst().map(FoodTypeInfo.Nutrition.Nutrients::amount).orElse(0d),
                        foodTypeInfos.nutrition().nutrients().stream().filter(nutrients -> nutrients.name().equals("Fat")).findFirst().map(FoodTypeInfo.Nutrition.Nutrients::amount).orElse(0d),
                        foodTypeInfos.id()
                        ))
                        .collect(Collectors.toList());

                for (FoodTypeDTO foodTypeDTO : foodTypeDTOS) {
                    FoodType foodTypeEntity = new FoodType(
                            foodTypeDTO.name(),
                            foodTypeDTO.calorie(),
                            foodTypeDTO.protein(),
                            foodTypeDTO.carbohydrate(),
                            foodTypeDTO.fat(),
                            foodTypeDTO.apiId()
                    );

                    foodTypeRepository.save(foodTypeEntity);
                }

            }
        }

        for(FoodType calorieType : foodTypeList) {
            foodTypeDTOS.add(new FoodTypeDTO(calorieType.getFoodType(),  calorieType.getCalories(), calorieType.getProtein(), calorieType.getCarbohydrate(), calorieType.getFat(), calorieType.getApiId()));
        }
        return foodTypeDTOS;
    }
}
