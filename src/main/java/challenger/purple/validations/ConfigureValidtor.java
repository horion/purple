package challenger.purple.validations;

import challenger.purple.model.TransactionModel;
import challenger.purple.model.response.AccountResponseModel;

import java.util.Map;

public class ConfigureValidtor {

    private  ActiveCardValidator activeCardValidator;
    private AccountResponseModel accountModel;
    private Map<Integer, TransactionModel> transactionModel;


    public ConfigureValidtor(AccountResponseModel accountModel, Map<Integer, TransactionModel> transactionModel) {
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

    public AccountResponseModel validation(){
        return activeCardValidator.validation(accountModel,transactionModel);
    }


}
