package challenger.purple.validations;

import challenger.purple.model.Account;
import challenger.purple.model.Transaction;
import challenger.purple.model.enums.EnumAccountViolations;
import challenger.purple.model.response.AccountResponse;
import challenger.purple.validations.transaction.TransactionFrequencySmallInterval;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class TransactionFrequencySmallIntervalTest {
    private TransactionFrequencySmallInterval transactionFrequencySmallInterval;
    private Map<Integer, Transaction> map = new TreeMap<>();
    private Account account;


    @BeforeEach
    void init(){
        transactionFrequencySmallInterval = new TransactionFrequencySmallInterval();
        transactionFrequencySmallInterval.setNextValidtor(new LastValidator());
        account = new Account(true, 100L);
    }


    @Test
    void validationThreeTransactionSeconds() {
        AccountResponse accountResponse = new AccountResponse(account);
        AccountResponse accountResponseExpected = new AccountResponse(account);

        Transaction transaction = new Transaction("Burger King", 30L, "2019-02-13T10:01:01.000Z");
        map.put(1, transaction);

        Transaction transaction2 = new Transaction("Gildo Lanches", 100L, "2019-02-13T10:01:02.000Z");
        map.put(2, transaction2);

        Transaction transaction3 = new Transaction("Bar da Veia", 100L, "2019-02-13T10:01:03.000Z");
        map.put(3, transaction3);

        AccountResponse a = transactionFrequencySmallInterval.validation(accountResponse,map);
        assertEquals(accountResponseExpected,a);

    }

    @Test
    void validation() {
        AccountResponse accountResponse = new AccountResponse(account);
        AccountResponse accountResponseExpected = new AccountResponse(account);

        Transaction transaction = new Transaction("Burger King", 30L, "2019-02-13T10:00:00.000Z");
        map.put(1, transaction);

        Transaction transaction2 = new Transaction("Gildo Lanches", 100L, "2019-02-13T10:01:02.000Z");
        map.put(2, transaction2);

        AccountResponse a = transactionFrequencySmallInterval.validation(accountResponse,map);
        assertEquals(accountResponseExpected,a);

    }

    @Test
    void validationThreeTransaction() {
        AccountResponse accountResponse = new AccountResponse(account);
        AccountResponse accountResponseExpected = new AccountResponse(account);

        Transaction transaction = new Transaction("Burger King", 30L, "2019-02-13T10:00:00.000Z");
        map.put(1, transaction);

        Transaction transaction3 = new Transaction("Bar da Veia", 100L, "2019-02-13T10:01:03.000Z");
        map.put(2, transaction3);

        Transaction transaction2 = new Transaction("Gildo Lanches", 100L, "2019-02-13T10:02:04.000Z");
        map.put(3, transaction2);


        AccountResponse a = transactionFrequencySmallInterval.validation(accountResponse,map);
        assertEquals(accountResponseExpected,a);

    }

    @Test
    void validationFourTransaction() {
        AccountResponse accountResponse = new AccountResponse(account);
        AccountResponse accountResponseExpected = new AccountResponse(account);

        Transaction transaction = new Transaction("Burger King", 30L, "2019-02-13T10:00:00.000Z");
        map.put(1, transaction);

        Transaction transaction3 = new Transaction("Bar da Veia", 100L, "2019-02-13T10:01:03.000Z");
        map.put(2, transaction3);

        Transaction transaction2 = new Transaction("Gildo Lanches", 100L, "2019-02-13T10:02:04.000Z");
        map.put(3, transaction2);

        Transaction transaction4 = new Transaction("Gildo Lanches", 100L, "2019-02-13T10:05:04.000Z");
        map.put(4, transaction4);

        AccountResponse a = transactionFrequencySmallInterval.validation(accountResponse,map);
        assertEquals(accountResponseExpected,a);
    }

    @Test
    void validationFourTransactionFailed() {
        AccountResponse accountResponse = new AccountResponse(account);
        AccountResponse accountResponseExpected = new AccountResponse(account);
        accountResponseExpected.setViolations(Collections.singletonList(EnumAccountViolations.HIGH_FREQUENCY_SMALL_INTERVAL));

        Transaction transaction = new Transaction("Burger King", 30L, "2019-02-13T10:01:00.000Z");
        map.put(1, transaction);

        Transaction transaction3 = new Transaction("Bar da Veia", 100L, "2019-02-13T10:01:03.000Z");
        map.put(2, transaction3);

        Transaction transaction2 = new Transaction("Gildo Lanches", 100L, "2019-02-13T10:02:04.000Z");
        map.put(3, transaction2);

        Transaction transaction4 = new Transaction("Gildo Lanches", 100L, "2019-02-13T10:02:05.000Z");
        map.put(4, transaction4);

        AccountResponse a = transactionFrequencySmallInterval.validation(accountResponse,map);
        assertEquals(accountResponseExpected,a);
    }
}