package buddy;

import java.util.ArrayList;
import buddy.task.Task;

public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Use this constructor when loading existing tasks from your storage.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Use this constructor to start with an empty list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    /**
     * Provides the raw list for the Storage class to save.
     */
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    private void validateIndex(int index, String action) throws BuddyException {
        if (index < 0 || index >= tasks.size()) {
            throw new BuddyException("I can't " + action + " that... Task " + (index + 1) + " doesn't exist!");
        }
    }

    public Task markTask(int index) throws BuddyException {
        validateIndex(index, "mark");
        tasks.get(index).markAsDone();
        return tasks.get(index);
    }

    public Task unmarkTask(int index) throws BuddyException {
        validateIndex(index, "unmark");
        tasks.get(index).unmarkAsDone();
        return tasks.get(index);
    }

    public Task remove(int index) throws BuddyException {
        validateIndex(index, "delete");
        return tasks.remove(index);
    }
}