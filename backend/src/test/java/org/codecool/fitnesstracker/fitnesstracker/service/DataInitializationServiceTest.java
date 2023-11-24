package org.codecool.fitnesstracker.fitnesstracker.service;
import org.codecool.fitnesstracker.fitnesstracker.dao.model.ActivityType;
import org.codecool.fitnesstracker.fitnesstracker.repositories.ActivityTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class DataInitializationServiceTest {
    @Mock
    private ActivityTypeRepository activityTypeRepository;

    @Captor
    ArgumentCaptor<List<ActivityType>> activityCaptor;


    private DataInitializationService dataInitializationService;

    private String inputFile;

//    @BeforeEach
//    void setUp() {
//        dataInitializationService = new DataInitializationService(activityTypeRepository, inputFile);
//
//    }

    @Test
    void initDataFromJsonFile_Success() throws IOException {
        inputFile = "ActivityTypes.json";
        dataInitializationService = new DataInitializationService(activityTypeRepository, inputFile);
        dataInitializationService.initDataFromJsonFile();

        Mockito.verify(activityTypeRepository, Mockito.times(1)).saveAll(activityCaptor.capture());
        List<ActivityType> value = activityCaptor.getValue();
        List<ActivityType> expectedList = new ArrayList<>();
        expectedList.add(new ActivityType(null, "running", 11, null));
        expectedList.add(new ActivityType(null, "swimming", 13, null));
        assertEquals(2, value.size());

        Assertions.assertIterableEquals(expectedList, value);

    }


    @Test
    void initDataFromJsonFile_FileNotFound() {

        inputFile = "at.json";
        dataInitializationService = new DataInitializationService(activityTypeRepository, inputFile);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalErr = System.err;
        System.setErr(printStream);
        dataInitializationService.initDataFromJsonFile();
        System.setErr(originalErr);
        String capturedOutput = outputStream.toString();

        assertTrue(capturedOutput.contains("File not found: " + inputFile));

    }
}
