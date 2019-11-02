package challenger.purple.validations;

import challenger.purple.model.AccountModel;
import challenger.purple.model.TransactionModel;
import challenger.purple.model.enums.EnumAccountViolations;
import challenger.purple.model.response.AccountResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionSameMerchantTest {

    private TransactionSameMerchant transactionSameMerchant;
    private Map<Integer,TransactionModel> map = new TreeMap<>();
    private AccountModel accountModel;


    @BeforeEach
    void init(){
        transactionSameMerchant = new TransactionSameMerchant();
        transactionSameMerchant.setNextValidtor(new LastValidator());
        accountModel = new AccountModel(true, 100L);
    }


    @Test
    void validation() {
        AccountResponseModel accountResponseModel = new AccountResponseModel(accountModel);
        AccountResponseModel accountResponseExpected = new AccountResponseModel(accountModel);

        TransactionModel transactionModel = new TransactionModel("Burger King", 30L, "2019-02-13T10:01:01.000Z");
        map.put(1, transactionModel);

        TransactionModel transactionModel2 = new TransactionModel("Gildo Lanches", 100L, "2019-02-13T10:03:02.000Z");
        map.put(2, transactionModel2);

        TransactionModel transactionModel3 = new TransactionModel("Gildo Lanches", 100L, "2019-02-13T10:05:03.000Z");
        map.put(3, transactionModel3);

        AccountResponseModel a2 = transactionSameMerchant.validation(accountResponseModel,map);
        assertEquals(accountResponseExpected,a2);
    }

    @Test
    void validationFailed() {
        AccountResponseModel accountResponseModel = new AccountResponseModel(accountModel);
        AccountResponseModel accountResponseExpected = new AccountResponseModel(accountModel);
        accountResponseExpected.setViolations(EnumAccountViolations.DOUBLE_TRANSACTION);

        TransactionModel transactionModel = new TransactionModel("Burger King", 30L, "2019-02-13T10:01:01.000Z");
        map.put(1, transactionModel);

        TransactionModel transactionModel2 = new TransactionModel("Gildo Lanches", 100L, "2019-02-13T10:01:02.000Z");
        map.put(2, transactionModel2);

        TransactionModel transactionModel3 = new TransactionModel("Gildo Lanches", 100L, "2019-02-13T10:01:03.000Z");
        map.put(3, transactionModel3);

        TransactionModel transactionModel4 = new TransactionModel("Burger King", 30L, "2019-02-13T10:02:00.000Z");
        map.put(4, transactionModel4);

        AccountResponseModel a2 = transactionSameMerchant.validation(accountResponseModel,map);
        assertEquals(accountResponseExpected,a2);
    }

    @Test
    void validationAmountDifferentSuccess() {
        AccountResponseModel accountResponseModel = new AccountResponseModel(accountModel);
        AccountResponseModel accountResponseExpected = new AccountResponseModel(accountModel);

        TransactionModel transactionModel = new TransactionModel("Burger King", 30L, "2019-02-13T10:01:00.000Z");
        map.put(1, transactionModel);

        TransactionModel transactionModel2 = new TransactionModel("Burger King", 100L, "2019-02-13T10:01:00.000Z");
        map.put(2, transactionModel2);

        AccountResponseModel a2 = transactionSameMerchant.validation(accountResponseModel,map);
        assertEquals(accountResponseExpected,a2);
    }

    @Test
    void validationTimeDifferentSuccess() {
        AccountResponseModel accountResponseModel = new AccountResponseModel(accountModel);
        AccountResponseModel accountResponseExpected = new AccountResponseModel(accountModel);
        accountResponseExpected.setViolations(EnumAccountViolations.DOUBLE_TRANSACTION);


        TransactionModel transactionModel = new TransactionModel("Burger King", 100L, "2019-02-13T10:01:02.000Z");
        map.put(1, transactionModel);

        TransactionModel transactionModel2 = new TransactionModel("Burger King", 100L, "2019-02-13T10:01:00.000Z");
        map.put(2, transactionModel2);

        AccountResponseModel a2 = transactionSameMerchant.validation(accountResponseModel,map);
        assertEquals(accountResponseExpected,a2);
    }

    @Test
    void validationMerchantDifferentSuccess() {
        AccountResponseModel accountResponseModel = new AccountResponseModel(accountModel);
        AccountResponseModel accountResponseExpected = new AccountResponseModel(accountModel);


        TransactionModel transactionModel = new TransactionModel("Bar da Veia", 100L, "2019-02-13T10:01:02.000Z");
        map.put(1, transactionModel);

        TransactionModel transactionModel2 = new TransactionModel("Burger King", 100L, "2019-02-13T10:01:00.000Z");
        map.put(2, transactionModel2);

        AccountResponseModel a2 = transactionSameMerchant.validation(accountResponseModel,map);
        assertEquals(accountResponseExpected,a2);
    }
}