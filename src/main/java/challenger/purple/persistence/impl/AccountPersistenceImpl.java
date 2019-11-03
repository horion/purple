package challenger.purple.persistence.impl;

import challenger.purple.model.Account;
import challenger.purple.persistence.Persistence;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

@Component
public class AccountPersistenceImpl implements Persistence<Account> {

    private Map<Integer, Account> account;

    public AccountPersistenceImpl() {
        this.account = new TreeMap<>();
    }

    @Override
    public Account merge(Account account) {
        return this.account.put(1, account);
    }

    @Override
    public Account getById(Integer id) {
        return this.account.get(id);
    }

    @Override
    public Map<Integer, Account> get() {
        return this.account;
    }

}
