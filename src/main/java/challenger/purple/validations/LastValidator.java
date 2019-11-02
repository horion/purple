package challenger.purple.validations;

import challenger.purple.model.response.AccountResponseModel;

public class LastValidator implements Validations {
    @Override
    public AccountResponseModel validation(AccountResponseModel o, Object v) {
        return o;
    }
}
