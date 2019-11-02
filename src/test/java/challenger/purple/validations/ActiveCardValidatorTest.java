package challenger.purple.validations;

import challenger.purple.model.AccountModel;
import challenger.purple.model.TransactionModel;
import challenger.purple.model.enums.EnumAccountViolations;
import challenger.purple.model.response.AccountResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ActiveCardValidatorTest {
    private ActiveCardValidator activeCardValidator;
    private Map<Integer,TransactionModel> map = new TreeMap<>();

    @BeforeEach
    void init(){
        activeCardValidator = new ActiveCardValidator();
        activeCardValidator.setNextValidtor(new LastValidator());
        TransactionModel transactionModel = new TransactionModel("Burger King", 20L, "2019-02-13T10:00:00.000Z");
        map.put(1, transactionModel);
    }

    @Test
    void validation() {
        AccountModel accountModel = new AccountModel(true, 100L);
        AccountResponseModel accountResponseModel = new AccountResponseModel(accountModel);
        AccountResponseModel accountResponseExpected = new AccountResponseModel(accountModel);

        AccountResponseModel a = activeCardValidator.validation(accountResponseModel,map);
        assertEquals(accountResponseExpected,a);

    }

    @Test
    void validationCardNotActive() {
        AccountModel accountModel = new AccountModel(false, 100L);
        AccountResponseModel accountResponseModel = new AccountResponseModel(accountModel);
        AccountResponseModel accountResponseExpected = new AccountResponseModel(accountModel);
        accountResponseExpected.setViolations(EnumAccountViolations.CARD_NOT_ACTIVE);


        AccountResponseModel a = activeCardValidator.validation(accountResponseModel,map);
        assertEquals(accountResponseExpected,a);

    }

    @Test
    void validationCardNotActiveFailed() {
        AccountModel accountModel = new AccountModel(false, 100L);
        AccountResponseModel accountResponseModel = new AccountResponseModel(accountModel);
        AccountResponseModel accountResponseExpected = new AccountResponseModel(accountModel);

        AccountResponseModel a = activeCardValidator.validation(accountResponseModel,map);
        assertNotEquals(accountResponseExpected,a);

    }

    @Test
    void validationActiveCardFailed() {
        AccountModel accountModel = new AccountModel(true, 100L);
        AccountResponseModel accountResponseModel = new AccountResponseModel(accountModel);
        AccountResponseModel accountResponseExpected = new AccountResponseModel(accountModel);
        accountResponseExpected.setViolations(EnumAccountViolations.CARD_NOT_ACTIVE);

        AccountResponseModel a = activeCardValidator.validation(accountResponseModel,map);
        assertNotEquals(accountResponseExpected,a);

    }


}