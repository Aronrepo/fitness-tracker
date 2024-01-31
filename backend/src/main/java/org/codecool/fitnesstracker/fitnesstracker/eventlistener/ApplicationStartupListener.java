package org.codecool.fitnesstracker.fitnesstracker.eventlistener;

import org.codecool.fitnesstracker.fitnesstracker.service.DataInitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ApplicationStartupListener {

    DataInitializationService dataInitializationService;
    @Autowired
    public ApplicationStartupListener(DataInitializationService dataInitializationService) {
        this.dataInitializationService = dataInitializationService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        System.out.println("hello world, I have just started up");
        dataInitializationService.initDataFromJsonFile();
    }
}
