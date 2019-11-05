package challenger.purple.controller;

import challenger.purple.model.Account;
import challenger.purple.model.event.AccountEvent;
import challenger.purple.queue.ExecutorManager;
import challenger.purple.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountControllerTest {

    private AccountController accountController;
    private AccountEvent accountEvent;
    private ExecutorManager executorManager;


    @BeforeEach
    void init(){
        executorManager = mock(ExecutorManager.class);
        AccountService accountService = mock(AccountService.class);
        accountEvent = new AccountEvent(this);
        accountController = new AccountController(accountService,executorManager);
        accountEvent.setAccount(new Account(true,100L));
        when(executorManager.queueSize()).thenReturn(1);

    }

    @Test
    void proccessEvent(){
        accountController.processEvent(accountEvent);
        int result = executorManager.queueSize();
        assertEquals(1,result);
    }





}