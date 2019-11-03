package challenger.purple.persistence.impl;

import challenger.purple.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AccountPersistenceImplTest {

    AccountPersistenceImpl accountPersistence;
    private Account account;
    private Map<Integer, Account> map = new TreeMap<>();

    @BeforeEach
    void init(){
        accountPersistence = mock(AccountPersistenceImpl.class) ;
        account = new Account(true,100L);

    }


    @Test
    void merge() {
        when(accountPersistence.merge(account)).thenReturn(account);
        Account result = accountPersistence.merge(account);
        assertEquals(account,result);
    }

    @Test
    void getById() {
        when(accountPersistence.getById(1)).thenReturn(account);
        Account result = accountPersistence.getById(1);
        assertEquals(account,result);
    }

    @Test
    void get() {
        map.put(1, account);
        when(accountPersistence.get()).thenReturn(map);
        Map<Integer, Account> result =  accountPersistence.get();
        assertEquals(map,result);
    }
}