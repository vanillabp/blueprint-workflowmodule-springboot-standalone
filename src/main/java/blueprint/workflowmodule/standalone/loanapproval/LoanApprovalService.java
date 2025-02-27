package blueprint.workflowmodule.standalone.loanapproval;

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
 * LoanApprovalService manages the lifecycle of a loan approval workflow.
 * It integrates with the BPMN process using the VanillaBP SPI,
 * handling both service and user tasks.
 *
 * <p>
 * This class is annotated as a Spring Service and is linked to a BPMN
 * process with the ID <em>loan_approval</em>.
 * </p>
 *
 * @version 1.0
 */
@Service
@WorkflowService(
    workflowAggregateClass = Aggregate.class,
    bpmnProcess = @BpmnProcess(bpmnProcessId = "loan_approval"))
@Transactional
public class LoanApprovalService {

    /**
     * Logger for this class, used to log workflow events and status messages.
     */
    private static final Logger log = LoggerFactory.getLogger(LoanApprovalService.class);

    /**
     * A reference to the {@link ProcessService} that will start and manage the
     * workflow process for the {@link Aggregate}.
     */
    @Autowired
    private ProcessService<Aggregate> service;

    /**
     * Starts the loan approval workflow.
     *
     * @param loanRequestId A unique identifier for the loan request.
     */
    public void initiateLoanApproval(
        final String loanRequestId) throws Exception {

        final var aggregate = new Aggregate();

        aggregate.setLoanRequestId(loanRequestId);

        service.startWorkflow(aggregate);

        log.info("Loan approval workflow '{}' started", aggregate.getLoanRequestId());
    }


    /**
     * Handles a BPMN user task when triggered by the workflow engine.
     *
     * @param taskId Unique identifier for the user task.
     */
    @WorkflowTask
    public void assessRisk(
        @TaskId final String taskId) {

        log.info("User Task: Assessing risk for task {}", taskId);

        // TODO: Implement user interaction or risk assessment logic
    }

    @WorkflowTask
    public void transferMoney(){

        log.info("Service task: Transferring money");

        // TODO: Implement business logic of service task
    }

    /**
     * Completes a risk assessment task based on the given decision.
     *
     * @param aggregate The aggregate instance containing the workflow.
     * @param taskId    The unique identifier of the user task.
     */
    public void completeRiskAssessment(
        final Aggregate aggregate,
        final String taskId) {

        final var riskAcceptable = aggregate.isRiskAcceptable();

        service.completeUserTask(aggregate, taskId);

        log.info("Risk assessment {} for task {}", riskAcceptable ? "accepted" : "denied", taskId);
    }
}
