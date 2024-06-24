package by.edu.web.validation;

public enum Expression {
    LOGIN_EXPRESSION("^.{4,40}$"),
    EMAIL_EXPRESSION("^.+@.+\\..+$"),
    PASSWORD_EXPRESSION("^.{4,40}$"),
    PHONE_EXPRESSION("^\\+.*$");

    private final String regexStr;


    private Expression(String regexStr){
        this.regexStr = regexStr;
    }

    public String getRegexStr(){
        return regexStr;
    }
}