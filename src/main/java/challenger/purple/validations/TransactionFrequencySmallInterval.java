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
import java.util.stream.IntStream;

public class TransactionFrequencySmallInterval implements Validations<AccountResponseModel, Map<Integer, TransactionModel>> {

    private Validations nextValidtor;
    private AtomicLong count;

    @Override
    public AccountResponseModel validation(AccountResponseModel accountResponseModel, Map<Integer, TransactionModel> transactionModelMap) {
        count = new AtomicLong(0);
        List<LocalDateTime>  transactionsDate = transactionModelMap.values().stream().map(getTime()).collect(Collectors.toList());
        IntStream.range(0,transactionsDate.size()-1).forEach(i -> compareDuration(transactionsDate.get(i),transactionsDate.get(i+1)));

        if(count.get() >= 2){
            accountResponseModel.setViolations(EnumAccountViolations.HIGH_FREQUENCY_SMALL_INTERVAL);
            return accountResponseModel;
        }
        return nextValidtor.validation(accountResponseModel,transactionModelMap);
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




    public Validations getNextValidtor() {
        return nextValidtor;
    }

    public Validations setNextValidtor(Validations nextValidtor) {
        this.nextValidtor = nextValidtor;
        return this;
    }
}
