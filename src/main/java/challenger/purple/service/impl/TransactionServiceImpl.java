package challenger.purple.service.impl;

import challenger.purple.model.AccountModel;
import challenger.purple.model.TransactionModel;
import challenger.purple.model.response.AccountResponseModel;
import challenger.purple.persistence.impl.TransactionPersistenceImpl;
import challenger.purple.validations.ConfigureValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl {

    private final TransactionPersistenceImpl persistence;
    private final AccountServiceImpl accountService;

    @Autowired
    public TransactionServiceImpl(TransactionPersistenceImpl persistence, AccountServiceImpl accountService) {
        this.persistence = persistence;
        this.accountService = accountService;
    }

    public AccountResponseModel createTransaction(TransactionModel transactionModel){
        AccountModel account = this.accountService.getAccount(1);
        AccountResponseModel accountResponseModel = new AccountResponseModel(account);
        persistence.merge(transactionModel);

        ConfigureValidator configureValidator = new ConfigureValidator(accountResponseModel,persistence.get());

        AccountResponseModel validation = configureValidator.validation();

        if(validation.getViolations() == null || validation.getViolations().isEmpty())
            accountService.updateLimit(1,transactionModel.getAmount());

        return validation;
    }
}
