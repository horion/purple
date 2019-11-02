package challenger.purple.validations;

import challenger.purple.model.TransactionModel;
import challenger.purple.model.enums.EnumAccountViolations;
import challenger.purple.model.response.AccountResponseModel;

import java.util.Map;

public class LimitCardValidator implements Validations<AccountResponseModel, Map<Integer, TransactionModel>> {

    private Validations nextValidator;

    @Override
    public AccountResponseModel validation(AccountResponseModel accountResponseModel, Map<Integer, TransactionModel> transactionModelMap) {
        TransactionModel transactionModel = transactionModelMap.get(transactionModelMap.values().size());
        if(accountResponseModel.getAvailableLimit()  < transactionModel.getAmount()){
            accountResponseModel.setViolations(EnumAccountViolations.INSUFFICIENT_LIMIT);
            return accountResponseModel;
        }
        return nextValidator.validation(accountResponseModel,transactionModelMap);
    }

    Validations setNextValidtor(Validations nextValidtor) {
        this.nextValidator = nextValidtor;
        return this;
    }
}
