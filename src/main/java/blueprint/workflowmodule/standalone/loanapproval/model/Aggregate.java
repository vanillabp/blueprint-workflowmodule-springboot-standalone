package blueprint.workflowmodule.standalone.loanapproval.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the data model for a standalone
 * <a href="https://github.com/vanillabp/spi-for-java/blob/main/README.md#process-specific-workflow-aggregate">workflow aggregate</a>.
 * This entity is mapped to the {@code LOANAPPROVAL} table in the database.
 *
 * <p>
 * The fields include:
 * <ul>
 *   <li>{@code loanRequestId}          — A unique identifier for each loan request.</li>
 *   <li>{@code riskAcceptable}         — An indicator that shows whether the risk of lending is acceptable.</li>
 *   <li>{@code amount}                 — The amount that was requested to loan   </li>
 * </ul>
 * </p>
 *
 * @version 1.0
 */
@Document(collection = "LOANAPPROVAL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aggregate {

    /**
     * The business identifier of this use case.
     */
    @org.springframework.data.annotation.Id
    private String loanRequestId;

    /**
     * The loan size.
     */
    private Integer amount;

    /**
     * Indicates whether the risk was assessed as acceptable.
     */
    private Boolean riskAcceptable;

    /**
     * The task id for risk assessment.
     */
    private String assessRiskTaskId;

    /**
     * Used in BPMN expression to not access data of aggregate directly.
     * By using this pattern changes of properties of the aggregate
     * doe not affect workflows already started before the change.
     * Additionally, in BPMN the intention of this expression has to
     * be named explicitly e.g. '${loanRequestAccepted}' what is a
     * better documentation of the purpose.
     *
     * @return Whether the loan request was accepted
     */
    public Boolean isLoanRequestAccepted() {

        if (riskAcceptable == null) {
            return null;
        }
        return true;

    }
}
