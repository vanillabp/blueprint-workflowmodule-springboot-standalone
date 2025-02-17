package blueprint.workflowmodule.standalone.usecase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

/**
 * Represents the data model for a standalone workflow aggregate. This
 * entity is mapped to the {@code STANDALONE} table in the database.
 *
 * <p>
 * The fields include:
 * <ul>
 *   <li>{@code id} — A unique identifier for each workflow instance.</li>
 *   <li>{@code wantUserTask} — A flag indicating whether the workflow
 *       should include a user task.</li>
 * </ul>
 * </p>
 *
 * @version 1.0
 */
@Entity
@Table(name = "AGGREGATE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aggregate {

    /**
     * The primary key for the {@code AGGREGATE} table.
     */
    @Id
    private String Id;

    /**
     * Indicates whether this workflow instance should include a user task.
     */
    @Column
    private boolean wantUserTask;
}
