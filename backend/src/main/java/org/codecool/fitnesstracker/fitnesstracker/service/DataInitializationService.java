package org.codecool.fitnesstracker.fitnesstracker.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.codecool.fitnesstracker.fitnesstracker.dao.model.ActivityType;
import org.codecool.fitnesstracker.fitnesstracker.repositories.ActivityTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class DataInitializationService {

    private ActivityTypeRepository activityTypeRepository;
    private static final String JSON_FILE_PATH = "ActivityTypes.json"; // Replace with the actual file name.


    @Autowired
    public DataInitializationService(ActivityTypeRepository activityTypeRepository) {
        this.activityTypeRepository = activityTypeRepository;
    }

    public void initDataFromJsonFile(String jsonFilePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ClassPathResource resource = new ClassPathResource(JSON_FILE_PATH);
            File file = resource.getFile();
            List<ActivityType> activityTypes = objectMapper.readValue(file, new TypeReference<>() {});
            activityTypeRepository.saveAll(activityTypes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
