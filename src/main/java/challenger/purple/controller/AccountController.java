package challenger.purple.controller;

import challenger.purple.model.Account;
import challenger.purple.model.event.AccountEvent;
import challenger.purple.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
public class AccountController {

    @Autowired
    private AccountService accountService;

    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    @EventListener
    public void processEvent(AccountEvent accountEvent){
        executorService.execute(() -> execute(accountEvent));
    }

    private void execute(AccountEvent accountEvent) {
        Account account = accountEvent.getAccount();

        if(account.getAvailableLimit() < 0L)
            account.setAvailableLimit(0L);

        accountService.save(account);
    }

}
