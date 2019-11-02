package challenger.purple.validations;

import challenger.purple.model.TransactionModel;
import challenger.purple.model.enums.EnumAccountViolations;
import challenger.purple.model.response.AccountResponseModel;

import java.util.Map;

public class LimitCardValidator implements Validations<AccountResponseModel, Map<Integer, TransactionModel>> {

    private Validations nextValidtor;

    @Override
    public AccountResponseModel validation(AccountResponseModel o, Map<Integer, TransactionModel> v) {
        TransactionModel transactionModel = v.get(v.values().size());
        if(o.getAvailableLimit()  < transactionModel.getAmount()){
            o.setViolations(EnumAccountViolations.INSUFFICIENT_LIMIT);
            return o;
        }
        return nextValidtor.validation(o,v);
    }

    Validations setNextValidtor(Validations nextValidtor) {
        this.nextValidtor = nextValidtor;
        return this;
    }
}
