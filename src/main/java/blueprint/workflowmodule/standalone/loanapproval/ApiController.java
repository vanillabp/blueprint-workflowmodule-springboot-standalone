package blueprint.workflowmodule.standalone.loanapproval;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * A simple REST controller which demonstrates how to accept parameters
 * for initiating and processing a loan approval.
 * </p>
 * <p>
 * This controller uses Spring’s {@code @RestController} annotation to
 * expose a REST API, and {@code @GetMapping} to handle GET requests.
 * </p>
 * <p>
 * This controller is not RESTful since it is aimed to be used via &quot;curl&quot;.
 * In typical applications the controller is called by web applications which
 * can easily transfer data via other methods than GET like POST, PUT or DELETE.
 * </p>
 *
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/loan-approval")
public class ApiController {

    /**
     * The loan approval service providing business functionality to
     * be triggered based on incoming REST calls.
     */
    @Autowired
    private Service service;

    /**
     * Initiate processing of a new loan approval.
     *
     * @param loanAmount The loan size.
     * @return The ID of loan request. Typically, the ID is given by the calling system but for demo
     * purposes it is generated and returned by this endpoint.
     */
    @GetMapping("/request-loan-approval")
    public ResponseEntity<String> requestLoanApproval(
            @RequestParam final int loanAmount) throws Exception {

        final var maxAmount = 10000;

        if (loanAmount > maxAmount) {
            return ResponseEntity.badRequest().build();
        }

        final var loanRequestId = UUID.randomUUID().toString();

        service.initiateLoanApproval(
                loanRequestId,
                loanAmount);

        return ResponseEntity.ok(loanRequestId);

    }

    /**
     * Assesses the risk of a loan request.
     *
     * @param loanRequestId             The ID of the loan request.
     * @param taskId                    The ID of the task to be completed.
     * @param riskIsAcceptable          Boolean indicating whether the risk is accepted.
     * @return A {@code ResponseEntity} indicating the outcome of the request.
     */
    @GetMapping("/{loanRequestId}/assess-risk/{taskId}")
    public ResponseEntity<String> assessRisk(
            @PathVariable final String loanRequestId,
            @PathVariable final String taskId,
            @RequestParam final boolean riskIsAcceptable) {

        final var taskCompleted = service.completeRiskAssessment(
                loanRequestId,
                taskId,
                riskIsAcceptable);

        if (!taskCompleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();

    }

}
