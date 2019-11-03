package challenger.purple.persistence.impl;

import challenger.purple.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TransactionPersistenceImplTest {

    private TransactionPersistenceImpl transactionPersistence;
    private Transaction transaction;
    private Map<Integer, Transaction> map = new TreeMap<>();

    @BeforeEach
    void init(){
        transactionPersistence = mock(TransactionPersistenceImpl.class) ;
        transaction = new Transaction("Burger King", 30L, "2019-02-13T10:00:00.000Z");
    }

    @Test
    void merge() {
        when(transactionPersistence.merge(transaction)).thenReturn(transaction);
        Transaction result = transactionPersistence.merge(transaction);
        assertEquals(transaction,result);
    }

    @Test
    void getById() {
        when(transactionPersistence.getById(1)).thenReturn(transaction);
        Transaction result = transactionPersistence.getById(1);
        assertEquals(transaction,result);
    }

    @Test
    void get() {
        map.put(1, transaction);
        when(transactionPersistence.get()).thenReturn(map);
        Map<Integer, Transaction> transactionModelMap = transactionPersistence.get();
        assertEquals(map,transactionModelMap);
    }
}