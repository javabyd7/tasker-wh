package pl.sda.task.model;

public class UserBusyException extends CannotAssignTaskException {

    public UserBusyException(){}

    public UserBusyException(String message) {
        super(message);
    }

    public UserBusyException(String message, Throwable cause) {
        super(message, cause);
    }
}
