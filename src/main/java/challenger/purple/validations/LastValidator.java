package challenger.purple.validations;

import challenger.purple.model.response.AccountResponse;
import challenger.purple.validations.Validations;

public class LastValidator implements Validations {

    @Override
    public AccountResponse validation(Object o, Object v) {
        return (AccountResponse) o;
    }
}
