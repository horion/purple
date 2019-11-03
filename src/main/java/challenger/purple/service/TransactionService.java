package challenger.purple.service;

import challenger.purple.model.Transaction;
import challenger.purple.model.response.AccountResponse;

public interface TransactionService {

    AccountResponse createTransaction(Transaction transaction);

}
