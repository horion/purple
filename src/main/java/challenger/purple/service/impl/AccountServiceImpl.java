package challenger.purple.service.impl;

import challenger.purple.model.AccountModel;
import challenger.purple.model.enums.EnumAccountViolations;
import challenger.purple.model.response.AccountResponseModel;
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
    public AccountResponseModel save(AccountModel accountModel) {
        AccountModel accountPersistence = this.persistence.getById(1);
        if(accountPersistence == null) {
            AccountModel saved = this.persistence.merge(accountModel);
            return new AccountResponseModel(saved);
        }else{
            AccountResponseModel accountResponseModel = new AccountResponseModel(accountModel);
            accountResponseModel.setViolations(EnumAccountViolations.ACCOUNT_ALREADY_INITIALIZED);
            return accountResponseModel;
        }
    }

    @Override
    public AccountModel getAccount(Integer id) {
        return this.persistence.getById(id);
    }

    @Override
    public AccountModel updateLimit(Integer id, Long amount) {
        AccountModel accountModel = this.getAccount(id);
        accountModel.setAvailableLimit(accountModel.getAvailableLimit() - amount);
        persistence.merge(accountModel);
        return accountModel;
    }

}
