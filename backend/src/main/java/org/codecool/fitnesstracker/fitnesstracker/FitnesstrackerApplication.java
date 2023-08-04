package org.codecool.fitnesstracker.fitnesstracker;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FitnesstrackerApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(FitnesstrackerApplication.class);
    // private DataInitializer dataInitializer;

    // @Autowired
    // public FitnesstrackerApplication(DataInitializer dataInitializer) {
    //     this.dataInitializer = dataInitializer;
    // }

    public static void main(String[] args) {
	SpringApplication.run(FitnesstrackerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // dataInitializer.init();
        LOGGER.info(String.format("Args are: %s", Arrays.toString(args)));
    }

}
