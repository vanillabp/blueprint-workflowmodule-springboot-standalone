package com.standalone.standalone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point of the Standalone application. This class
 * initializes and runs the Spring Boot application context.
 *
 * @author Torsoto
 * @version 1.0
 */
@SpringBootApplication
public class Application {

    /**
     * The main method, used to run the Spring Boot application. This
     * method delegates to {@link SpringApplication#run(Class, String[])}
     * to bootstrap the application context.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String... args) {
        new SpringApplication(Application.class).run(args);
    }
}
