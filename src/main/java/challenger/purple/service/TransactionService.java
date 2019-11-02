package challenger.purple.service;

import challenger.purple.model.TransactionModel;
import challenger.purple.model.response.AccountResponseModel;

public interface TransactionService {

    AccountResponseModel createTransaction(TransactionModel transactionModel);

}
