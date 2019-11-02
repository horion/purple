package challenger.purple.persistence.impl;

import challenger.purple.model.AccountModel;
import challenger.purple.persistence.Persistence;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AccountPersistenceImpl implements Persistence<AccountModel> {

    private Map<Integer,AccountModel> account;

    public AccountPersistenceImpl() {
        this.account = new HashMap<>();
    }

    @Override
    public AccountModel merge(AccountModel accountModel) {
        return this.account.put(1,accountModel);
    }

    @Override
    public AccountModel getById(Integer id) {
        return this.account.get(id);
    }

    @Override
    public Map<Integer, AccountModel> get() {
        return this.account;
    }

}
