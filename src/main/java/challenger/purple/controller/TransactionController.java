package challenger.purple.controller;

import challenger.purple.model.event.TransactionEvent;
import challenger.purple.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    private ExecutorService executorService = Executors.newFixedThreadPool(5);


    @EventListener
    public void processEvent(TransactionEvent transactionEvent){
        executorService.execute(() -> transactionService.createTransaction(transactionEvent.getTransaction()));
    }
}
