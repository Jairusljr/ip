package buddy;

import buddy.task.Deadline;
import buddy.task.Event;
import buddy.task.Todo;

public class Parser {
    private static final int TODO_OFFSET = 5;
    private static final int DEADLINE_OFFSET = 9;
    private static final int EVENT_OFFSET = 6;
    private static final int MARK_OFFSET = 5;
    private static final int UNMARK_OFFSET = 7;
    private static final int DELETE_OFFSET = 7;

    public static String getCommandWord(String input) {
        return input.trim().split(" ")[0].toLowerCase();
    }

    public static Todo parseTodo(String input) throws BuddyException {
        if (input.trim().length() <= TODO_OFFSET) {
            throw new BuddyException("What am I supposed to do?? Format: todo [name]");
        }
        return new Todo(input.substring(TODO_OFFSET).trim());
    }

    public static Deadline parseDeadline(String input) throws BuddyException {
        if (!input.contains(" /by ")) {
            throw new BuddyException("When am I supposed to do this by?? " +
                    "Format: deadline [name] /by [deadline]!");
        }
        String[] parts = input.substring(DEADLINE_OFFSET).split(" /by ", 2);
        if (parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new BuddyException("Hello please fill in the description and deadline time!! " +
                    "Format: deadline [name] /by [deadline]!");
        }
        return new Deadline(parts[0].trim(), parts[1].trim());
    }

    public static Event parseEvent(String input) throws BuddyException {
        if (!input.contains(" /from") || !input.contains(" /to")) {
            throw new BuddyException("Format: event [name] /from [start] /to [end]");
        }
        String[] parts = input.substring(EVENT_OFFSET).split(" /from | /to ", 3);
        if (parts.length < 3 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            throw new BuddyException("Your event is missing details!");
        }
        return new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
    }

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
            throw new BuddyException("I need a number to " + commandName + " the task, not words!");
        }
    }

    public static int parseMarkIndex(String input) throws BuddyException {
        return parseTaskIndex(input, "mark", MARK_OFFSET);
    }

    public static int parseUnmarkIndex(String input) throws BuddyException {
        return parseTaskIndex(input, "unmark", UNMARK_OFFSET);
    }

    public static int parseDeleteIndex(String input) throws BuddyException {
        return parseTaskIndex(input, "delete", DELETE_OFFSET);
    }
}
