package challenger.purple.validations;

import challenger.purple.Util.Util;
import challenger.purple.model.TransactionModel;
import challenger.purple.model.enums.EnumAccountViolations;
import challenger.purple.model.response.AccountResponseModel;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class TransactionSameMerchant implements Validations<AccountResponseModel, Map<Integer, TransactionModel>> {
    private Validations nextValidtor;


    public Validations getNextValidtor() {
        return nextValidtor;
    }

    public Validations setNextValidtor(Validations nextValidtor) {
        this.nextValidtor = nextValidtor;
        return this;
    }

    @Override
    public AccountResponseModel validation(AccountResponseModel accountResponseModel, Map<Integer, TransactionModel> transactionModelMap) {
        AtomicLong count = new AtomicLong(0);

        for (int i = 0; i < transactionModelMap.values().size()-1; i++) {
            for (int j = i+1; j < transactionModelMap.values().size(); j++) {
                if(isEquals(transactionModelMap, i, j) && compareDuration(transactionModelMap.get(i+1),transactionModelMap.get(j+1))){
                    count.incrementAndGet();
                }
            }
        }
        if(count.get() > 0){
            accountResponseModel.setViolations(EnumAccountViolations.DOUBLE_TRANSACTION);
        }

        return nextValidtor.validation(accountResponseModel,transactionModelMap);
    }

    private boolean isEquals(Map<Integer, TransactionModel> transactionModelMap, int i, int j) {
        return transactionModelMap.get(i+1).equals(transactionModelMap.get(j+1));
    }


    private boolean compareDuration(TransactionModel previus, TransactionModel next){
        Duration duration = Duration.between(Util.convertStringDateToLocalDateTime(previus.getTime()),Util.convertStringDateToLocalDateTime(next.getTime()));
        return duration.getSeconds() <= 120;
    }
}
