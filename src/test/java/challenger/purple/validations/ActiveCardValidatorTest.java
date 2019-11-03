package challenger.purple.validations;

import challenger.purple.model.Account;
import challenger.purple.model.Transaction;
import challenger.purple.model.enums.EnumAccountViolations;
import challenger.purple.model.response.AccountResponse;
import challenger.purple.validations.transaction.ActiveCardValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ActiveCardValidatorTest {
    private ActiveCardValidator activeCardValidator;
    private Map<Integer, Transaction> map = new TreeMap<>();

    @BeforeEach
    void init(){
        activeCardValidator = new ActiveCardValidator();
        activeCardValidator.setNextValidtor(new LastValidator());
        Transaction transaction = new Transaction("Burger King", 20L, "2019-02-13T10:00:00.000Z");
        map.put(1, transaction);
    }

    @Test
    void validation() {
        Account account = new Account(true, 100L);
        AccountResponse accountResponse = new AccountResponse(account);
        AccountResponse accountResponseExpected = new AccountResponse(account);

        AccountResponse a = activeCardValidator.validation(accountResponse,map);
        assertEquals(accountResponseExpected,a);

    }

    @Test
    void validationCardNotActive() {
        Account account = new Account(false, 100L);
        AccountResponse accountResponse = new AccountResponse(account);
        AccountResponse accountResponseExpected = new AccountResponse(account);
        accountResponseExpected.setViolations(Collections.singletonList(EnumAccountViolations.CARD_NOT_ACTIVE));


        AccountResponse a = activeCardValidator.validation(accountResponse,map);
        assertEquals(accountResponseExpected,a);

    }

    @Test
    void validationCardNotActiveFailed() {
        Account account = new Account(false, 100L);
        AccountResponse accountResponse = new AccountResponse(account);
        AccountResponse accountResponseExpected = new AccountResponse(account);

        AccountResponse a = activeCardValidator.validation(accountResponse,map);
        assertNotEquals(accountResponseExpected,a);

    }

    @Test
    void validationActiveCardFailed() {
        Account account = new Account(true, 100L);
        AccountResponse accountResponse = new AccountResponse(account);
        AccountResponse accountResponseExpected = new AccountResponse(account);
        accountResponseExpected.setViolations(Collections.singletonList(EnumAccountViolations.CARD_NOT_ACTIVE));

        AccountResponse a = activeCardValidator.validation(accountResponse,map);
        assertNotEquals(accountResponseExpected,a);

    }


}