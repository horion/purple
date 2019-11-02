package challenger.purple.validations;

import challenger.purple.model.TransactionModel;
import challenger.purple.model.enums.EnumAccountViolations;
import challenger.purple.model.response.AccountResponseModel;

import java.util.Map;

public class LimitCardValidator implements Validations<AccountResponseModel, Map<Integer, TransactionModel>> {

    private Validations nextValidtor;

    @Override
    public AccountResponseModel validation(AccountResponseModel accountResponseModel, Map<Integer, TransactionModel> transactionModelMap) {
        TransactionModel transactionModel = transactionModelMap.get(transactionModelMap.values().size());
        if(accountResponseModel.getAvailableLimit()  < transactionModel.getAmount()){
            accountResponseModel.setViolations(EnumAccountViolations.INSUFFICIENT_LIMIT);
            return accountResponseModel;
        }
        return nextValidtor.validation(accountResponseModel,transactionModelMap);
    }

    Validations setNextValidtor(Validations nextValidtor) {
        this.nextValidtor = nextValidtor;
        return this;
    }
}
