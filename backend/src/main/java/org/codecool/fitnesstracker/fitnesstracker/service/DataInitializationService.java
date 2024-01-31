package org.codecool.fitnesstracker.fitnesstracker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.codecool.fitnesstracker.fitnesstracker.dao.model.ActivityType;
import org.codecool.fitnesstracker.fitnesstracker.repositories.ActivityTypeRepository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class DataInitializationService {
    private final ActivityTypeRepository activityTypeRepository;

    private final String filePath;


    public DataInitializationService(ActivityTypeRepository activityTypeRepository, String filePath) {
        this.activityTypeRepository = activityTypeRepository;
        this.filePath = filePath;
    }


    public void initDataFromJsonFile() {
        ClassLoader classLoader = DataInitializationService.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(this.filePath)) {
            if (inputStream != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                List<ActivityType> activityTypes = objectMapper.readValue(
                        inputStream,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, ActivityType.class)
                );
                activityTypeRepository.saveAll(activityTypes);

            } else {
                throw new FileNotFoundException("File not found: " + this.filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
