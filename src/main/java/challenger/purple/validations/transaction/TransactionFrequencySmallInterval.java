package challenger.purple.validations.transaction;

import challenger.purple.Util.Util;
import challenger.purple.model.Transaction;
import challenger.purple.model.enums.EnumAccountViolations;
import challenger.purple.model.response.AccountResponse;
import challenger.purple.validations.Validations;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TransactionFrequencySmallInterval implements Validations<AccountResponse, Map<Integer, Transaction>> {

    private Validations nextValidator;
    private AtomicLong count;

    @Override
    public AccountResponse validation(AccountResponse accountResponse, Map<Integer, Transaction> transactionModelMap) {
        count = new AtomicLong(0);
        List<LocalDateTime>  transactionsDate = transactionModelMap.values().stream().map(getTime()).collect(Collectors.toList());
        for (int i = 0; i < transactionsDate.size()-1; i++) {
            for (int j = transactionsDate.size() - 1; j >= i + 1; j--) {
                if(compareDuration(transactionsDate.get(i),transactionsDate.get(j))){
                    count.incrementAndGet();
                }else{
                    break;
                }
            }
        }
        if(count.get() > 3){
            accountResponse.setViolations(Collections.singletonList(EnumAccountViolations.HIGH_FREQUENCY_SMALL_INTERVAL));
            return accountResponse;
        }
        return nextValidator.validation(accountResponse,transactionModelMap);
    }

    private Function<Transaction, LocalDateTime> getTime() {
        return transactionModel ->
                Util.convertStringDateToLocalDateTime(transactionModel.getTime());
    }

    private boolean compareDuration(LocalDateTime previus, LocalDateTime next){
        Duration duration = Duration.between(previus, next);
        return duration.getSeconds() <= 120;


    }


    public Validations setNextValidtor(Validations nextValidtor) {
        this.nextValidator = nextValidtor;
        return this;
    }
}
