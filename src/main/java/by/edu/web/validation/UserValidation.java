package by.edu.web.validation;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.edu.web.command.constant.ParamConst.*;

public class UserValidation {
    private final boolean result;
    private UserValidateException exception;

    public boolean isResult() {
        return result;
    }

    public UserValidateException getException() {
        return exception;
    }

    private UserValidation(Builder builder) {
        if (builder.getErrors().isEmpty()) {
            result = false;
        } else {
            result = true;
            exception = new UserValidateException(builder.getErrors().toString());
        }
    }


    public static class Builder implements ObjBuilder<UserValidation> {
        private final Map<String, String> uncorrectFieldName = new HashMap<>();

        public Map<String, String> getErrors() {
            return uncorrectFieldName;


        }

        public Builder validLogin(String login) {
            if (!valid(login, Expression.LOGIN_EXPRESSION.getRegexStr())) {
                uncorrectFieldName.put(LOGIN_PARAM, "Uncorrect Login");
            }
            return this;
        }

        public Builder validEmail(String email) {
            if (!valid(email, Expression.EMAIL_EXPRESSION.getRegexStr())) {
                uncorrectFieldName.put(EMAIL_PARAM, "Uncorrect Email");
            }
            return this;
        }

        public Builder validPhone(String phone) {
            if (!valid(phone, Expression.PHONE_EXPRESSION.getRegexStr())) {
                uncorrectFieldName.put(PHONE_PARAM, "Uncorrect Phone");
            }

            return this;
        }

        public Builder validPassword(String password) {
            if (!valid(password, Expression.PASSWORD_EXPRESSION.getRegexStr())) {
                uncorrectFieldName.put(PASSWORD_PARAM, "Uncorrect Password");
            }
            return this;

        }

        private boolean valid(String param, String expression) {
            if (expression != null) {
                Pattern pattern = Pattern.compile(expression);
                Matcher match = pattern.matcher(param);
                return match.find();
            }
            return false;
        }

        @Override
        public UserValidation build() {
            return new UserValidation(this);
        }
    }
}

