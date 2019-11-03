package challenger.purple.controller;

import challenger.purple.model.event.TransactionEvent;
import challenger.purple.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    @EventListener
    public void processEvent(TransactionEvent transactionEvent){
        transactionService.createTransaction(transactionEvent.getTransaction());
    }
}
