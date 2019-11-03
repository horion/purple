package challenger.purple.controller;

import challenger.purple.model.Account;
import challenger.purple.model.event.AccountEvent;
import challenger.purple.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class AccountController {

    @Autowired
    private AccountService accountService;

    @EventListener
    public void processEvent(AccountEvent accountEvent){

        Account account = accountEvent.getAccount();

        if(account.getAvailableLimit() < 0L)
            account.setAvailableLimit(0L);

        accountService.save(account);
    }

}
