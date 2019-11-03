package challenger.purple.model.event;

import challenger.purple.model.Transaction;
import org.springframework.context.ApplicationEvent;


public class TransactionEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1710798748565490907L;
    private Transaction transaction;

    public TransactionEvent(Object source, Transaction transaction) {
        super(source);
        this.transaction = transaction;
    }

    public TransactionEvent(Object source) {
        super(source);
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public TransactionEvent setTransaction(Transaction transaction) {
        this.transaction = transaction;
        return this;
    }

}
