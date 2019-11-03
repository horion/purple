package challenger.purple.validations.account;

import challenger.purple.model.Account;
import challenger.purple.model.enums.EnumAccountViolations;
import challenger.purple.model.response.AccountResponse;
import challenger.purple.validations.Validations;

public class AccountCreationValidator implements Validations<AccountResponse,Account> {

    private Validations nextValidator;

    @Override
    public AccountResponse validation(AccountResponse accountResponse,Account account) {
        if(accountResponse.getAccount() != null){
            accountResponse.setViolations(EnumAccountViolations.ACCOUNT_ALREADY_INITIALIZED);
            return accountResponse;
        }
        accountResponse.setAccount(account);
        return nextValidator.validation(accountResponse,account);
    }

    public AccountCreationValidator setNextValidator(Validations nextValidator) {
        this.nextValidator = nextValidator;
        return this;
    }
}
