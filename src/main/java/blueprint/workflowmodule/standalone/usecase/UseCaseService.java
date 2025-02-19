package blueprint.workflowmodule.standalone.usecase;

import io.vanillabp.spi.process.ProcessService;
import io.vanillabp.spi.service.BpmnProcess;
import io.vanillabp.spi.service.TaskId;
import io.vanillabp.spi.service.WorkflowService;
import io.vanillabp.spi.service.WorkflowTask;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UseCaseService is a simple demonstration of how to integrate a BPMN
 * process with the VanillaBP SPI. It manages the lifecycle of a standalone
 * workflow, starting the process and executing service and user tasks.
 *
 * <p>
 * An instance of this class is created as a Spring Service and is annotated
 * with {@code @WorkflowService}, linking it to a BPMN process with the ID
 * <em>demo</em>.
 * </p>
 *
 * @version 1.0
 */
@Service
@WorkflowService(
    workflowAggregateClass = Aggregate.class,
    bpmnProcess = @BpmnProcess(bpmnProcessId = "demo"))
@Transactional
public class UseCaseService {

    /**
     * Logger for this class, used to log workflow events and status messages.
     */
    private static final Logger log = LoggerFactory.getLogger(UseCaseService.class);

    /**
     * A reference to the {@link ProcessService} that will start and manage the
     * workflow process for the {@link Aggregate}.
     */
    @Autowired
    private ProcessService<Aggregate> service;

    /**
     * Starts the workflow process for a given ID. This method creates a new
     * {@link Aggregate} with the provided ID and a boolean flag
     * indicating whether a user task is desired. It then delegates to the
     * {@code standaloneService} to start the BPMN process.
     *
     * @param id           A unique identifier for this workflow instance.
     * @param wantUserTask If {@code true}, the BPMN process will include a user task;
     *                     otherwise it will skip that step.
     * @throws Exception If the workflow cannot be started for any reason.
     */
    public void initiateUseCase(
        final String id,
        final boolean wantUserTask) throws Exception {

        final var aggregate = new Aggregate();

        aggregate.setId(id);
        aggregate.setWantUserTask(wantUserTask);

        service.startWorkflow(aggregate);

        log.info("Workflow '{}' started", aggregate.getId());
    }

    /**
     * A BPMN service task. Invoked automatically by the workflow engine
     * when the corresponding service task is reached in the BPMN process.
     * Typically, you would include business logic here.
     */
    @WorkflowTask
    public void doService() {

        log.info("Service-Task started");

        // TODO: Implement service-task-specific business logic
    }

    /**
     * A BPMN user task. Invoked automatically by the workflow engine
     * when the corresponding user task is reached in the BPMN process.
     * In a real-world scenario, this might involve interacting with a
     * user interface or waiting for user input.
     *
     * @param taskId a unique identifier for each Task. The id is later used to complete the UserTask asynchronously
     */
    @WorkflowTask
    public void doUserTask(
        @TaskId final String taskId) {

        log.info("UserTask {} started", taskId);

        // TODO: Implement user-task-specific business logic or user interaction
    }

    /**
     * Completes a specific user task within the given aggregate's workflow.
     * <p>
     * This method is responsible for marking a user task as completed within
     * a specified aggregate's workflow. It delegates the task completion
     * to the service layer and logs the completion event.
     *
     * @param aggregate The aggregate instance that contains the workflow where the task is located.
     * @param taskId    The unique identifier of the user task to be completed.
     */
    public void completeUserTask(
        final Aggregate aggregate,
        final String taskId) {

        service.completeUserTask(aggregate, taskId);

        log.info("UserTask {} completed", taskId);
    }
}
