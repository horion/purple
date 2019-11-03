package challenger.purple.validations;

import challenger.purple.model.Account;
import challenger.purple.model.Transaction;
import challenger.purple.model.enums.EnumAccountViolations;
import challenger.purple.model.response.AccountResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class LimitCardValidatorTest {

    private LimitCardValidator limitCard;
    private Map<Integer, Transaction> map = new TreeMap<>();
    private Account account;


    @BeforeEach
    void init(){
        limitCard = new LimitCardValidator();
        limitCard.setNextValidtor(new LastValidator());
        account = new Account(true, 100L);
    }


    @Test
    void validation() {
        AccountResponse accountResponse = new AccountResponse(account);
        AccountResponse accountResponseExpected = new AccountResponse(account);
        Transaction transaction = new Transaction("Burger King", 20L, "2019-02-13T10:00:00.000Z");
        map.put(1, transaction);

        AccountResponse a = limitCard.validation(accountResponse,map);
        assertEquals(accountResponseExpected,a);
    }

    @Test
    void validationInsufficientLimit() {
        AccountResponse accountResponse = new AccountResponse(account);
        AccountResponse accountResponseExpected = new AccountResponse(account);
        accountResponseExpected.setViolations(EnumAccountViolations.INSUFFICIENT_LIMIT);
        Transaction transaction = new Transaction("Burger King", 110L, "2019-02-13T10:00:00.000Z");
        map.put(1, transaction);

        AccountResponse a = limitCard.validation(accountResponse,map);
        assertEquals(accountResponseExpected,a);
    }

    @Test
    void validationEqualLimit() {
        AccountResponse accountResponse = new AccountResponse(account);
        AccountResponse accountResponseExpected = new AccountResponse(account);
        Transaction transaction = new Transaction("Burger King", 100L, "2019-02-13T10:00:00.000Z");
        map.put(1, transaction);

        AccountResponse a = limitCard.validation(accountResponse,map);
        assertEquals(accountResponseExpected,a);
    }
    @Test
    void validationZeroLimit() {
        Account account = new Account(true, 0L);
        AccountResponse accountResponse = new AccountResponse(account);
        AccountResponse accountResponseExpected = new AccountResponse(account);
        accountResponseExpected.setViolations(EnumAccountViolations.INSUFFICIENT_LIMIT);

        Transaction transaction = new Transaction("Burger King", 100L, "2019-02-13T10:00:00.000Z");
        map.put(1, transaction);

        AccountResponse a = limitCard.validation(accountResponse,map);
        assertEquals(accountResponseExpected,a);
    }

    @Test
    void validationZeroTransaction() {
        AccountResponse accountResponse = new AccountResponse(account);
        AccountResponse accountResponseExpected = new AccountResponse(account);

        Transaction transaction = new Transaction("Burger King", 0L, "2019-02-13T10:00:00.000Z");
        map.put(1, transaction);

        AccountResponse a = limitCard.validation(accountResponse,map);
        assertEquals(accountResponseExpected,a);
    }

    @Test
    void validationZeroTransactionAndZeroLimit() {
        Account account = new Account(true, 0L);
        AccountResponse accountResponse = new AccountResponse(account);
        AccountResponse accountResponseExpected = new AccountResponse(account);

        Transaction transaction = new Transaction("Burger King", 0L, "2019-02-13T10:00:00.000Z");
        map.put(1, transaction);

        AccountResponse a = limitCard.validation(accountResponse,map);
        assertEquals(accountResponseExpected,a);
    }

    @Test
    void validationTwoTransactionFailed() {
        Account account = new Account(true, 100L);
        AccountResponse accountResponse = new AccountResponse(account);
        AccountResponse accountResponseExpected = new AccountResponse(account);

        Transaction transaction = new Transaction("Burger King", 30L, "2019-02-13T10:00:00.000Z");
        map.put(1, transaction);

        AccountResponse a = limitCard.validation(accountResponse,map);
        assertEquals(accountResponseExpected,a);

        account.setAvailableLimit(a.getAvailableLimit() - transaction.getAmount());
        accountResponse = new AccountResponse(account);

        Transaction transaction2 = new Transaction("Gildo Lanches", 100L, "2019-02-13T10:00:00.000Z");
        map.put(2, transaction2);

        AccountResponse a2 = limitCard.validation(accountResponse,map);

        assertNotEquals(accountResponseExpected,a2);

    }

    @Test
    void validationTwoTransaction() {
        Account account = new Account(true, 100L);
        AccountResponse accountResponse = new AccountResponse(account);
        AccountResponse accountResponseExpected = new AccountResponse(account);

        Transaction transaction = new Transaction("Burger King", 30L, "2019-02-13T10:00:00.000Z");
        map.put(1, transaction);

        AccountResponse a = limitCard.validation(accountResponse,map);
        assertEquals(accountResponseExpected,a);

        account.setAvailableLimit(a.getAvailableLimit() - transaction.getAmount());
        accountResponse = new AccountResponse(account);

        Transaction transaction2 = new Transaction("Gildo Lanches", 70L, "2019-02-13T10:00:00.000Z");
        map.put(2, transaction2);

        AccountResponse a2 = limitCard.validation(accountResponse,map);

        assertEquals(accountResponseExpected,a2);

    }

}