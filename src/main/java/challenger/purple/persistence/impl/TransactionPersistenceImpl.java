package challenger.purple.persistence.impl;

import challenger.purple.model.Transaction;
import challenger.purple.persistence.Persistence;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

@Component
public class TransactionPersistenceImpl implements Persistence<Transaction> {


    private Map<Integer, Transaction> transactions;

    public TransactionPersistenceImpl() {
        this.transactions = new TreeMap<>();
    }

    @Override
    public Transaction merge(Transaction transaction) {
        Transaction result;
        int index = this.transactions.size() + 1;
        result = this.transactions.put(index, transaction);
        return result;
    }

    @Override
    public Transaction getById(Integer id) {
        return this.transactions.get(id);
    }

    @Override
    public Map<Integer, Transaction> get() {
        return this.transactions;
    }


}
