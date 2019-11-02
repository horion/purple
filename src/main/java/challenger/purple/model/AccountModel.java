package challenger.purple.model;

import java.io.Serializable;
import java.util.Objects;

public class AccountModel implements Serializable {
    private static final long serialVersionUID = -5891953921719208748L;

    private boolean activeCard;
    private Long availableLimit;

    public AccountModel(){

    }

    public AccountModel(boolean activeCard, Long availableLimit) {
        this.activeCard = activeCard;
        this.availableLimit = availableLimit;
    }

    public Long getAvailableLimit() {
        return availableLimit;
    }

    public AccountModel setAvailableLimit(Long availableLimit) {
        this.availableLimit = availableLimit;
        return this;
    }

    public boolean isActiveCard() {
        return activeCard;
    }

    public AccountModel setActiveCard(boolean activeCard) {
        this.activeCard = activeCard;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountModel that = (AccountModel) o;
        return activeCard == that.activeCard &&
                Objects.equals(availableLimit, that.availableLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activeCard, availableLimit);
    }
}
