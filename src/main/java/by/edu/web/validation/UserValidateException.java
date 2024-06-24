package by.edu.web.validation;

public class UserValidateException extends Exception{
    private static final long serialVersionUID = 1L;



    public UserValidateException() {
        super();
    }

    public UserValidateException(String message) {
        super(message);
    }

    public UserValidateException(Exception e) {
        super(e);
    }

    public UserValidateException(String message, Exception e) {
        super(message, e);
    }
}