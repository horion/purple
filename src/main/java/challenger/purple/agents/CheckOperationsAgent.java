package challenger.purple.agents;


import challenger.purple.Util.Util;
import challenger.purple.model.Account;
import challenger.purple.model.Transaction;
import challenger.purple.model.event.AccountEvent;
import challenger.purple.model.event.TransactionEvent;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ConfigurationProperties(prefix = "scheduler")
@Component
public class CheckOperationsAgent {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());



    @Scheduled(initialDelayString = "${scheduler.initial}", fixedRateString = "${scheduler.fixedRate}")
    public void startProcess(){
        try {
            Path operationsFile = Paths.get("operations");
            Files.lines(operationsFile).forEach(this::getOperations);
        } catch (IOException e) {
            logger.info("Arquivo de operações não encontrado");
        }
    }

    private void getOperations(String folder) {
        if(!StringUtils.isEmpty(folder)){
            JsonNode jsonNode = Util.getJsonNode(folder);
            if(jsonNode != null && jsonNode.get("account") != null){
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