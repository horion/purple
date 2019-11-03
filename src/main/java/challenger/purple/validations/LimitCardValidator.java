package challenger.purple.validations;

import challenger.purple.model.Transaction;
import challenger.purple.model.enums.EnumAccountViolations;
import challenger.purple.model.response.AccountResponse;

import java.util.Map;

public class LimitCardValidator implements Validations<AccountResponse, Map<Integer, Transaction>> {

    private Validations nextValidator;

    @Override
    public AccountResponse validation(AccountResponse accountResponse, Map<Integer, Transaction> transactionModelMap) {
        Transaction transaction = transactionModelMap.get(transactionModelMap.values().size());
        if(accountResponse.getAvailableLimit()  < transaction.getAmount()){
            accountResponse.setViolations(EnumAccountViolations.INSUFFICIENT_LIMIT);
            return accountResponse;
        }
        return nextValidator.validation(accountResponse,transactionModelMap);
    }

    Validations setNextValidtor(Validations nextValidtor) {
        this.nextValidator = nextValidtor;
        return this;
    }
}
