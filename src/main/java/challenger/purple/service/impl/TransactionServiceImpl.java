package challenger.purple.service.impl;

import challenger.purple.model.Account;
import challenger.purple.model.Transaction;
import challenger.purple.model.response.AccountResponse;
import challenger.purple.persistence.impl.TransactionPersistenceImpl;
import challenger.purple.service.TransactionService;
import challenger.purple.validations.transaction.ConfigureValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionPersistenceImpl persistence;
    private final AccountServiceImpl accountService;

    @Autowired
    public TransactionServiceImpl(TransactionPersistenceImpl persistence, AccountServiceImpl accountService) {
        this.persistence = persistence;
        this.accountService = accountService;
    }

    @Override
    public AccountResponse createTransaction(Transaction transaction){
        Account account = this.accountService.getAccount(1);
        AccountResponse accountResponse = new AccountResponse(account);
        persistence.merge(transaction);

        ConfigureValidator configureValidator = new ConfigureValidator(accountResponse,persistence.get());

        AccountResponse validation = configureValidator.validation();

        if(validation.getViolations() == null || validation.getViolations().isEmpty()) {
            Account resultUpdate =  accountService.updateLimit(1, transaction.getAmount());
            validation.getAccount().setAvailableLimit(resultUpdate.getAvailableLimit());
        }

        return validation;
    }
}
