package blueprint.workflowmodule.standalone.loanapproval.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import io.vanillabp.springboot.modules.WorkflowModuleIdAwareProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * Configuration class for defining properties specific to the aggregate workflow module.
 * Implements {@link WorkflowModuleIdAwareProperties} to provide the workflow module ID.
 */
@Configuration
@ConfigurationProperties(prefix = "loan-approval") // same as spring application name
@Getter
@Setter
public class LoanApprovalProperties {

    private int maxAmount;

}
