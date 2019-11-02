package challenger.purple.validations;

import challenger.purple.model.response.AccountResponseModel;

public interface Validations<T,K> {

    AccountResponseModel validation(T o, K v);
}
