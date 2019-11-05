package challenger.purple.controller;

import challenger.purple.model.Transaction;
import challenger.purple.model.event.TransactionEvent;
import challenger.purple.queue.ExecutorManager;
import challenger.purple.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TransactionControllerTest {

    private ExecutorManager executorManager;
    private TransactionEvent transactionEvent;
    private TransactionController transactionController;

    @BeforeEach
    void init(){
        executorManager = mock(ExecutorManager.class);
        TransactionService transactionService = mock(TransactionService.class);
        transactionEvent = new TransactionEvent(this);
        transactionController = new TransactionController(transactionService,executorManager);
        transactionEvent.setTransaction(new Transaction("Burger King",100L,"2019-02-13T10:00:00.000Z"));
        when(executorManager.queueSize()).thenReturn(1);

    }

    @Test
    void proccessEvent(){
        transactionController.processEvent(transactionEvent);
        int result = executorManager.queueSize();
        assertEquals(1,result);
    }


}