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

import static org.junit.jupiter.api.Assertions.*;

class LimitCardValidatorTest {

    private LimitCardValidator limitCard;
    private Map<Integer,TransactionModel> map = new TreeMap<>();
    private AccountModel accountModel;


    @BeforeEach
    void init(){
        limitCard = new LimitCardValidator();
        limitCard.setNextValidtor(new LastValidator());
        accountModel = new AccountModel(true, 100L);
    }


    @Test
    void validation() {
        AccountResponseModel accountResponseModel = new AccountResponseModel(accountModel,null);
        AccountResponseModel accountResponseExpected = new AccountResponseModel(accountModel,null);
        TransactionModel transactionModel = new TransactionModel("Burger King", 20L, "2019-02-13T10:00:00.000Z");
        map.put(1, transactionModel);

        AccountResponseModel a = limitCard.validation(accountResponseModel,map);
        assertEquals(accountResponseExpected,a);
    }

    @Test
    void validationInsufficientLimit() {
        AccountResponseModel accountResponseModel = new AccountResponseModel(accountModel,null);
        AccountResponseModel accountResponseExpected = new AccountResponseModel(accountModel,null);
        accountResponseExpected.setViolations(EnumAccountViolations.INSUFFICIENT_LIMIT);
        TransactionModel transactionModel = new TransactionModel("Burger King", 110L, "2019-02-13T10:00:00.000Z");
        map.put(1, transactionModel);

        AccountResponseModel a = limitCard.validation(accountResponseModel,map);
        assertEquals(accountResponseExpected,a);
    }

    @Test
    void validationEqualLimit() {
        AccountResponseModel accountResponseModel = new AccountResponseModel(accountModel,null);
        AccountResponseModel accountResponseExpected = new AccountResponseModel(accountModel,null);
        TransactionModel transactionModel = new TransactionModel("Burger King", 100L, "2019-02-13T10:00:00.000Z");
        map.put(1, transactionModel);

        AccountResponseModel a = limitCard.validation(accountResponseModel,map);
        assertEquals(accountResponseExpected,a);
    }
    @Test
    void validationZeroLimit() {
        AccountModel accountModel = new AccountModel(true, 0L);
        AccountResponseModel accountResponseModel = new AccountResponseModel(accountModel,null);
        AccountResponseModel accountResponseExpected = new AccountResponseModel(accountModel,null);
        accountResponseExpected.setViolations(EnumAccountViolations.INSUFFICIENT_LIMIT);

        TransactionModel transactionModel = new TransactionModel("Burger King", 100L, "2019-02-13T10:00:00.000Z");
        map.put(1, transactionModel);

        AccountResponseModel a = limitCard.validation(accountResponseModel,map);
        assertEquals(accountResponseExpected,a);
    }

    @Test
    void validationZeroTransaction() {
        AccountResponseModel accountResponseModel = new AccountResponseModel(accountModel,null);
        AccountResponseModel accountResponseExpected = new AccountResponseModel(accountModel,null);

        TransactionModel transactionModel = new TransactionModel("Burger King", 0L, "2019-02-13T10:00:00.000Z");
        map.put(1, transactionModel);

        AccountResponseModel a = limitCard.validation(accountResponseModel,map);
        assertEquals(accountResponseExpected,a);
    }

    @Test
    void validationZeroTransactionAndZeroLimit() {
        AccountModel accountModel = new AccountModel(true, 0L);
        AccountResponseModel accountResponseModel = new AccountResponseModel(accountModel,null);
        AccountResponseModel accountResponseExpected = new AccountResponseModel(accountModel,null);

        TransactionModel transactionModel = new TransactionModel("Burger King", 0L, "2019-02-13T10:00:00.000Z");
        map.put(1, transactionModel);

        AccountResponseModel a = limitCard.validation(accountResponseModel,map);
        assertEquals(accountResponseExpected,a);
    }

    @Test
    void validationTwoTransactionFailed() {
        AccountModel accountModel = new AccountModel(true, 100L);
        AccountResponseModel accountResponseModel = new AccountResponseModel(accountModel,null);
        AccountResponseModel accountResponseExpected = new AccountResponseModel(accountModel,null);

        TransactionModel transactionModel = new TransactionModel("Burger King", 30L, "2019-02-13T10:00:00.000Z");
        map.put(1, transactionModel);

        AccountResponseModel a = limitCard.validation(accountResponseModel,map);
        assertEquals(accountResponseExpected,a);

        accountModel.setAvailableLimit(a.getAvailableLimit() - transactionModel.getAmount());
        accountResponseModel = new AccountResponseModel(accountModel,null);

        TransactionModel transactionModel2 = new TransactionModel("Gildo Lanches", 100L, "2019-02-13T10:00:00.000Z");
        map.put(2, transactionModel2);

        AccountResponseModel a2 = limitCard.validation(accountResponseModel,map);

        assertNotEquals(accountResponseExpected,a2);

    }

    @Test
    void validationTwoTransaction() {
        AccountModel accountModel = new AccountModel(true, 100L);
        AccountResponseModel accountResponseModel = new AccountResponseModel(accountModel,null);
        AccountResponseModel accountResponseExpected = new AccountResponseModel(accountModel,null);

        TransactionModel transactionModel = new TransactionModel("Burger King", 30L, "2019-02-13T10:00:00.000Z");
        map.put(1, transactionModel);

        AccountResponseModel a = limitCard.validation(accountResponseModel,map);
        assertEquals(accountResponseExpected,a);

        accountModel.setAvailableLimit(a.getAvailableLimit() - transactionModel.getAmount());
        accountResponseModel = new AccountResponseModel(accountModel,null);

        TransactionModel transactionModel2 = new TransactionModel("Gildo Lanches", 70L, "2019-02-13T10:00:00.000Z");
        map.put(2, transactionModel2);

        AccountResponseModel a2 = limitCard.validation(accountResponseModel,map);

        assertEquals(accountResponseExpected,a2);

    }

}