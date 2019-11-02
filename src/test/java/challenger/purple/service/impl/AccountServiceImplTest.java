package challenger.purple.service.impl;

import challenger.purple.model.AccountModel;
import challenger.purple.model.enums.EnumAccountViolations;
import challenger.purple.model.response.AccountResponseModel;
import challenger.purple.persistence.impl.AccountPersistenceImpl;
import challenger.purple.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountServiceImplTest {

    @Mock
    AccountPersistenceImpl accountPersistence;
    private AccountModel accountModel;
    private AccountServiceImpl accountService;


    @BeforeEach
    void init(){
        accountPersistence = mock(AccountPersistenceImpl.class);
        accountModel = new AccountModel(true,100L);
        accountService = new AccountServiceImpl(accountPersistence);
        when(accountPersistence.merge(eq(accountModel))).thenReturn(accountModel);

    }

    @Test
    void save() {
        AccountResponseModel expected = new AccountResponseModel(accountModel);
        AccountResponseModel result = accountService.save(accountModel);
        assertEquals(expected,result);
    }

    @Test
    void saveFailed() {
        AccountResponseModel expected = new AccountResponseModel(accountModel);

        AccountResponseModel expected2 = new AccountResponseModel(accountModel);
        expected2.setViolations(EnumAccountViolations.ACCOUNT_ALREADY_INITIALIZED);


        AccountResponseModel result = accountService.save(accountModel) ;
        assertEquals(expected,result);

        AccountModel accountModel1 = new AccountModel(true,350L);
        when(accountPersistence.getById(eq(1))).thenReturn(accountModel);

        when(accountPersistence.merge(eq(accountModel1))).thenReturn(accountModel1);
        AccountResponseModel result2 = accountService.save(accountModel1);

        assertEquals(expected2,result2);

    }

    @Test
    void getAccount() {
        when(accountPersistence.getById(eq(1))).thenReturn(accountModel);
        AccountModel result = accountService.getAccount(1);
        assertEquals(accountModel,result);
    }

    @Test
    void getAccountFailed() {
        when(accountPersistence.getById(eq(1))).thenReturn(accountModel);
        AccountModel result = accountService.getAccount(2);
        assertNotEquals(accountModel,result);
    }

    @Test
    void saveNotActiveCard() {
        AccountModel accountModel = new AccountModel(false,100L);
        when(accountPersistence.merge(eq(accountModel))).thenReturn(accountModel);
        AccountResponseModel expected = new AccountResponseModel(accountModel);
        AccountResponseModel result = accountService.save(accountModel);
        assertEquals(expected,result);
    }

    @Test
    void saveNotLimitCard() {
        AccountModel accountModel = new AccountModel(true,0L);
        when(accountPersistence.merge(eq(accountModel))).thenReturn(accountModel);
        AccountResponseModel expected = new AccountResponseModel(accountModel);
        AccountResponseModel result = accountService.save(accountModel);
        assertEquals(expected,result);
    }

    @Test
    void saveNotLimitAndNotActiveCard() {
        AccountModel accountModel = new AccountModel(false,0L);
        when(accountPersistence.merge(eq(accountModel))).thenReturn(accountModel);
        AccountResponseModel expected = new AccountResponseModel(accountModel);
        AccountResponseModel result = accountService.save(accountModel);
        assertEquals(expected,result);
    }
}