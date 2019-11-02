package challenger.purple.service.impl;

import challenger.purple.model.AccountModel;
import challenger.purple.model.TransactionModel;
import challenger.purple.model.enums.EnumAccountViolations;
import challenger.purple.model.response.AccountResponseModel;
import challenger.purple.persistence.impl.TransactionPersistenceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TransactionServiceImplTest {
    private Map<Integer,TransactionModel> map = new TreeMap<>();
    private TransactionServiceImpl transactionService;
    private TransactionModel transactionModel;
    private AccountModel account;
    private TransactionPersistenceImpl persistence;
    private AccountServiceImpl accountService;


    @BeforeEach
    void init(){
        persistence = mock(TransactionPersistenceImpl.class);
        accountService = mock(AccountServiceImpl.class);
        transactionService = new TransactionServiceImpl(persistence, accountService);
        account = new AccountModel(true, 100L);
        when(accountService.getAccount(1)).thenReturn(account);
        transactionModel = new TransactionModel("Burger King", 20L, "2019-02-13T10:00:00.000Z");
        when(persistence.merge(eq(transactionModel))).thenReturn(transactionModel);
    }

    @Test
    void createTransaction() {
        TransactionModel transactionModel = new TransactionModel("Burger King", 20L, "2019-02-13T10:01:00.000Z");
        map.put(map.size()+1,transactionModel);

        when(persistence.get()).thenReturn(map);

        AccountResponseModel expected = new AccountResponseModel(account);
        AccountResponseModel result = transactionService.createTransaction(transactionModel);
        assertEquals(expected,result);

    }

    @Test
    void createTransactionSameMerchantFailed() {
        AccountResponseModel expected = new AccountResponseModel(account);
        TransactionModel transactionModel = new TransactionModel("Burger King", 20L, "2019-02-13T10:01:00.000Z");
        map.put(map.size()+1,transactionModel);
        when(persistence.get()).thenReturn(map);

        AccountResponseModel result = transactionService.createTransaction(transactionModel);
        assertEquals(expected,result);

        TransactionModel transactionModel2 = new TransactionModel("Burger King", 20L, "2019-02-13T10:01:00.000Z");
        map.put(map.size()+1,transactionModel2);

        expected.setViolations(EnumAccountViolations.DOUBLE_TRANSACTION);

        when(persistence.get()).thenReturn(map);

        AccountResponseModel result2 = transactionService.createTransaction(transactionModel);
        assertEquals(expected,result2);

    }


    @Test
    void createTransactionSameMerchantTimeSuccess() {
        AccountResponseModel expected = new AccountResponseModel(account);
        TransactionModel transactionModel = new TransactionModel("Burger King", 20L, "2019-02-13T10:01:00.000Z");
        map.put(map.size()+1,transactionModel);
        when(persistence.get()).thenReturn(map);

        AccountResponseModel result = transactionService.createTransaction(transactionModel);
        assertEquals(expected,result);

        TransactionModel transactionModel2 = new TransactionModel("Burger King", 20L, "2019-02-13T10:03:01.000Z");
        map.put(map.size()+1,transactionModel2);

        when(persistence.get()).thenReturn(map);

        AccountResponseModel result2 = transactionService.createTransaction(transactionModel);
        assertEquals(expected,result2);

    }

    @Test
    void createTransactionSameMerchantAmountSuccess() {
        AccountResponseModel expected = new AccountResponseModel(account);
        TransactionModel transactionModel = new TransactionModel("Burger King", 30L, "2019-02-13T10:01:00.000Z");
        map.put(map.size()+1,transactionModel);
        when(persistence.get()).thenReturn(map);

        AccountResponseModel result = transactionService.createTransaction(transactionModel);
        assertEquals(expected,result);

        TransactionModel transactionModel2 = new TransactionModel("Burger King", 20L, "2019-02-13T10:03:01.000Z");
        map.put(map.size()+1,transactionModel2);

        when(persistence.get()).thenReturn(map);

        AccountResponseModel result2 = transactionService.createTransaction(transactionModel);
        assertEquals(expected,result2);

    }


    @Test
    void createTransactionHighFrequecnyFailed() {
        AccountResponseModel expected = new AccountResponseModel(account);

        TransactionModel transactionModel = new TransactionModel("Burger King", 20L, "2019-02-13T10:01:00.000Z");
        map.put(map.size()+1,transactionModel);
        when(persistence.get()).thenReturn(map);

        AccountResponseModel result = transactionService.createTransaction(transactionModel);
        assertEquals(expected,result);

        TransactionModel transactionModel2 = new TransactionModel("Burger King", 30L, "2019-02-13T10:01:00.000Z");
        map.put(map.size()+1,transactionModel2);

        when(persistence.get()).thenReturn(map);

        AccountResponseModel result2 = transactionService.createTransaction(transactionModel);
        assertEquals(expected,result2);


        TransactionModel transactionModel3 = new TransactionModel("Gildo Lanches", 40L, "2019-02-13T10:05:00.000Z");
        map.put(map.size()+1,transactionModel3);
        when(persistence.get()).thenReturn(map);

        AccountResponseModel result3 = transactionService.createTransaction(transactionModel);
        assertEquals(expected,result3);

        TransactionModel transactionModel4 = new TransactionModel("Bar da Veia", 10L, "2019-02-13T10:03:00.000Z");
        map.put(map.size()+1,transactionModel4);
        when(persistence.get()).thenReturn(map);

        expected.setViolations(EnumAccountViolations.HIGH_FREQUENCY_SMALL_INTERVAL);

        AccountResponseModel result4 = transactionService.createTransaction(transactionModel);
        assertEquals(expected,result4);

    }

    @Test
    void createTransactionLimitFailed() {
        AccountModel account2 = new AccountModel(true, 100L);

        AccountResponseModel expected = new AccountResponseModel(account2);
        TransactionModel transactionModel = new TransactionModel("Burger King", 90L, "2019-02-13T10:01:00.000Z");
        map.put(map.size()+1,transactionModel);
        when(persistence.get()).thenReturn(map);

        AccountResponseModel result = transactionService.createTransaction(transactionModel);

        assertEquals(expected,result);

        account2.setAvailableLimit(account2.getAvailableLimit() - 90);

        when(accountService.getAccount(1)).thenReturn(account2);

        TransactionModel transactionModel2 = new TransactionModel("Burger King", 20L, "2019-02-13T10:03:00.000Z");
        map.put(map.size()+1,transactionModel2);

        expected.setViolations(EnumAccountViolations.INSUFFICIENT_LIMIT);

        when(persistence.get()).thenReturn(map);

        AccountResponseModel result2 = transactionService.createTransaction(transactionModel);
        assertEquals(expected,result2);

    }
}