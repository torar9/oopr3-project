package cz.osu.project.exception;

public class UserErrorException extends Exception {

    public UserErrorException(String message) {
        super(message);
    }
    public UserErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
