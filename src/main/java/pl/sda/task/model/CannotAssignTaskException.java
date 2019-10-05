package pl.sda.task.model;

public class CannotAssignTaskException extends RuntimeException {

    public CannotAssignTaskException() {
    }

    public CannotAssignTaskException(String message) {
        super(message);
    }

    public CannotAssignTaskException(String message, Throwable cause) {
        super(message, cause);
    }
}
