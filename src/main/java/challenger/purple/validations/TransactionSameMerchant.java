package challenger.purple.validations;

import challenger.purple.Util.Util;
import challenger.purple.model.Transaction;
import challenger.purple.model.enums.EnumAccountViolations;
import challenger.purple.model.response.AccountResponse;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class TransactionSameMerchant implements Validations<AccountResponse, Map<Integer, Transaction>> {
    private Validations nextValidator;

    Validations setNextValidtor(Validations nextValidtor) {
        this.nextValidator = nextValidtor;
        return this;
    }

    @Override
    public AccountResponse validation(AccountResponse accountResponse, Map<Integer, Transaction> transactionModelMap) {
        AtomicLong count = new AtomicLong(0);

        for (int i = 0; i < transactionModelMap.values().size()-1; i++) {
            for (int j = i+1; j < transactionModelMap.values().size(); j++) {
                if(isEquals(transactionModelMap, i, j) && compareDuration(transactionModelMap.get(i+1),transactionModelMap.get(j+1))){
                    count.incrementAndGet();
                }
            }
        }
        if(count.get() > 0){
            accountResponse.setViolations(EnumAccountViolations.DOUBLE_TRANSACTION);
        }

        return nextValidator.validation(accountResponse,transactionModelMap);
    }

    private boolean isEquals(Map<Integer, Transaction> transactionModelMap, int i, int j) {
        return transactionModelMap.get(i+1).equals(transactionModelMap.get(j+1));
    }


    private boolean compareDuration(Transaction previus, Transaction next){
        Duration duration = Duration.between(Util.convertStringDateToLocalDateTime(previus.getTime()),Util.convertStringDateToLocalDateTime(next.getTime()));
        return duration.getSeconds() <= 120;
    }
}
