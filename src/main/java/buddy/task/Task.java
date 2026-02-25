package buddy.task;

/**
 * Represents a generic task in the Buddy application.
 * <p>
 * A <code>Task</code> acts as the base class for more specific task types like
 * {@link Todo}, {@link Deadline}, and {@link Event}. It provides core
 * functionality for managing descriptions and completion status.
 * </p>
 */
public class Task {
    protected String description;
    private boolean isDone;

    /**
     * Initializes a new Task with the specified description.
     * The task is set to not done by default.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the description of the task.
     *
     * @return The task description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns whether the task is completed.
     *
     * @return {@code true} if the task is done, {@code false} otherwise.
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Returns a string icon representing the completion status.
     * Returns "X" if done, or a space " " if not done.
     *
     * @return The status icon string.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks the task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Reverts the task to an incomplete status.
     */
    public void unmarkAsDone() {
        this.isDone = false;
    }

    /**
     * Returns a string representation of the task, including its status icon and description.
     *
     * @return The formatted task string.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
