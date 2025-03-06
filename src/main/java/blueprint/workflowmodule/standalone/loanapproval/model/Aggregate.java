package blueprint.workflowmodule.standalone.loanapproval.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
 *   <li>{@code loanRequestId} — A unique identifier for each loan request.</li>
 *   <li>{@code riskAcceptable} — An indicator that shows whether the risk of lending is acceptable.</li>
 * </ul>
 * </p>
 *
 * @version 1.0
 */
@Entity
@Table(name = "LOANAPPROVAL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aggregate {

    /**
     * The primary key for the {@code LOANAPPROVAL} table.
     */
    @Id
    private String loanRequestId;

    /**
     * The loan size.
     */
    @Column
    private Integer amount;

    /**
     * Indicates whether the assessed risk is acceptable.
     */
    @Column
    private Boolean riskAcceptable;

    /**
     * The task id for risk assessment.
     */
    @Column
    private String assessRiskTaskId;

}
