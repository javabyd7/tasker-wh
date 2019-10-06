package pl.sda.common.user;

import pl.sda.task.model.CannotAssignTaskException;

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
