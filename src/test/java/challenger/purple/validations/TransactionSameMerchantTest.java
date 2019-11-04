package challenger.purple.validations;

import challenger.purple.model.Account;
import challenger.purple.model.Transaction;
import challenger.purple.model.enums.EnumAccountViolations;
import challenger.purple.model.response.AccountResponse;
import challenger.purple.validations.transaction.TransactionSameMerchant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionSameMerchantTest {

    private TransactionSameMerchant transactionSameMerchant;
    private Map<Integer, Transaction> map = new TreeMap<>();
    private Account account;


    @BeforeEach
    void init(){
        transactionSameMerchant = new TransactionSameMerchant();
        transactionSameMerchant.setNextValidator(new LastValidator());
        account = new Account(true, 100L);
    }


    @Test
    void validation() {
        AccountResponse accountResponse = new AccountResponse(account);
        AccountResponse accountResponseExpected = new AccountResponse(account);

        Transaction transaction = new Transaction("Burger King", 30L, "2019-02-13T10:01:01.000Z");
        map.put(1, transaction);

        Transaction transaction2 = new Transaction("Gildo Lanches", 100L, "2019-02-13T10:03:02.000Z");
        map.put(2, transaction2);

        Transaction transaction3 = new Transaction("Gildo Lanches", 100L, "2019-02-13T10:05:03.000Z");
        map.put(3, transaction3);

        AccountResponse a2 = transactionSameMerchant.validation(accountResponse,map);
        assertEquals(accountResponseExpected,a2);
    }

    @Test
    void validationFailed() {
        AccountResponse accountResponse = new AccountResponse(account);
        AccountResponse accountResponseExpected = new AccountResponse(account);

        /*{ "transaction": { "merchant": "Habbib's", "amount": 30, "time": "2019-02-13T11:00:00.000Z" } }
        { "transaction": { "merchant": "Habbib's", "amount": 30, "time": "2019-02-13T11:00:00.000Z" } }
        { "transaction": { "merchant": "Habbib's", "amount": 25, "time": "2019-02-13T11:00:00.000Z" } }*/

        Transaction transaction = new Transaction("Habbib's", 30L, "2019-02-13T11:00:00.000Z");
        map.put(1, transaction);

        AccountResponse a2 = transactionSameMerchant.validation(accountResponse,map);
        assertEquals(accountResponseExpected,a2);

        accountResponseExpected.setViolations(Collections.singletonList(EnumAccountViolations.DOUBLE_TRANSACTION));

        Transaction transaction2 = new Transaction("Habbib's", 30L, "2019-02-13T11:00:00.000Z");
        map.put(2, transaction2);

        a2 = transactionSameMerchant.validation(accountResponse,map);
        assertEquals(accountResponseExpected,a2);

        Transaction transaction3 = new Transaction("Habbib's", 25L, "2019-02-13T11:00:00.000Z");
        map.put(3, transaction3);

        accountResponseExpected.setViolations(new ArrayList<>());

        a2 = transactionSameMerchant.validation(accountResponse,map);
        assertEquals(accountResponseExpected,a2);

        Transaction transaction4 = new Transaction("Burger King", 30L, "2019-02-13T10:02:00.000Z");
        map.put(4, transaction4);

        a2 = transactionSameMerchant.validation(accountResponse,map);
        assertEquals(accountResponseExpected,a2);
    }

    @Test
    void validationAmountDifferentSuccess() {
        AccountResponse accountResponse = new AccountResponse(account);
        AccountResponse accountResponseExpected = new AccountResponse(account);

        Transaction transaction = new Transaction("Burger King", 30L, "2019-02-13T10:01:00.000Z");
        map.put(1, transaction);

        Transaction transaction2 = new Transaction("Burger King", 100L, "2019-02-13T10:01:00.000Z");
        map.put(2, transaction2);

        AccountResponse a2 = transactionSameMerchant.validation(accountResponse,map);
        assertEquals(accountResponseExpected,a2);
    }

    @Test
    void validationTimeDifferentSuccess() {
        AccountResponse accountResponse = new AccountResponse(account);
        AccountResponse accountResponseExpected = new AccountResponse(account);
        accountResponseExpected.setViolations(Collections.singletonList(EnumAccountViolations.DOUBLE_TRANSACTION));


        Transaction transaction = new Transaction("Burger King", 100L, "2019-02-13T10:01:02.000Z");
        map.put(1, transaction);

        Transaction transaction2 = new Transaction("Burger King", 100L, "2019-02-13T10:01:00.000Z");
        map.put(2, transaction2);

        AccountResponse a2 = transactionSameMerchant.validation(accountResponse,map);
        assertEquals(accountResponseExpected,a2);
    }

    @Test
    void validationMerchantDifferentSuccess() {
        AccountResponse accountResponse = new AccountResponse(account);
        AccountResponse accountResponseExpected = new AccountResponse(account);


        Transaction transaction = new Transaction("Bar da Veia", 100L, "2019-02-13T10:01:02.000Z");
        map.put(1, transaction);

        Transaction transaction2 = new Transaction("Burger King", 100L, "2019-02-13T10:01:00.000Z");
        map.put(2, transaction2);

        AccountResponse a2 = transactionSameMerchant.validation(accountResponse,map);
        assertEquals(accountResponseExpected,a2);
    }
}