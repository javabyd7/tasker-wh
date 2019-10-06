package pl.sda.task.model;

public class UserBusyException extends CannotAssignTaskException {

    public UserBusyException(){
        super("User is busy");
    }

    public UserBusyException(String message) {
        super(message);
    }

    public UserBusyException(String message, Throwable cause) {
        super(message, cause);
    }
}
