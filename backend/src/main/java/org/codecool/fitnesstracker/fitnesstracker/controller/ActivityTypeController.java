package org.codecool.fitnesstracker.fitnesstracker.controller;

import org.codecool.fitnesstracker.fitnesstracker.controller.dto.ActivityTypeDTO;
import org.codecool.fitnesstracker.fitnesstracker.service.ActivityTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("activitytype")
public class ActivityTypeController {
    public final ActivityTypeService activityTypeService;
    @Autowired
    public ActivityTypeController(ActivityTypeService activityTypeService) {
        this.activityTypeService = activityTypeService;
    }

    @GetMapping("/search")
    public List<ActivityTypeDTO> getAllActivityTypeByName(@RequestParam String activityType) {
        return activityTypeService.getSearchedActivityType(activityType);
    }
}
