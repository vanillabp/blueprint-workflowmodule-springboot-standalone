package blueprint.workflowmodule.standalone.loanapproval;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

/**
 * Represents the data model for a standalone workflow aggregate. This
 * entity is mapped to the {@code LOANAPPROVAL} table in the database.
 *
 * <p>
 * The fields include:
 * <ul>
 *   <li>{@code id} — A unique identifier for each loan request.</li>
 *   <li>{@code riskAcceptable} — A flag indicating whether the loan
 *       should be accepted or not.</li>
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
     * Indicates whether this loan request should be accepted or denied.
     */
    @Column
    private boolean riskAcceptable;
}
