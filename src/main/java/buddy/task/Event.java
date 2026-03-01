package buddy.task;

/**
 * Represents a task that occurs within a specific time frame.
 * <p>
 * An <code>Event</code> is a type of {@link Task} that includes a start time/date
 * and an end time/date in addition to the standard task description.
 * </p>
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Constructs a new <code>Event</code> task with the specified description and duration.
     *
     * @param description A summary of the event.
     * @param from The start time or date of the event.
     * @param to The end time or date of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the starting time or date of this event.
     *
     * @return The start time/date string.
     */
    public String getFrom() {
        return this.from;
    }

    /**
     * Returns the ending time or date of this event.
     *
     * @return The end time/date string.
     */
    public String getTo() {
        return this.to;
    }

    /**
     * Returns a string representation of the event task.
     * <p>
     * The format is: {@code [E][StatusIcon] Description (from: start to: end)}
     * </p>
     *
     * @return A formatted string representing the event task.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
