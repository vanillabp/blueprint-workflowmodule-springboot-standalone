package blueprint.workflowmodule.standalone.loanapproval.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * Configuration class for defining properties specific to the workflow module.
 */
@ConfigurationProperties(prefix = "loan-approval") // same as spring application name
@Getter
@Setter
public class LoanApprovalProperties {

    private int maxAmount;

}
