package challenger.purple.model.enums;

public enum EnumAccountViolations {

    ACCOUNT_ALREADY_INITIALIZED("account-already-initialized"),INSUFFICIENT_LIMIT("insufficient-limit"),CARD_NOT_ACTIVE("card-not-active")
    ,HIGH_FREQUENCY_SMALL_INTERVAL("high-frequency-small-interval"),DOUBLE_TRANSACTION("double-transaction");
    private String value;


    EnumAccountViolations(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public EnumAccountViolations setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return value;
    }
}
