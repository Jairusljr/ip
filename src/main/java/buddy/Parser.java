package buddy;

import buddy.task.Deadline;
import buddy.task.Event;
import buddy.task.Todo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Deals with making sense of the user command.
 * <p>
 * This class contains static methods that parse raw input strings into
 * command words, task objects (Todo, Deadline, Event), or task indices
 * for manipulation.
 * </p>
 */
public class Parser {
    // Offset constants used to strip command words from input
    private static final int TODO_OFFSET = 5;
    private static final int DEADLINE_OFFSET = 9;
    private static final int EVENT_OFFSET = 6;
    private static final int MARK_OFFSET = 5;
    private static final int UNMARK_OFFSET = 7;
    private static final int DELETE_OFFSET = 7;
    private static final int FIND_OFFSET = 5;

    /**
     * Extracts the primary command word from the user input.
     *
     * @param input The raw input string from the user.
     * @return The first word of the input in lowercase.
     */
    public static String getCommandWord(String input) {
        return input.trim().split(" ")[0].toLowerCase();
    }

    /**
     * Parses the input to create a <code>Todo</code> task.
     *
     * @param input The full user input string.
     * @return A new {@link Todo} task.
     * @throws BuddyException If the description is missing.
     */
    public static Todo parseTodo(String input) throws BuddyException {
        if (input.trim().length() <= TODO_OFFSET) {
            throw new BuddyException("What am I supposed to do?? Format: todo [name]");
        }
        return new Todo(input.substring(TODO_OFFSET).trim());
    }

    /**
     * Parses the input to create a <code>Deadline</code> task.
     *
     * @param input The full user input string.
     * @return A new {@link Deadline} task.
     * @throws BuddyException If the format is invalid or the date is incorrectly formatted.
     */
    public static Deadline parseDeadline(String input) throws BuddyException {
        if (!input.contains(" /by ")) {
            throw new BuddyException("When am I supposed to do this by?? " +
                    "Format: deadline [name] /by yyyy-mm-dd");
        }
        String[] parts = input.substring(DEADLINE_OFFSET).split(" /by ", 2);

        try {
            // Attempt to parse the date string
            LocalDate date = LocalDate.parse(parts[1].trim());
            return new Deadline(parts[0].trim(), date);
        } catch (DateTimeParseException e) {
            throw new BuddyException("Please fill in the description and deadline time!! " +
                    "Format: deadline [name] /by yyyy-mm-dd");
        }
    }

    /**
     * Parses the input to create an <code>Event</code> task.
     *
     * @param input The full user input string.
     * @return A new {@link Event} task.
     * @throws BuddyException If the format is invalid or details are missing.
     */
    public static Event parseEvent(String input) throws BuddyException {
        if (!input.contains(" /from") || !input.contains(" /to")) {
            throw new BuddyException("Format: event [name] /from [start] /to [end]");
        }
        String[] parts = input.substring(EVENT_OFFSET).split(" /from | /to ", 3);
        if (parts.length < 3 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            throw new BuddyException("Your event is missing details! Format: event [name] /from [start] /to [end]");
        }
        return new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
    }

    /**
     * Internal helper to extract a zero-based index from a numeric command string.
     *
     * @param input The raw input string.
     * @param commandName The name of the command being performed (for error messages).
     * @param offset The number of characters to skip at the start of the string.
     * @return The zero-based index of the target task.
     * @throws BuddyException If the input is not a valid number or is missing.
     */
    private static int parseTaskIndex(String input, String commandName, int offset) throws BuddyException {
        String trimmed = input.trim();

        if (trimmed.equalsIgnoreCase(commandName) || trimmed.length() <= offset) {
            throw new BuddyException("Which task number am I " + commandName + "ing? " +
                    "Format: " + commandName + " [number]");
        }

        try {
            String indexPart = trimmed.substring(offset).trim();
            return Integer.parseInt(indexPart) - 1;
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            throw new BuddyException("I need a number to " + commandName + " the task, not words! " +
                    "Format: " + commandName + " [number]");
        }
    }

    /**
     * Parses the index for the 'mark' command.
     *
     * @param input The full user input string.
     * @return The zero-based index of the task to mark.
     * @throws BuddyException If parsing fails.
     */
    public static int parseMarkIndex(String input) throws BuddyException {
        return parseTaskIndex(input, "mark", MARK_OFFSET);
    }

    /**
     * Parses the index for the 'unmark' command.
     *
     * @param input The full user input string.
     * @return The zero-based index of the task to unmark.
     * @throws BuddyException If parsing fails.
     */
    public static int parseUnmarkIndex(String input) throws BuddyException {
        return parseTaskIndex(input, "unmark", UNMARK_OFFSET);
    }

    /**
     * Parses the index for the 'delete' command.
     *
     * @param input The full user input string.
     * @return The zero-based index of the task to delete.
     * @throws BuddyException If parsing fails.
     */
    public static int parseDeleteIndex(String input) throws BuddyException {
        return parseTaskIndex(input, "delete", DELETE_OFFSET);
    }

    /**
     * Extracts the search keyword from the 'find' command.
     *
     * @param input The full user input string.
     * @return The trimmed keyword for searching.
     * @throws BuddyException If the keyword is missing.
     */
    public static String parseFindKeyword(String input) throws BuddyException {
        String trimmed = input.trim();
        if (trimmed.length() <= FIND_OFFSET) {
            throw new BuddyException("What am I looking for?? Format: find [keyword]");
        }
        return trimmed.substring(FIND_OFFSET).trim();
    }
}
