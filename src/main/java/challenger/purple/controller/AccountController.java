package challenger.purple.controller;

import challenger.purple.Util.Util;
import challenger.purple.model.Account;
import challenger.purple.model.event.AccountEvent;
import challenger.purple.model.response.AccountResponse;
import challenger.purple.queue.ExecutorManager;
import challenger.purple.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class AccountController {

    private final AccountService accountService;
    private final ExecutorManager executorManager;

    @Autowired
    public AccountController(AccountService accountService, ExecutorManager executorManager) {
        this.accountService = accountService;
        this.executorManager = executorManager;
    }


    @EventListener
    public void processEvent(AccountEvent accountEvent){
        executorManager.addQueue(() -> execute(accountEvent));
    }

    private void execute(AccountEvent accountEvent) {
        Account account = accountEvent.getAccount();

        if(account.getAvailableLimit() < 0L)
            account.setAvailableLimit(0L);

        AccountResponse accountResponse = accountService.save(account);
        System.out.println(accountResponse);
    }

}
