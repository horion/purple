package challenger.purple.persistence.impl;

import challenger.purple.model.AccountModel;
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
    private AccountModel accountModel;
    private Map<Integer,AccountModel> map = new TreeMap<>();

    @BeforeEach
    void init(){
        accountPersistence = mock(AccountPersistenceImpl.class) ;
        accountModel = new AccountModel(true,100L);

    }


    @Test
    void merge() {
        when(accountPersistence.merge(accountModel)).thenReturn(accountModel);
        AccountModel result = accountPersistence.merge(accountModel);
        assertEquals(accountModel,result);
    }

    @Test
    void getById() {
        when(accountPersistence.getById(1)).thenReturn(accountModel);
        AccountModel result = accountPersistence.getById(1);
        assertEquals(accountModel,result);
    }

    @Test
    void get() {
        map.put(1,accountModel);
        when(accountPersistence.get()).thenReturn(map);
        Map<Integer, AccountModel> result =  accountPersistence.get();
        assertEquals(map,result);
    }
}