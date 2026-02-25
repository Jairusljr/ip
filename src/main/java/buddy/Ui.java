package buddy;

import java.util.ArrayList;
import java.util.Scanner;
import buddy.task.Task;

/**
 * Deals with interactions with the user.
 * <p>
 * This class is responsible for reading commands from the standard input
 * and displaying various messages, task lists, and errors to the user
 * in a standardized format.
 * </p>
 */
public class Ui {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private Scanner scanner;

    /**
     * Initializes a new <code>Ui</code> object and its input scanner.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads the next line of input provided by the user.
     *
     * @return The raw command string entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays a greeting and welcome message to the user.
     */
    public void printGreeting() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Woof! I'm Buddy, your loyal Task-Tracker.");
        System.out.println("What shall I add to the List for you?");
        System.out.println(HORIZONTAL_LINE);
    }

    /**
     * Displays a goodbye message when the user exits the application.
     */
    public void printExitMessage() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(HORIZONTAL_LINE);
    }

    /**
     * Displays an error message formatted with Buddy's specific error prefix.
     *
     * @param message The details of the error that occurred.
     */
    public void printErrorMessage(String message) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(" OOPS!!! " + message);
        System.out.println(HORIZONTAL_LINE);
    }

    /**
     * Confirms to the user that a task has been successfully added.
     *
     * @param task The task that was added.
     * @param taskCount The updated total number of tasks in the list.
     */
    public void printTaskAdded(Task task, int taskCount) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Got it! I've added '" + task + "' to your pile.");
        System.out.println("You now have " + taskCount + " things on your list!");
        System.out.println(HORIZONTAL_LINE);
    }

    /**
     * Prints a generic status update message along with a specific task.
     * Used primarily for marking and unmarking tasks.
     *
     * @param message The status message to display.
     * @param task The task being updated.
     */
    public void printStatusUpdate(String message, Task task) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(message);
        System.out.println(task);
        System.out.println(HORIZONTAL_LINE);
    }

    /**
     * Confirms the successful removal of a task from the list.
     *
     * @param removedTask The task that was deleted.
     * @param totalTasks The remaining number of tasks in the list.
     */
    public void printTaskDeleted(Task removedTask, int totalTasks) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Noted. I've removed this task:");
        System.out.println(removedTask);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        System.out.println(HORIZONTAL_LINE);
    }

    /**
     * Displays the complete list of tasks to the user in a numbered format.
     *
     * @param tasks The {@link ArrayList} of all tasks currently in the list.
     */
    public void printTaskList(ArrayList<Task> tasks) {
        System.out.println(HORIZONTAL_LINE);
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
        System.out.println(HORIZONTAL_LINE);
    }

    /**
     * Displays a list of tasks that match a given search keyword.
     * <p>
     * If no matching tasks are found, an appropriate "not found" message is shown.
     * </p>
     *
     * @param matchingTasks The list of tasks containing the keyword.
     * @param keyword The search term used to filter the tasks.
     */
    public void printMatchingTasks(ArrayList<Task> matchingTasks, String keyword) {
        if(matchingTasks.isEmpty()) {
            System.out.println(HORIZONTAL_LINE);
            System.out.println("I couldn't find any tasks containing " + keyword +"!");
            System.out.println(HORIZONTAL_LINE);
            return;
        }

        System.out.println(HORIZONTAL_LINE);
        System.out.println("Here are the tasks containing " + keyword + " in your list:");
        for (int i = 0; i < matchingTasks.size(); i++) {
            System.out.println((i + 1) + "." + matchingTasks.get(i));
        }
        System.out.println(HORIZONTAL_LINE);
    }
}
