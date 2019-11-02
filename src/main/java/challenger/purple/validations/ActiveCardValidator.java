package challenger.purple.validations;

import challenger.purple.model.TransactionModel;
import challenger.purple.model.enums.EnumAccountViolations;
import challenger.purple.model.response.AccountResponseModel;

import java.util.Map;

public class ActiveCardValidator implements Validations<AccountResponseModel, Map<Integer, TransactionModel>> {

    private Validations nextValidtor;

    @Override
    public AccountResponseModel validation(AccountResponseModel accountResponseModel, Map<Integer, TransactionModel> transactionModelMap) {
        if(!accountResponseModel.isActiveCard()){
            accountResponseModel.setViolations(EnumAccountViolations.CARD_NOT_ACTIVE);
            return accountResponseModel;
        }
        return nextValidtor.validation(accountResponseModel,transactionModelMap);
    }

    public Validations getNextValidtor() {
        return nextValidtor;
    }

    public Validations setNextValidtor(Validations nextValidtor) {
        this.nextValidtor = nextValidtor;
        return this;
    }
}
