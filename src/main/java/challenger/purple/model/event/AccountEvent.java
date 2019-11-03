package challenger.purple.model.event;

import challenger.purple.model.Account;
import org.springframework.context.ApplicationEvent;


public class AccountEvent extends ApplicationEvent {

    private static final long serialVersionUID = 5507366609826243112L;
    private Account account;

    public AccountEvent(Object source, Account account) {
        super(source);
        this.account = account;
    }

    public AccountEvent(Object source) {
        super(source);
    }


    public Account getAccount() {
        return account;
    }

    public AccountEvent setAccount(Account account) {
        this.account = account;
        return this;
    }
}
