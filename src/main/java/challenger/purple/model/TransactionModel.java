package challenger.purple.model;

import java.io.Serializable;
import java.util.Objects;

public class TransactionModel implements Serializable {
    private static final long serialVersionUID = -6966027411603573620L;

    private String merchant;
    private Long amount;
    private String time;

    public TransactionModel(String merchant, Long amount, String time) {
        this.merchant = merchant;
        this.amount = amount;
        this.time = time;
    }

    public TransactionModel() {
    }

    public String getMerchant() {
        return merchant;
    }

    public TransactionModel setMerchant(String merchant) {
        this.merchant = merchant;
        return this;
    }

    public Long getAmount() {
        return amount;
    }

    public TransactionModel setAmount(Long amount) {
        this.amount = amount;
        return this;
    }

    public String getTime() {
        return time;
    }

    public TransactionModel setTime(String time) {
        this.time = time;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionModel that = (TransactionModel) o;
        return Objects.equals(merchant, that.merchant) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(merchant, amount);
    }
}
