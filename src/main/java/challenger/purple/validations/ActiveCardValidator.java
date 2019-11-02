package challenger.purple.validations;

import challenger.purple.model.TransactionModel;
import challenger.purple.model.enums.EnumAccountViolations;
import challenger.purple.model.response.AccountResponseModel;

import java.util.Map;

public class ActiveCardValidator implements Validations<AccountResponseModel, Map<Integer, TransactionModel>> {

    private Validations nextValidtor;

    @Override
    public AccountResponseModel validation(AccountResponseModel o, Map<Integer, TransactionModel> v) {
        if(!o.isActiveCard()){
            o.setViolations(EnumAccountViolations.CARD_NOT_ACTIVE);
            return o;
        }
        return nextValidtor.validation(o,v);
    }

    public Validations getNextValidtor() {
        return nextValidtor;
    }

    public Validations setNextValidtor(Validations nextValidtor) {
        this.nextValidtor = nextValidtor;
        return this;
    }
}
