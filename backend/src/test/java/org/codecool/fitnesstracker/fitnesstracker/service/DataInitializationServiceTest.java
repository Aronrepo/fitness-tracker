package org.codecool.fitnesstracker.fitnesstracker.service;
import org.codecool.fitnesstracker.fitnesstracker.dao.model.ActivityType;
import org.codecool.fitnesstracker.fitnesstracker.repositories.ActivityTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class DataInitializationServiceTest {
    @Mock
    private ActivityTypeRepository activityTypeRepository;

    @Captor
    ArgumentCaptor<List<ActivityType>> activityCaptor;
    @Captor
    ArgumentCaptor<String> fileInputCaptor;



    private DataInitializationService dataInitializationService;

    private String inputFile;

    @BeforeEach
    void setUp() {
        dataInitializationService = new DataInitializationService(activityTypeRepository);

    }

    @Test
    void initDataFromJsonFile_Success() throws IOException {
        dataInitializationService.initDataFromJsonFile("ActivityTypes.json");

        Mockito.verify(activityTypeRepository, Mockito.times(1)).saveAll(activityCaptor.capture());
        List<ActivityType> value = activityCaptor.getValue();
        List<ActivityType> expectedList = new ArrayList<>();
        expectedList.add(new ActivityType(null, "running", 11, null));
        expectedList.add(new ActivityType(null, "swimming", 13, null));
        assertEquals(2, value.size());

        Assertions.assertIterableEquals(expectedList, value);
    }

    @Test
    void initDataFromJsonFile_withCorrectFile() {
        String expectedFile = "ActivityTypes.json";
        dataInitializationService.initDataFromJsonFile(expectedFile);


    }


    @Test
    void initDataFromJsonFile_FileNotFound() {

        // Arrange
        ClassLoader mockClassLoader = Mockito.mock(ClassLoader.class);
        given(mockClassLoader.getResourceAsStream(eq("ActivityTypes.json"))).willThrow(new FileNotFoundException());


        // Act and Assert
        // Use assertThrows to verify that the method throws the expected exception
        //assertThrows(FileNotFoundException.class, () -> dataInitializationService.initDataFromJsonFile());

    }
}
