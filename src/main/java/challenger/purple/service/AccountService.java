package challenger.purple.service;

import challenger.purple.model.AccountModel;
import challenger.purple.model.response.AccountResponseModel;

public interface AccountService {

    AccountResponseModel save(AccountModel accountModel);

    AccountModel getAccount(Integer id);

    AccountModel updateLimit(Integer id, Long amount);

}
