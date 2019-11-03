package challenger.purple.validations.transaction;

import challenger.purple.model.Transaction;
import challenger.purple.model.response.AccountResponse;
import challenger.purple.validations.LastValidator;

import java.util.Map;

public class ConfigureValidator {

    private  ActiveCardValidator activeCardValidator;
    private AccountResponse accountModel;
    private Map<Integer, Transaction> transactionModel;


    public ConfigureValidator(AccountResponse accountModel, Map<Integer, Transaction> transactionModel) {
        this.init();
        this.accountModel = accountModel;
        this.transactionModel = transactionModel;
    }

    private void init(){
        TransactionSameMerchant transactionSameMerchant = new TransactionSameMerchant();
        TransactionFrequencySmallInterval transactionFrequencySmallInterval = new TransactionFrequencySmallInterval();
        LimitCardValidator limitCardValidator = new LimitCardValidator();
        activeCardValidator = new ActiveCardValidator();

        activeCardValidator.setNextValidtor(limitCardValidator);
        limitCardValidator.setNextValidtor(transactionFrequencySmallInterval);
        transactionFrequencySmallInterval.setNextValidtor(transactionSameMerchant);
        transactionSameMerchant.setNextValidtor(new LastValidator());

    }

    public AccountResponse validation(){
        return activeCardValidator.validation(accountModel,transactionModel);
    }


}
