package challenger.purple.controller;

import challenger.purple.Util.Util;
import challenger.purple.model.event.TransactionEvent;
import challenger.purple.model.response.AccountResponse;
import challenger.purple.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class TransactionController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private TransactionService transactionService;


    @EventListener
    public void processEvent(TransactionEvent transactionEvent){
        AccountResponse accountResponse = transactionService.createTransaction(transactionEvent.getTransaction());
        System.out.println(Util.objectToJson(accountResponse));
    }
}