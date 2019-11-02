package challenger.purple.validations;

import challenger.purple.model.TransactionModel;
import challenger.purple.model.enums.EnumAccountViolations;
import challenger.purple.model.response.AccountResponseModel;

import java.util.Map;

public class ActiveCardValidator implements Validations<AccountResponseModel, Map<Integer, TransactionModel>> {

    private Validations nextValidator;

    @Override
    public AccountResponseModel validation(AccountResponseModel accountResponseModel, Map<Integer, TransactionModel> transactionModelMap) {
        if(!accountResponseModel.isActiveCard()){
            accountResponseModel.setViolations(EnumAccountViolations.CARD_NOT_ACTIVE);
            return accountResponseModel;
        }
        return nextValidator.validation(accountResponseModel,transactionModelMap);
    }

    public Validations getNextValidator() {
        return nextValidator;
    }

    Validations setNextValidtor(Validations nextValidtor) {
        this.nextValidator = nextValidtor;
        return this;
    }
}
