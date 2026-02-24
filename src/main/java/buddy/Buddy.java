package buddy;

import java.util.Scanner;
import java.util.ArrayList;

import buddy.task.Deadline;
import buddy.task.Event;
import buddy.task.Task;
import buddy.task.Todo;

/**
 * Represents the main chatbot Buddy, a loyal task tracker.
 * Handles user input and manages the task list.
 */
public class Buddy {
    private Ui ui;
    private Storage storage;
    private TaskList tasks;

    public Buddy() {
        ui = new Ui();
        storage = new Storage(FILE_PATH, DIR_PATH);
        try {
            this.tasks = new TaskList(storage.loadTasks());
        } catch (BuddyException e) {
            ui.printErrorMessage("I couldn't load your old list, woof! Starting fresh.");
            this.tasks = new TaskList();
        }
    }

    public static void main(String[] args) {
        Buddy buddy = new Buddy();
        buddy.run();
    }

    public void run() {
        ui.printGreeting();
        Scanner in = new Scanner(System.in);
        runCommandLoop(in);
        ui.printExitMessage();
    }

    private static final String FILE_PATH = "./data/buddy.txt";
    private static final String DIR_PATH = "./data/";
    // Constants
    private static final int TODO_OFFSET = 5;
    private static final int DEADLINE_OFFSET = 9;
    private static final int EVENT_OFFSET = 6;
    private static final int MARK_OFFSET = 5;
    private static final int UNMARK_OFFSET = 7;
    private static final int DELETE_OFFSET = 7;


    /**
     * Reads and processes user commands until 'bye' is received.
     *
     * @param in The Scanner object for reading input.
     */
    private void runCommandLoop(Scanner in) {
        while (true) {
            String line = in.nextLine();

            if (line.equalsIgnoreCase("bye")) {
                return;
            }

            try {
                processCommand(line);
            } catch (BuddyException e) {
                ui.printErrorMessage(e.getMessage());
            }
        }
    }

    private void processCommand(String line) throws BuddyException {
        if (line.equals("list")) {
            ui.printTaskList(tasks.getAllTasks());
        } else if (line.startsWith("mark")) {
            handleMarkTask(line);
        } else if (line.startsWith("unmark")) {
            handleUnmarkTask(line);
        } else if (line.startsWith("todo")) {
            addToDo(line);
        } else if (line.startsWith("deadline")) {
            addDeadline(line);
        } else if (line.startsWith("event")) {
            addEvent(line);
        } else if (line.startsWith("delete")) {
            deleteTask(line);
        } else {
            throw new BuddyException("Whimper... I don't recognize that command. " +
                    "Try 'list', 'mark', 'unmark', 'todo', 'deadline', or 'event'!");
        }
    }

    private void handleMarkTask(String line) throws BuddyException {
        try {
            if (line.trim().length() < MARK_OFFSET) {
                throw new BuddyException("Which task number am I marking? " +
                        "Format: mark [number]");
            }

            int index = Integer.parseInt(line.substring(MARK_OFFSET)) - 1;

            if (index < 0 || index >= tasks.size()) {
                throw new BuddyException("I can't mark that... Task " + (index + 1) + " doesn't exist!");
            }

            tasks.markTask(index);
            storage.saveTasks(tasks.getAllTasks());
            ui.printStatusUpdate("Awesome! I've checked this off your list:", tasks.get(index));
        } catch (NumberFormatException e) {
            throw new BuddyException("I need a number to mark the task, not words!");
        }
    }

    private void handleUnmarkTask(String line) throws BuddyException {
        try {
            if (line.trim().length() < UNMARK_OFFSET) {
                throw new BuddyException("Which task number am I unmarking? " +
                        "Format: unmark [number]");
            }

            int index = Integer.parseInt(line.substring(UNMARK_OFFSET)) - 1;

            if (index < 0 || index >= tasks.size()) {
                throw new BuddyException("I can't unmark that... Task " + (index + 1) + " doesn't exist!");
            }

            tasks.unmarkTask(index);
            storage.saveTasks(tasks.getAllTasks());
            ui.printStatusUpdate("No problem, I've put this back on the list for you:", tasks.get(index));
        } catch (NumberFormatException e) {
            throw new BuddyException("I need a number to unmark the task, not words!");
        }
    }

    private void addToDo(String line) throws BuddyException {
        if (line.trim().length() <= TODO_OFFSET) {
            throw new BuddyException("What am I supposed to do?? Format: todo [name]");
        }

        String description = line.substring(TODO_OFFSET).trim();
        Task newTask = new Todo(description);
        tasks.add(newTask);
        storage.saveTasks(tasks.getAllTasks());
        ui.printTaskAdded(newTask, tasks.size());
    }

    private void addDeadline(String line) throws BuddyException {
        if (!line.contains(" /by ")) {
            throw new BuddyException("When am I supposed to do this by?? " +
                    "Format: deadline [name] /by [deadline]!");
        }

        String[] parts = line.substring(DEADLINE_OFFSET).split(" /by ", 2);

        if (parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new BuddyException("Hello please fill in the description and deadline time!! " +
                    "Format: deadline [name] /by [deadline]!");
        }

        Task newTask = new Deadline(parts[0], parts[1]);
        tasks.add(newTask);
        storage.saveTasks(tasks.getAllTasks());
        ui.printTaskAdded(newTask, tasks.size());
    }

    private void addEvent(String line) throws BuddyException {
        if (!line.contains(" /from") || !line.contains(" /to")) {
            throw new BuddyException("When does this event start and end?? " +
                    "Format: event [name] /from [start] /to [end]!!");
        }

        String[] parts = line.substring(EVENT_OFFSET).split(" /from | /to ", 3);

        if (parts.length < 3 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            throw new BuddyException("Your event is missing details! " +
                    "Format: event [name] /from [start] /to [end]!!");
        }
        Task newTask = new Event(parts[0], parts[1], parts[2]);
        tasks.add(newTask);
        storage.saveTasks(tasks.getAllTasks());
        ui.printTaskAdded(newTask, tasks.size());
    }

    public void deleteTask(String line) throws BuddyException {
        try {
            if (line.trim().length() < DELETE_OFFSET) {
                throw new BuddyException("Which task I supposed to delete?? Format: delete [task number]");
            }

            int index = Integer.parseInt(line.substring(DELETE_OFFSET)) - 1;

            if (index < 0 || index >= tasks.size()) {
                throw new BuddyException("I can't delete that... Task " + (index + 1) + " doesn't exist!");
            }

            Task removedTask = tasks.remove(index);
            storage.saveTasks(tasks.getAllTasks());
            ui.printTaskDeleted(removedTask, tasks.size());
        } catch (NumberFormatException e) {
            throw new BuddyException("I need a number to delete the task, not words!");
        }
    }
}
