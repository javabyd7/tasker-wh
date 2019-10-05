package pl.sda.task.model;

public class TaskAlreadyAssignedException extends CannotAssignTaskException {

    public TaskAlreadyAssignedException() {
    }

    public TaskAlreadyAssignedException(String message) {
        super(message);
    }

    public TaskAlreadyAssignedException(String message, Throwable cause) {
        super(message, cause);
    }
}
