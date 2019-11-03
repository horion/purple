package challenger.purple.validations.transaction;

import challenger.purple.model.Transaction;
import challenger.purple.model.enums.EnumAccountViolations;
import challenger.purple.model.response.AccountResponse;
import challenger.purple.validations.Validations;

import java.util.Collections;
import java.util.Map;

public class ActiveCardValidator implements Validations<AccountResponse, Map<Integer, Transaction>> {

    private Validations nextValidator;

    @Override
    public AccountResponse validation(AccountResponse accountResponse, Map<Integer, Transaction> transactionModelMap) {
        if(!accountResponse.getAccount().isActiveCard()){
            accountResponse.setViolations(Collections.singletonList(EnumAccountViolations.CARD_NOT_ACTIVE));
            return accountResponse;
        }
        return nextValidator.validation(accountResponse,transactionModelMap);
    }

    public Validations getNextValidator() {
        return nextValidator;
    }

    public Validations setNextValidtor(Validations nextValidtor) {
        this.nextValidator = nextValidtor;
        return this;
    }
}
