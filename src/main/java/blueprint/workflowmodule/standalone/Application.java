package blueprint.workflowmodule.standalone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import blueprint.workflowmodule.standalone.loanapproval.config.LoanApprovalProperties;

/**
 * The main entry point of the Standalone application. This class
 * initializes and runs the Spring Boot application context.
 *
 * @version 1.0
 */
@SpringBootApplication
@EnableConfigurationProperties(LoanApprovalProperties.class)
public class Application {

    public static void main(
            String... args) {
        new SpringApplication(Application.class).run(args);
    }

}
