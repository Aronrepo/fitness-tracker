package org.codecool.fitnesstracker.fitnesstracker.service;

import org.codecool.fitnesstracker.fitnesstracker.controller.dto.ActivityTypeDTO;
import org.codecool.fitnesstracker.fitnesstracker.dao.model.ActivityType;
import org.codecool.fitnesstracker.fitnesstracker.exceptions.NoSuchActivityTypeException;
import org.codecool.fitnesstracker.fitnesstracker.repositories.ActivityTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityTypeService {

    private final ActivityTypeRepository activityTypeRepository;
    @Autowired
    public ActivityTypeService(ActivityTypeRepository activityTypeRepository) {
        this.activityTypeRepository = activityTypeRepository;
    }

    public List<ActivityTypeDTO> getSearchedActivityType(String activityType) {
        List<ActivityType> activityTypes = activityTypeRepository.findActivityTypeByActivityTypeIsLikeIgnoreCase(activityType);
        if(activityTypes.size() == 0) {
            throw new NoSuchActivityTypeException("There is no result with this foodType");
        }

        return activityTypes.stream()
                .map(activityTypeInfos -> new ActivityTypeDTO(activityTypeInfos.getId(), activityTypeInfos.getActivityType(), activityTypeInfos.getCalories())
        ).toList();
    }
}
