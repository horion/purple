package challenger.purple.persistence.impl;

import challenger.purple.model.TransactionModel;
import challenger.purple.persistence.Persistence;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

@Component
public class TransactionPersistenceImpl implements Persistence<TransactionModel> {


    private Map<Integer,TransactionModel> transactions;

    public TransactionPersistenceImpl() {
        this.transactions = new TreeMap<>();
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
