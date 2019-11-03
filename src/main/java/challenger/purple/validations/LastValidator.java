package challenger.purple.validations;

import challenger.purple.model.response.AccountResponse;

public class LastValidator implements Validations {

    @Override
    public AccountResponse validation(Object o, Object v) {
        return (AccountResponse) o;
    }
}
