package blueprint.workflowmodule.standalone.loanapproval;

import io.vanillabp.spi.process.ProcessService;
import io.vanillabp.spi.service.BpmnProcess;
import io.vanillabp.spi.service.TaskId;
import io.vanillabp.spi.service.WorkflowService;
import io.vanillabp.spi.service.WorkflowTask;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This service manages the lifecycle of a loan approval workflow.
 * It integrates with the BPMN process using the VanillaBP SPI,
 * handling both service and user tasks.
 *
 * <p>
 * This class is annotated as a Spring Service and is linked to a BPMN
 * process with the ID <em>loan_approval</em>.
 * </p>
 *
 * @version 1.0
 * @see <a href="https://github.com/vanillabp/spi-for-java/blob/main/README.md#wire-up-a-process">VanillaBP docs &quot;Wire up a process&quot;</a>
 */
@Slf4j
@org.springframework.stereotype.Service
@WorkflowService(
    workflowAggregateClass = Aggregate.class,
    bpmnProcess = @BpmnProcess(bpmnProcessId = "loan_approval"))
@Transactional
public class Service {

    /**
     * Repository for retrieving and persisting {@link Aggregate} entities.
     */
    @Autowired
    private AggregateRepository loanApprovals;

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
        final String loanRequestId,
        final int loanAmount) throws Exception {

        // build the aggregate
        // (https://github.com/vanillabp/spi-for-java/blob/main/README.md#process-specific-workflow-aggregate)

        final var loanApproval = new Aggregate();
        loanApproval.setLoanRequestId(loanRequestId);
        loanApproval.setAmount(loanAmount);

        // start workflow

        service.startWorkflow(loanApproval);

        log.info("Loan approval '{}' started", loanApproval.getLoanRequestId());

    }

    /**
     * This method is called by VanillaBP once the user task, identified by the method's name, is created
     *
     * @see <a href="https://github.com/vanillabp/spi-for-java/blob/main/README.md#wire-up-a-task">VanillaBP docs &quot;Wire up a task&quot;</a>
     * @see <a href="https://github.com/vanillabp/spi-for-java/blob/main/README.md#user-tasks-and-asynchronous-tasks>VanillaBP docs &quot;User tasks and asynchronous tasks&quot;</a>
     * @param loanApproval The workflow's aggregate.
     * @param taskId Unique identifier for the user task.
     */
    @WorkflowTask
    public void assessRisk(
        final Aggregate loanApproval,
        @TaskId final String taskId) {

        // store task id for later validation (see Service#completeRiskAssessment(...))

        loanApproval.setAssessRiskTaskId(taskId);

        log.info("Assessing risk for loan approval '{}' (user task ID = '{}')", loanApproval.getLoanRequestId(), taskId);

    }

    /**
     /**
     * This method is called by VanillaBP once the service task, identified by the method's name, is created.
     *
     * @see <a href="https://github.com/vanillabp/spi-for-java/blob/main/README.md#wire-up-a-task">VanillaBP docs &quot;Wire up a task&quot;</a>
     * @param loanApproval The workflow's aggregate.
     */
    @WorkflowTask
    public void transferMoney(
            final Aggregate loanApproval) {

        log.info("Transferring money for loan request '{}'", loanApproval.getLoanRequestId());

        // not part of this demo

    }

    /**
     * Completes a risk assessment task based on the given decision.
     *
     * @param loanRequestId The identifier for a single loan approval.
     * @param taskId  The unique identifier of the user task.
     * @param riskIsAcceptable Whether the risk acceptable.
     */
    public boolean completeRiskAssessment(
            final String loanRequestId,
            final String taskId,
            final boolean riskIsAcceptable) {

        final var loanApprovalFound = loanApprovals.findById(loanRequestId);

        // validation

        if (loanApprovalFound.isEmpty()) {
            return false;
        }
        final var loanApproval = loanApprovalFound.get();
        if ((loanApproval.getAssessRiskTaskId() == null) || !taskId.equals(loanApproval.getAssessRiskTaskId())) {
            return false;
        }

        log.info("Got risk assessment '{}' for loan approval '{}'", riskIsAcceptable ? "accepted" : "denied", loanRequestId);

        // save confirmed data in aggregate

        loanApproval.setRiskAcceptable(riskIsAcceptable);

        // complete user task

        service.completeUserTask(loanApproval, taskId);

        return true;

    }

}
