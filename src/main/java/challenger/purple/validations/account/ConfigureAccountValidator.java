package challenger.purple.validations.account;

import challenger.purple.model.Account;
import challenger.purple.model.response.AccountResponse;
import challenger.purple.validations.LastValidator;

public class ConfigureAccountValidator {
    private Account account;
    private AccountResponse accountResponse;

    private AccountCreationValidator accountCreationValidator;

    public ConfigureAccountValidator(Account account,AccountResponse accountResponse){
        accountCreationValidator = new AccountCreationValidator();
        accountCreationValidator.setNextValidator(new LastValidator());
        this.account = account;
        this.accountResponse = accountResponse;
    }

    public AccountResponse validation(){
        return accountCreationValidator.validation(accountResponse,account);
    }




}
