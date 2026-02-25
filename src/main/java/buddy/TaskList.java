package buddy;

import java.util.ArrayList;
import buddy.task.Task;

/**
 * Represents the internal task list of the Buddy chatbot.
 * <p>
 * This class provides methods to manipulate the list of tasks, including adding,
 * deleting, marking as done, and searching for tasks by keyword.
 * </p>
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs a <code>TaskList</code> using an existing collection of tasks.
     * Primarily used when loading data from the {@link Storage} component.
     *
     * @param tasks An {@link ArrayList} of tasks to initialize the list with.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Constructs an empty <code>TaskList</code>.
     * Used when the application starts without an existing save file.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Appends a new task to the list.
     *
     * @param task The {@link Task} object to be added.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Retrieves a task from the list based on its index.
     *
     * @param index The zero-based index of the task.
     * @return The {@link Task} at the specified position.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the total number of tasks currently in the list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Provides access to the underlying list of tasks.
     * Primarily used by the {@link Storage} class for data persistence.
     *
     * @return The raw {@link ArrayList} of tasks.
     */
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    /**
     * Validates if a given index is within the bounds of the task list.
     *
     * @param index The index to validate.
     * @param action A description of the action being performed (e.g., "mark").
     * @throws BuddyException If the index is out of bounds.
     */
    private void validateIndex(int index, String action) throws BuddyException {
        if (index < 0 || index >= tasks.size()) {
            throw new BuddyException("I can't " + action + " that... Task " + (index + 1) + " doesn't exist!");
        }
    }

    /**
     * Marks a specific task as completed.
     *
     * @param index The zero-based index of the task to mark.
     * @return The {@link Task} that was updated.
     * @throws BuddyException If the index is invalid.
     */
    public Task markTask(int index) throws BuddyException {
        validateIndex(index, "mark");
        tasks.get(index).markAsDone();
        return tasks.get(index);
    }

    /**
     * Reverts a specific task to an incomplete status.
     *
     * @param index The zero-based index of the task to unmark.
     * @return The {@link Task} that was updated.
     * @throws BuddyException If the index is invalid.
     */
    public Task unmarkTask(int index) throws BuddyException {
        validateIndex(index, "unmark");
        tasks.get(index).unmarkAsDone();
        return tasks.get(index);
    }

    /**
     * Removes a task from the list based on its position.
     *
     * @param index The zero-based index of the task to delete.
     * @return The {@link Task} that was removed.
     * @throws BuddyException If the index is invalid.
     */
    public Task remove(int index) throws BuddyException {
        validateIndex(index, "delete");
        return tasks.remove(index);
    }

    /**
     * Searches the list for tasks containing a specific keyword in their description.
     * <p>
     * The search is case-insensitive.
     * </p>
     *
     * @param keyword The search term used to filter tasks.
     * @return An {@link ArrayList} of tasks that match the keyword.
     */
    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }
}