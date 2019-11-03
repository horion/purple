package challenger.purple.service.impl;

import challenger.purple.model.Account;
import challenger.purple.model.enums.EnumAccountViolations;
import challenger.purple.model.response.AccountResponse;
import challenger.purple.persistence.impl.AccountPersistenceImpl;
import challenger.purple.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountPersistenceImpl persistence;

    @Autowired
    public AccountServiceImpl(AccountPersistenceImpl persistence) {
        this.persistence = persistence;
    }

    @Override
    public AccountResponse save(Account account) {
        Account accountPersistence = this.persistence.getById(1);
        if(accountPersistence == null) {
            Account saved = this.persistence.merge(account);
            return new AccountResponse(saved);
        }else{
            AccountResponse accountResponse = new AccountResponse(accountPersistence);
            accountResponse.setViolations(EnumAccountViolations.ACCOUNT_ALREADY_INITIALIZED);
            return accountResponse;
        }
    }

    @Override
    public Account getAccount(Integer id) {
        return this.persistence.getById(id);
    }

    @Override
    public Account updateLimit(Integer id, Long amount) {
        Account account = this.getAccount(id);
        long availableLimit = account.getAvailableLimit() - amount;
        if(availableLimit < 0)
            availableLimit = 0;
        account.setAvailableLimit(availableLimit);
        persistence.merge(account);
        return account;
    }

}
