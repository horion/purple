package challenger.purple.service.impl;

import challenger.purple.model.Account;
import challenger.purple.model.Transaction;
import challenger.purple.model.enums.EnumAccountViolations;
import challenger.purple.model.response.AccountResponse;
import challenger.purple.persistence.impl.TransactionPersistenceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TransactionServiceImplTest {
    private Map<Integer, Transaction> map = new TreeMap<>();
    private TransactionServiceImpl transactionService;
    private Transaction transaction;
    private Account account;
    private TransactionPersistenceImpl persistence;
    private AccountServiceImpl accountService;


    @BeforeEach
    void init(){
        persistence = mock(TransactionPersistenceImpl.class);
        accountService = mock(AccountServiceImpl.class);
        transactionService = new TransactionServiceImpl(persistence, accountService);
        account = new Account(true, 100L);
        when(accountService.getAccount(1)).thenReturn(account);
        transaction = new Transaction("Burger King", 20L, "2019-02-13T10:00:00.000Z");
        when(persistence.merge(eq(transaction))).thenReturn(transaction);
    }

    @Test
    void createTransaction() {
        Transaction transaction = new Transaction("Burger King", 20L, "2019-02-13T10:01:00.000Z");
        map.put(map.size()+1, transaction);

        when(persistence.get()).thenReturn(map);

        Account account = new Account(true, 80L);

        AccountResponse expected = new AccountResponse(account);
        when(accountService.updateLimit(1,20L)).thenReturn(account);
        AccountResponse result = transactionService.createTransaction(transaction);
        assertEquals(expected,result);

    }

    @Test
    void createTransactionSameMerchantFailed() {
        AccountResponse expected = new AccountResponse(account);
        Transaction transaction = new Transaction("Burger King", 20L, "2019-02-13T10:01:00.000Z");
        map.put(map.size()+1, transaction);
        when(persistence.get()).thenReturn(map);

        Account account = new Account(true, 80L);

        when(accountService.updateLimit(1,20L)).thenReturn(account);

        AccountResponse result = transactionService.createTransaction(transaction);
        assertEquals(expected,result);

        Transaction transaction2 = new Transaction("Burger King", 20L, "2019-02-13T10:01:00.000Z");
        map.put(map.size()+1, transaction2);

        Account account2 = new Account(true, 60L);

        when(accountService.updateLimit(1,20L)).thenReturn(account2);

        expected.setViolations(Collections.singletonList(EnumAccountViolations.DOUBLE_TRANSACTION));

        when(persistence.get()).thenReturn(map);

        AccountResponse result2 = transactionService.createTransaction(transaction);
        assertEquals(expected,result2);

    }


    @Test
    void createTransactionSameMerchantTimeSuccess() {
        AccountResponse expected = new AccountResponse(account);
        Transaction transaction = new Transaction("Burger King", 20L, "2019-02-13T10:01:00.000Z");
        map.put(map.size()+1, transaction);
        when(persistence.get()).thenReturn(map);

        Account account = new Account(true, 80L);

        when(accountService.updateLimit(1,20L)).thenReturn(account);

        AccountResponse result = transactionService.createTransaction(transaction);
        assertEquals(expected,result);

        Transaction transaction2 = new Transaction("Burger King", 20L, "2019-02-13T10:03:01.000Z");
        map.put(map.size()+1, transaction2);

        Account account2 = new Account(true, 60L);

        when(accountService.updateLimit(1,20L)).thenReturn(account2);

        when(persistence.get()).thenReturn(map);

        AccountResponse result2 = transactionService.createTransaction(transaction);
        assertEquals(expected,result2);

    }

    @Test
    void createTransactionSameMerchantAmountSuccess() {
        AccountResponse expected = new AccountResponse(account);
        Transaction transaction = new Transaction("Burger King", 30L, "2019-02-13T10:01:00.000Z");
        map.put(map.size()+1, transaction);
        when(persistence.get()).thenReturn(map);

        Account account = new Account(true, 70L);

        when(accountService.updateLimit(1,30L)).thenReturn(account);

        AccountResponse result = transactionService.createTransaction(transaction);
        assertEquals(expected,result);

        Transaction transaction2 = new Transaction("Burger King", 20L, "2019-02-13T10:03:01.000Z");
        map.put(map.size()+1, transaction2);

        when(persistence.get()).thenReturn(map);

        Account account2 = new Account(true, 50L);

        when(accountService.updateLimit(1,20L)).thenReturn(account2);

        AccountResponse result2 = transactionService.createTransaction(transaction);
        assertEquals(expected,result2);

    }


    @Test
    void createTransactionHighFrequecnyFailed() {
        AccountResponse expected = new AccountResponse(account);

        Transaction transaction = new Transaction("Burger King", 20L, "2019-02-13T10:01:01.000Z");
        map.put(map.size()+1, transaction);
        when(persistence.get()).thenReturn(map);

        Account account = new Account(true, 80L);

        when(accountService.updateLimit(1,20L)).thenReturn(account);

        AccountResponse result = transactionService.createTransaction(transaction);
        assertEquals(expected,result);

        Transaction transaction2 = new Transaction("Burger King", 30L, "2019-02-13T10:01:02.000Z");
        map.put(map.size()+1, transaction2);

        when(persistence.get()).thenReturn(map);

        Account account2 = new Account(true, 50L);

        when(accountService.updateLimit(1,20L)).thenReturn(account2);

        AccountResponse result2 = transactionService.createTransaction(transaction);
        assertEquals(expected,result2);


        Transaction transaction3 = new Transaction("Gildo Lanches", 40L, "2019-02-13T10:01:03.000Z");
        map.put(map.size()+1, transaction3);
        when(persistence.get()).thenReturn(map);

        Account account3 = new Account(true, 10L);

        when(accountService.updateLimit(1,40L)).thenReturn(account3);

        AccountResponse result3 = transactionService.createTransaction(transaction);
        assertEquals(expected,result3);

        Transaction transaction4 = new Transaction("Bar da Veia", 10L, "2019-02-13T10:03:00.000Z");
        map.put(map.size()+1, transaction4);
        when(persistence.get()).thenReturn(map);

        Account account4 = new Account(true, 0L);

        when(accountService.updateLimit(1,10L)).thenReturn(account4);


        expected.setViolations(Collections.singletonList(EnumAccountViolations.HIGH_FREQUENCY_SMALL_INTERVAL));

        AccountResponse result4 = transactionService.createTransaction(transaction);
        assertEquals(expected,result4);

    }

    @Test
    void createTransactionLimitFailed() {
        Account account2 = new Account(true, 100L);

        AccountResponse expected = new AccountResponse(account2);
        Transaction transaction = new Transaction("Burger King", 90L, "2019-02-13T10:01:00.000Z");
        map.put(map.size()+1, transaction);
        when(persistence.get()).thenReturn(map);

        Account account4 = new Account(true, 10L);

        when(accountService.updateLimit(1,90L)).thenReturn(account4);

        AccountResponse result = transactionService.createTransaction(transaction);
        expected.setAccount(account4);

        assertEquals(expected,result);

        account2.setAvailableLimit(account2.getAvailableLimit() - 90);

        when(accountService.getAccount(1)).thenReturn(account2);

        Transaction transaction2 = new Transaction("Burger King", 20L, "2019-02-13T10:03:00.000Z");
        map.put(map.size()+1, transaction2);

        expected.setViolations(Collections.singletonList(EnumAccountViolations.INSUFFICIENT_LIMIT));

        when(persistence.get()).thenReturn(map);

        AccountResponse result2 = transactionService.createTransaction(transaction);
        assertEquals(expected,result2);

    }
}