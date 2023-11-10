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
import java.io.InputStream;
import java.util.List;

@Service
public class DataInitializationService {
    private ActivityTypeRepository activityTypeRepository;
    private static final String JSON_FILE_PATH = "ActivityTypes.json";


    @Autowired
    public DataInitializationService(ActivityTypeRepository activityTypeRepository) {
        this.activityTypeRepository = activityTypeRepository;
    }

    public void initDataFromJsonFile() {
        ClassLoader classLoader = DataInitializationService.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(JSON_FILE_PATH)) {
            if (inputStream != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                List<ActivityType> activityTypes = objectMapper.readValue(
                        inputStream,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, ActivityType.class)
                );
                activityTypeRepository.saveAll(activityTypes);

            } else {
                System.err.println("File not found: " + JSON_FILE_PATH);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
