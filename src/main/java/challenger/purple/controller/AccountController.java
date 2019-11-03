package challenger.purple.controller;

import challenger.purple.Util.Util;
import challenger.purple.model.Account;
import challenger.purple.model.event.AccountEvent;
import challenger.purple.model.response.AccountResponse;
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
        execute(accountEvent);
    }

    private void execute(AccountEvent accountEvent) {
        Account account = accountEvent.getAccount();

        if(account.getAvailableLimit() < 0L)
            account.setAvailableLimit(0L);

        AccountResponse accountResponse = accountService.save(account);
        System.out.println(Util.objectToJson(accountResponse));
    }

}
