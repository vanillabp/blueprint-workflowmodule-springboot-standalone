package com.standalone.standalone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * A simple REST controller that provides an endpoint to start a BPMN-based
 * workflow process. It demonstrates how to accept parameters for creating
 * a new workflow instance and delegate to the {@link StandaloneWorkflow}.
 *
 * <p>
 * This controller uses Spring’s {@code @RestController} annotation to
 * expose a REST API, and {@code @GetMapping} to handle GET requests.
 * </p>
 *
 * <p><strong>Note:</strong> If you are using a path variable (i.e., {@code @PathVariable}),
 * you may want to match it in your route mapping (e.g., {@code @GetMapping("/{id}")}).
 * Otherwise, if the route is just {@code /}, you might consider removing
 * the {@code @PathVariable} annotation.
 * </p>
 *
 * @author Torsoto
 * @version 1.0
 */
@org.springframework.web.bind.annotation.RestController
public class RestController {

    /**
     * Service that orchestrates the BPMN process for standalone workflows.
     */
    @Autowired
    private StandaloneWorkflow service;

    /**
     * Repository for retrieving and persisting {@link Aggregate} entities.
     */
    @Autowired
    private AggregateRepository aggregateRepo;

    /**
     * Starts a new workflow instance based on the provided parameters.
     * It delegates the call to {@link StandaloneWorkflow#startWorkflow(String, boolean)}
     * to actually begin the BPMN process.
     *
     * @param id           A unique identifier for the workflow instance.
     * @param wantUserTask A boolean flag indicating whether the workflow
     *                     should include a user task. Defaults to {@code false}.
     * @return A {@code ResponseEntity} containing a simple greeting message
     *         upon successful start of the workflow.
     * @throws Exception   If there is any error encountered while starting
     *                     the workflow process.
     */
    @GetMapping("/")
    public ResponseEntity<String> startWorkflow(
            @PathVariable final String id,
            @RequestParam(
                    value = "wantUserTask",
                    required = false,
                    defaultValue = "false")
            final boolean wantUserTask) throws Exception {

        // Start the workflow by delegating to the service
        service.startWorkflow(id, wantUserTask);

        // Return a simple message indicating success
        return ResponseEntity.ok("Hello World");
    }
}
