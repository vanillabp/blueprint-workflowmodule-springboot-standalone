package com.standalone.standalone;

import io.vanillabp.spi.process.ProcessService;
import io.vanillabp.spi.service.BpmnProcess;
import io.vanillabp.spi.service.WorkflowService;
import io.vanillabp.spi.service.WorkflowTask;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * StandaloneWorkflow is a simple demonstration of how to integrate a BPMN
 * process with the VanillaBP SPI. It manages the lifecycle of a standalone
 * workflow, starting the process and executing service and user tasks.
 *
 * <p>
 * An instance of this class is created as a Spring Service and is annotated
 * with {@code @WorkflowService}, linking it to a BPMN process with the ID
 * <em>standalone</em>.
 * </p>
 *
 * <p>
 * Usage:
 * <ul>
 *   <li>Initialize the workflow on application startup via {@link #init()}.</li>
 *   <li>Call {@link #startWorkflow(String, boolean)} to start a new workflow instance.</li>
 *   <li>Workflow tasks are then automatically handled via {@link #doService()} and
 *   {@link #doUserTask()} as defined by the BPMN model.</li>
 * </ul>
 * </p>
 *
 * @author Torsoto
 * @version 1.0
 */
@Service
@WorkflowService(
        workflowAggregateClass = StandaloneWorkflow.class,
        bpmnProcess = @BpmnProcess(bpmnProcessId = "standalone"))
@Transactional
public class StandaloneWorkflow {

    /**
     * Logger for this class, used to log workflow events and status messages.
     */
    private static final Logger log = LoggerFactory.getLogger(StandaloneWorkflow.class);

    /**
     * A reference to the {@link ProcessService} that will start and manage the
     * workflow process for the {@link StandaloneAggregate}.
     */
    @Autowired
    private ProcessService<StandaloneAggregate> standaloneService;

    /**
     * Initializes the StandaloneWorkflow. This method is called after the
     * bean is constructed and all dependencies are injected. It logs a simple
     * message indicating that the workflow service is ready.
     */
    @PostConstruct
    public void init() {
        log.info("StandaloneWorkflow init...");
    }

    /**
     * Starts the workflow process for a given ID. This method creates a new
     * {@link StandaloneAggregate} with the provided ID and a boolean flag
     * indicating whether a user task is desired. It then delegates to the
     * {@code standaloneService} to start the BPMN process.
     *
     * @param id           A unique identifier for this workflow instance.
     * @param wantUserTask If {@code true}, the BPMN process will include a user task;
     *                     otherwise it will skip that step.
     * @throws Exception   If the workflow cannot be started for any reason.
     */
    public void startWorkflow(
            final String id,
            final boolean wantUserTask) throws Exception {

        var standaloneAggregate = new StandaloneAggregate();

        standaloneAggregate.setId(id);
        standaloneAggregate.setWantUserTask(wantUserTask);

        standaloneService.startWorkflow(standaloneAggregate);

        log.info("Standalone Workflow '{}' started", standaloneAggregate.getId());
    }

    /**
     * A BPMN service task. Invoked automatically by the workflow engine
     * when the corresponding service task is reached in the BPMN process.
     * Typically, you would include business logic here.
     */
    @WorkflowTask
    public void doService() {
        log.info("StandaloneWorkflow doService");
        // TODO: Implement service-task-specific business logic
    }

    /**
     * A BPMN user task. Invoked automatically by the workflow engine
     * when the corresponding user task is reached in the BPMN process.
     * In a real-world scenario, this might involve interacting with a
     * user interface or waiting for user input.
     */
    @WorkflowTask
    public void doUserTask() {
        log.info("StandaloneWorkflow doUserTask");
        // TODO: Implement user-task-specific business logic or user interaction
    }
}
