package buddy.task;

/**
 * Represents a simple "To-Do" task.
 * <p>
 * A <code>Todo</code> is a type of {@link Task} that does not have any
 * date or time attached to it. It simply tracks a description and
 * completion status.
 * </p>
 */
public class Todo extends Task {

    /**
     * Constructs a new <code>Todo</code> task with the specified description.
     *
     * @param description A summary of the task to be completed.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the to-do task.
     * <p>
     * The format is: {@code [T][StatusIcon] Description}
     * </p>
     *
     * @return A formatted string representing the to-do task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
