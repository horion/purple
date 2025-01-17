package challenger.purple.controller;

import challenger.purple.model.event.TransactionEvent;
import challenger.purple.model.response.AccountResponse;
import challenger.purple.queue.ExecutorManager;
import challenger.purple.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class TransactionController {


    private final TransactionService transactionService;
    private final ExecutorManager executorManager;

    @Autowired
    public TransactionController(TransactionService transactionService, ExecutorManager executorManager) {
        this.transactionService = transactionService;
        this.executorManager = executorManager;
    }


    @EventListener
    public void processEvent(TransactionEvent transactionEvent){
        executorManager.addQueue(() -> execute(transactionEvent));
    }

    private void execute(TransactionEvent transactionEvent) {
        AccountResponse accountResponse = transactionService.createTransaction(transactionEvent.getTransaction());
        System.out.println(accountResponse);
    }
}
