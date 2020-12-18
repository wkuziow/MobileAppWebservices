package pl.kuziow.mobileappwebservices.exceptions;

public class UserServiceException extends RuntimeException {

    private static final long serialVersionUID = 9035265904727268033L;

    public UserServiceException(String message) {
        super(message);
    }
}
