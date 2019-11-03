package challenger.purple.validations;

import challenger.purple.model.response.AccountResponse;

public interface Validations<T,K> {

    AccountResponse validation(T o, K v);
}
