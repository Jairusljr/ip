package buddy;

import java.util.ArrayList;
import buddy.task.Task;

public class TaskList {
    private ArrayList<Task> tasks;

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

    public Task remove(int index) {
        return tasks.remove(index);
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

    public void markTask(int index) {
        tasks.get(index).markAsDone();
    }

    public void unmarkTask(int index) {
        tasks.get(index).unmarkAsDone();
    }
}