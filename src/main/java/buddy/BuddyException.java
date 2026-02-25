package buddy;

/**
 * Represents exceptions specific to the Buddy chatbot.
 * <p>
 * This class is used to signal errors related to user input, task management,
 * and data persistence within the Buddy application. It carries custom
 * error messages that Buddy "barks" back to the user.
 * </p>
 */
public class BuddyException extends Exception {

    /**
     * Constructs a new <code>BuddyException</code> with the specified error message.
     *
     * @param message The detailed error message describing what went wrong.
     */
    public BuddyException(String message) {
        super(message);
    }
}
