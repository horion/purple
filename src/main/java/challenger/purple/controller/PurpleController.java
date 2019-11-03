package challenger.purple.controller;


import challenger.purple.Util.Util;
import challenger.purple.model.Account;
import challenger.purple.model.Transaction;
import challenger.purple.model.event.AccountEvent;
import challenger.purple.model.event.TransactionEvent;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

public class PurpleController {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    public void startProcess(String[] args){
        if(args != null && args.length > 0){
            for (int i = 0; i < args.length -1; i++) {
                JsonNode jsonNode = Util.getJsonNode(args[i]);
                if(jsonNode != null && !jsonNode.get("account").isNull()){
                    AccountEvent accountEvent = new AccountEvent(this);
                    getAccount(accountEvent, jsonNode);
                    applicationEventPublisher.publishEvent(accountEvent);
                }else if(jsonNode != null && !jsonNode.get("transaction").isNull()){
                    TransactionEvent transactionEvent = new TransactionEvent(this);
                    getTransaction(transactionEvent, jsonNode);
                    applicationEventPublisher.publishEvent(transactionEvent);
                }
            }
        }
    }

    private void getTransaction(TransactionEvent transactionEvent, JsonNode jsonNode) {
        Transaction transaction = new Transaction(jsonNode.get("transaction").get("merchant").asText()
                ,jsonNode.get("transaction").get("amount").asLong(),jsonNode.get("transaction").get("time").asText());
        transactionEvent.setTransaction(transaction);
    }

    private void getAccount(AccountEvent transactionEvent, JsonNode jsonNode) {
        Account account = new Account(jsonNode.get("account").get("activeCard").asBoolean()
                ,jsonNode.get("account").get("availableLimit").asLong());
        transactionEvent.setAccount(account);
    }
}
