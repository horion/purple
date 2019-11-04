package challenger.purple.service.impl;

import challenger.purple.model.Account;
import challenger.purple.model.enums.EnumAccountViolations;
import challenger.purple.model.response.AccountResponse;
import challenger.purple.persistence.impl.AccountPersistenceImpl;
import challenger.purple.service.AccountService;
import challenger.purple.validations.account.ConfigureAccountValidator;
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
        AccountResponse accountResponse = new AccountResponse(accountPersistence);
        ConfigureAccountValidator configureAccountValidator = new ConfigureAccountValidator(account,accountResponse);
        AccountResponse validation = configureAccountValidator.validation();
        if(validation.getViolations() == null || validation.getViolations().isEmpty()) {
            this.persistence.merge(account);
        }
        return accountResponse;
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
