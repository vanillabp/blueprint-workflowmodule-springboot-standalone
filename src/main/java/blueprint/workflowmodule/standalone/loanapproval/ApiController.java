package blueprint.workflowmodule.standalone.loanapproval;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * A simple REST controller that provides different endpoints.
 * It demonstrates how to accept parameters for creating a new workflow instance and delegate to the {@link LoanApprovalService}.
 *
 * <p>
 * This controller uses Spring’s {@code @RestController} annotation to
 * expose a REST API, and {@code @GetMapping} to handle GET requests.
 * </p>
 *
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/loan-approval")
public class ApiController {

    /**
     * Service that orchestrates the BPMN process for standalone workflows.
     */
    @Autowired
    private LoanApprovalService service;

    /**
     * Repository for retrieving and persisting {@link Aggregate} entities.
     */
    @Autowired
    private AggregateRepository aggregateRepo;

    /**
     * Starts a new loan approval workflow.
     *
     * @return The ID of the started workflow.
     */
    @GetMapping("/start")
    public ResponseEntity<String> requestLoanApproval() throws Exception{

        final var loanRequestId = UUID.randomUUID().toString();

        service.initiateLoanApproval(loanRequestId);

        log.info("Started loan approval request: {}",loanRequestId);

        return ResponseEntity.ok(loanRequestId);
    }

    /**
     * Assesses the risk of a loan request.
     *
     * @param loanRequestId             The ID of the loan request.
     * @param taskId                    The ID of the task to be processed.
     * @param riskAcceptable            Boolean indicating whether the risk is accepted.
     * @throws NoSuchElementException   if no matching aggregate is found.
     * @return A {@code ResponseEntity} indicating the outcome of the request.
     */
    @GetMapping("/{loanRequestId}/assess-risk/{taskId}")
    public ResponseEntity<String> assessRisk(
        @PathVariable final String loanRequestId,
        @PathVariable final String taskId,
        @RequestParam final boolean riskAcceptable) {

        final var aggregate = aggregateRepo.findById(loanRequestId).orElseThrow();
        aggregate.setRiskAcceptable(riskAcceptable);

        service.completeRiskAssessment(aggregate, taskId);

        log.info("Risk assessment completed");

        return ResponseEntity.ok().build();
    }
}
