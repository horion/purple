package challenger.purple.persistence.impl;

import challenger.purple.model.TransactionModel;
import challenger.purple.persistence.Persistence;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TransactionPersistenceImpl implements Persistence<TransactionModel> {


    private Map<Integer,TransactionModel> transactions;

    public TransactionPersistenceImpl() {
        this.transactions = new HashMap<>();
    }

    @Override
    public TransactionModel merge(TransactionModel transactionModel) {
        TransactionModel result;
        int index = this.transactions.size() + 1;
        result = this.transactions.put(index,transactionModel);
        return result;
    }

    @Override
    public TransactionModel getById(Integer id) {
        return this.transactions.get(id);
    }

    @Override
    public Map<Integer, TransactionModel> get() {
        return this.transactions;
    }


}
