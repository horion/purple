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

class TransactionFrequencySmallIntervalTest {
    private TransactionFrequencySmallInterval transactionFrequencySmallInterval;
    private Map<Integer,TransactionModel> map = new TreeMap<>();
    private AccountModel accountModel;


    @BeforeEach
    void init(){
        transactionFrequencySmallInterval = new TransactionFrequencySmallInterval();
        transactionFrequencySmallInterval.setNextValidtor(new LastValidator());
        accountModel = new AccountModel(true, 100L);
    }


    @Test
    void validationThreeTransactionSeconds() {
        AccountResponseModel accountResponseModel = new AccountResponseModel(accountModel);
        AccountResponseModel accountResponseExpected = new AccountResponseModel(accountModel);

        TransactionModel transactionModel = new TransactionModel("Burger King", 30L, "2019-02-13T10:01:01.000Z");
        map.put(1, transactionModel);

        TransactionModel transactionModel2 = new TransactionModel("Gildo Lanches", 100L, "2019-02-13T10:01:02.000Z");
        map.put(2, transactionModel2);

        TransactionModel transactionModel3 = new TransactionModel("Bar da Veia", 100L, "2019-02-13T10:01:03.000Z");
        map.put(3, transactionModel3);

        AccountResponseModel a = transactionFrequencySmallInterval.validation(accountResponseModel,map);
        assertEquals(accountResponseExpected,a);

    }

    @Test
    void validation() {
        AccountResponseModel accountResponseModel = new AccountResponseModel(accountModel);
        AccountResponseModel accountResponseExpected = new AccountResponseModel(accountModel);

        TransactionModel transactionModel = new TransactionModel("Burger King", 30L, "2019-02-13T10:00:00.000Z");
        map.put(1, transactionModel);

        TransactionModel transactionModel2 = new TransactionModel("Gildo Lanches", 100L, "2019-02-13T10:01:02.000Z");
        map.put(2, transactionModel2);

        AccountResponseModel a = transactionFrequencySmallInterval.validation(accountResponseModel,map);
        assertEquals(accountResponseExpected,a);

    }

    @Test
    void validationThreeTransaction() {
        AccountResponseModel accountResponseModel = new AccountResponseModel(accountModel);
        AccountResponseModel accountResponseExpected = new AccountResponseModel(accountModel);

        TransactionModel transactionModel = new TransactionModel("Burger King", 30L, "2019-02-13T10:00:00.000Z");
        map.put(1, transactionModel);

        TransactionModel transactionModel3 = new TransactionModel("Bar da Veia", 100L, "2019-02-13T10:01:03.000Z");
        map.put(2, transactionModel3);

        TransactionModel transactionModel2 = new TransactionModel("Gildo Lanches", 100L, "2019-02-13T10:02:04.000Z");
        map.put(3, transactionModel2);


        AccountResponseModel a = transactionFrequencySmallInterval.validation(accountResponseModel,map);
        assertEquals(accountResponseExpected,a);

    }

    @Test
    void validationFourTransaction() {
        AccountResponseModel accountResponseModel = new AccountResponseModel(accountModel);
        AccountResponseModel accountResponseExpected = new AccountResponseModel(accountModel);

        TransactionModel transactionModel = new TransactionModel("Burger King", 30L, "2019-02-13T10:00:00.000Z");
        map.put(1, transactionModel);

        TransactionModel transactionModel3 = new TransactionModel("Bar da Veia", 100L, "2019-02-13T10:01:03.000Z");
        map.put(2, transactionModel3);

        TransactionModel transactionModel2 = new TransactionModel("Gildo Lanches", 100L, "2019-02-13T10:02:04.000Z");
        map.put(3, transactionModel2);

        TransactionModel transactionModel4 = new TransactionModel("Gildo Lanches", 100L, "2019-02-13T10:05:04.000Z");
        map.put(4, transactionModel4);

        AccountResponseModel a = transactionFrequencySmallInterval.validation(accountResponseModel,map);
        assertEquals(accountResponseExpected,a);
    }

    @Test
    void validationFourTransactionFailed() {
        AccountResponseModel accountResponseModel = new AccountResponseModel(accountModel);
        AccountResponseModel accountResponseExpected = new AccountResponseModel(accountModel);
        accountResponseExpected.setViolations(EnumAccountViolations.HIGH_FREQUENCY_SMALL_INTERVAL);

        TransactionModel transactionModel = new TransactionModel("Burger King", 30L, "2019-02-13T10:01:00.000Z");
        map.put(1, transactionModel);

        TransactionModel transactionModel3 = new TransactionModel("Bar da Veia", 100L, "2019-02-13T10:01:03.000Z");
        map.put(2, transactionModel3);

        TransactionModel transactionModel2 = new TransactionModel("Gildo Lanches", 100L, "2019-02-13T10:02:04.000Z");
        map.put(3, transactionModel2);

        TransactionModel transactionModel4 = new TransactionModel("Gildo Lanches", 100L, "2019-02-13T10:02:05.000Z");
        map.put(4, transactionModel4);

        AccountResponseModel a = transactionFrequencySmallInterval.validation(accountResponseModel,map);
        assertEquals(accountResponseExpected,a);
    }
}