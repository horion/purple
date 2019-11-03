package challenger.purple.validations;

import challenger.purple.model.Transaction;
import challenger.purple.model.enums.EnumAccountViolations;
import challenger.purple.model.response.AccountResponse;

import java.util.Map;

public class ActiveCardValidator implements Validations<AccountResponse, Map<Integer, Transaction>> {

    private Validations nextValidator;

    @Override
    public AccountResponse validation(AccountResponse accountResponse, Map<Integer, Transaction> transactionModelMap) {
        if(!accountResponse.isActiveCard()){
            accountResponse.setViolations(EnumAccountViolations.CARD_NOT_ACTIVE);
            return accountResponse;
        }
        return nextValidator.validation(accountResponse,transactionModelMap);
    }

    public Validations getNextValidator() {
        return nextValidator;
    }

    Validations setNextValidtor(Validations nextValidtor) {
        this.nextValidator = nextValidtor;
        return this;
    }
}
