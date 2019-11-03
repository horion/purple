package challenger.purple.service.impl;

import challenger.purple.model.Account;
import challenger.purple.model.enums.EnumAccountViolations;
import challenger.purple.model.response.AccountResponse;
import challenger.purple.persistence.impl.AccountPersistenceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountServiceImplTest {

    @Mock
    AccountPersistenceImpl accountPersistence;
    private Account account;
    private AccountServiceImpl accountService;


    @BeforeEach
    void init(){
        accountPersistence = mock(AccountPersistenceImpl.class);
        account = new Account(true,100L);
        accountService = new AccountServiceImpl(accountPersistence);
        when(accountPersistence.merge(eq(account))).thenReturn(account);

    }

    @Test
    void save() {
        AccountResponse expected = new AccountResponse(account);
        AccountResponse result = accountService.save(account);
        assertEquals(expected,result);
    }

    @Test
    void saveFailed() {
        AccountResponse expected = new AccountResponse(account);

        AccountResponse expected2 = new AccountResponse(account);
        expected2.setViolations(EnumAccountViolations.ACCOUNT_ALREADY_INITIALIZED);


        AccountResponse result = accountService.save(account) ;
        assertEquals(expected,result);

        Account account1 = new Account(true,350L);
        when(accountPersistence.getById(eq(1))).thenReturn(account);

        when(accountPersistence.merge(eq(account1))).thenReturn(account1);
        AccountResponse result2 = accountService.save(account1);

        assertEquals(expected2,result2);

    }

    @Test
    void getAccount() {
        when(accountPersistence.getById(eq(1))).thenReturn(account);
        Account result = accountService.getAccount(1);
        assertEquals(account,result);
    }

    @Test
    void getAccountFailed() {
        when(accountPersistence.getById(eq(1))).thenReturn(account);
        Account result = accountService.getAccount(2);
        assertNotEquals(account,result);
    }

    @Test
    void saveNotActiveCard() {
        Account account = new Account(false,100L);
        when(accountPersistence.merge(eq(account))).thenReturn(account);
        AccountResponse expected = new AccountResponse(account);
        AccountResponse result = accountService.save(account);
        assertEquals(expected,result);
    }

    @Test
    void saveNotLimitCard() {
        Account account = new Account(true,0L);
        when(accountPersistence.merge(eq(account))).thenReturn(account);
        AccountResponse expected = new AccountResponse(account);
        AccountResponse result = accountService.save(account);
        assertEquals(expected,result);
    }

    @Test
    void saveNotLimitAndNotActiveCard() {
        Account account = new Account(false,0L);
        when(accountPersistence.merge(eq(account))).thenReturn(account);
        AccountResponse expected = new AccountResponse(account);
        AccountResponse result = accountService.save(account);
        assertEquals(expected,result);
    }

    @Test
    void updateLimit(){
        Account account = new Account(true,100L);
        when(accountService.getAccount(1)).thenReturn(account);
        Account expected = new Account(true,30L);
        when(accountPersistence.merge(eq(account))).thenReturn(expected);

        Account result = accountService.updateLimit(1,70L);
        assertEquals(expected,result);

    }


    @Test
    void updateLimitFailed(){
        Account account = new Account(true,100L);
        when(accountService.getAccount(1)).thenReturn(account);
        Account expected = new Account(true,0L);
        when(accountPersistence.merge(eq(account))).thenReturn(expected);

        Account result = accountService.updateLimit(1,110L);
        assertEquals(expected,result);

    }
}