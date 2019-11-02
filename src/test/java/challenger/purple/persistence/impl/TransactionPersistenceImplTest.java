package challenger.purple.persistence.impl;

import challenger.purple.model.TransactionModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TransactionPersistenceImplTest {

    private TransactionPersistenceImpl transactionPersistence;
    private TransactionModel transactionModel;
    private Map<Integer,TransactionModel> map = new TreeMap<>();

    @BeforeEach
    void init(){
        transactionPersistence = mock(TransactionPersistenceImpl.class) ;
        transactionModel = new TransactionModel("Burger King", 30L, "2019-02-13T10:00:00.000Z");
    }

    @Test
    void merge() {
        when(transactionPersistence.merge(transactionModel)).thenReturn(transactionModel);
        TransactionModel result = transactionPersistence.merge(transactionModel);
        assertEquals(transactionModel,result);
    }

    @Test
    void getById() {
        when(transactionPersistence.getById(1)).thenReturn(transactionModel);
        TransactionModel result = transactionPersistence.getById(1);
        assertEquals(transactionModel,result);
    }

    @Test
    void get() {
        map.put(1,transactionModel);
        when(transactionPersistence.get()).thenReturn(map);
        Map<Integer,TransactionModel> transactionModelMap = transactionPersistence.get();
        assertEquals(map,transactionModelMap);
    }
}