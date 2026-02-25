package buddy.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a specific deadline.
 * <p>
 * A <code>Deadline</code> task includes a description and a {@link LocalDate}
 * indicating when the task must be completed by.
 * </p>
 */
public class Deadline extends Task {
    protected LocalDate by;

    /**
     * Constructs a new <code>Deadline</code> task.
     *
     * @param description A summary of the task.
     * @param by The due date of the task.
     */
    public Deadline (String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the due date of this task.
     *
     * @return The {@link LocalDate} representing the deadline.
     */
    public LocalDate getBy() {
        return by;
    }

    /**
     * Returns a string representation of the deadline task.
     * <p>
     * The format is: {@code [D][StatusIcon] Description (by: MMM dd yyyy)}
     * </p>
     *
     * @return A formatted string representing the deadline task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " +
                by.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }
}
