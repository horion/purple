package challenger.purple.service;

import challenger.purple.model.Account;
import challenger.purple.model.response.AccountResponse;

public interface AccountService {

    AccountResponse save(Account account);

    Account getAccount(Integer id);

    Account updateLimit(Integer id, Long amount);

}
