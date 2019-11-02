package challenger.purple.validations;

import challenger.purple.Util.Util;
import challenger.purple.model.TransactionModel;
import challenger.purple.model.enums.EnumAccountViolations;
import challenger.purple.model.response.AccountResponseModel;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TransactionFrequencySmallInterval implements Validations<AccountResponseModel, Map<Integer, TransactionModel>> {

    private Validations nextValidtor;

    @Override
    public AccountResponseModel validation(AccountResponseModel o, Map<Integer, TransactionModel> v) {
        long l = v.values().stream().filter(transactionModel -> getTime(transactionModel) <= 0).count();

        if(l > 3){
            o.setViolations(EnumAccountViolations.HIGH_FREQUENCY_SMALL_INTERVAL);
            return o;
        }
        return nextValidtor.validation(o,v);
    }

    private long getTime(TransactionModel transactionModel) {
        Long aLong = Util.convertStringDateToMillis(transactionModel.getTime());
        return (aLong + TimeUnit.MINUTES.toMillis(2L)) - aLong;
    }

    public Validations getNextValidtor() {
        return nextValidtor;
    }

    public Validations setNextValidtor(Validations nextValidtor) {
        this.nextValidtor = nextValidtor;
        return this;
    }
}
