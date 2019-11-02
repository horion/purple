package challenger.purple.validations;

import challenger.purple.Util.Util;
import challenger.purple.model.TransactionModel;
import challenger.purple.model.enums.EnumAccountViolations;
import challenger.purple.model.response.AccountResponseModel;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TransactionFrequencySmallInterval implements Validations<AccountResponseModel, Map<Integer, TransactionModel>> {

    private Validations nextValidator;
    private AtomicLong count;

    @Override
    public AccountResponseModel validation(AccountResponseModel accountResponseModel, Map<Integer, TransactionModel> transactionModelMap) {
        count = new AtomicLong(0);
        List<LocalDateTime>  transactionsDate = transactionModelMap.values().stream().map(getTime()).collect(Collectors.toList());
        for (int i = 0; i < transactionsDate.size()-1; i++) {
            for (int j = i+1; j < transactionsDate.size(); j++) {
                compareDuration(transactionsDate.get(i),transactionsDate.get(j));
            }
        }
        if(count.get() > 3){
            accountResponseModel.setViolations(EnumAccountViolations.HIGH_FREQUENCY_SMALL_INTERVAL);
            return accountResponseModel;
        }
        return nextValidator.validation(accountResponseModel,transactionModelMap);
    }

    private Function<TransactionModel, LocalDateTime> getTime() {
        return transactionModel ->
                Util.convertStringDateToLocalDateTime(transactionModel.getTime());
    }

    private void compareDuration(LocalDateTime previus, LocalDateTime next){
        Duration duration = Duration.between(previus, next);
        if(duration.getSeconds() <= 120)
            count.incrementAndGet();

    }


    Validations setNextValidtor(Validations nextValidtor) {
        this.nextValidator = nextValidtor;
        return this;
    }
}
