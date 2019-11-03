package challenger.purple.model.response;


import challenger.purple.model.Account;
import challenger.purple.model.enums.EnumAccountViolations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class AccountResponse implements Serializable {

    private static final long serialVersionUID = 1387586689917885258L;
    private boolean activeCard;
    private Long availableLimit;
    private List<EnumAccountViolations> violations;

    public AccountResponse() {
    }

    public Long getAvailableLimit() {
        return availableLimit;
    }

    public AccountResponse setAvailableLimit(Long availableLimit) {
        this.availableLimit = availableLimit;
        return this;
    }

    public boolean isActiveCard() {
        return activeCard;
    }

    public AccountResponse setActiveCard(boolean activeCard) {
        this.activeCard = activeCard;
        return this;
    }


    public List<EnumAccountViolations> getViolations() {
        if(violations == null){
            violations = new ArrayList<>();
        }
        return violations;
    }

    public AccountResponse(Account model){
        this.activeCard = model.isActiveCard();
        this.availableLimit = model.getAvailableLimit();
        this.getViolations();
    }

    public AccountResponse setViolations(EnumAccountViolations violations) {
        this.getViolations().add(violations);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountResponse that = (AccountResponse) o;
        return activeCard == that.activeCard &&
                Objects.equals(violations, that.violations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activeCard, violations);
    }

    @Override
    public String toString() {
        return "{ account: {" +
                "activeCard:" + activeCard +
                ", availableLimit:" + availableLimit +
                '}'+", violations: "+ violations.toString()+"}";
    }
}
