package challenger.purple.model.response;


import challenger.purple.model.Account;
import challenger.purple.model.enums.EnumAccountViolations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class AccountResponse implements Serializable {

    private static final long serialVersionUID = 1387586689917885258L;
    private Account account;
    private List<EnumAccountViolations> violations;

    public AccountResponse() {
    }


    public List<EnumAccountViolations> getViolations() {
        if(this.violations == null)
            this.violations = new ArrayList<>();
        return violations;
    }

    public AccountResponse(Account model){
        this.account = model;
        this.getViolations();
    }

    public AccountResponse setViolations(List<EnumAccountViolations> violations) {
        this.violations = violations;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountResponse that = (AccountResponse) o;
        return this.account.equals(that.account) &&
                Objects.equals(violations, that.violations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, violations);
    }

    @Override
    public String toString() {
        return "{" +
                "account:" + account +
                '}'+", violations: "+ violations.toString()+"}";
    }

    public Account getAccount() {
        return account;
    }

    public AccountResponse setAccount(Account account) {
        this.account = account;
        return this;
    }
}
