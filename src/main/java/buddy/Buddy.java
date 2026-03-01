package buddy;

import java.util.ArrayList;
import buddy.task.Task;
import buddy.task.Todo;

/**
 * Represents the main chatbot Buddy, a loyal task tracker.
 * Handles user input and manages the task list.
 */
public class Buddy {
    private static final String FILE_PATH = "./data/buddy.txt";
    private static final String DIR_PATH = "./data/";

    private Ui ui;
    private Storage storage;
    private TaskList tasks;
    
    /**
     * Initializes Buddy by setting up the UI, Storage, and loading existing tasks.
     * If loading fails, it starts with an empty task list.
     */
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

    /**
     * The main entry point for the Buddy application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        Buddy buddy = new Buddy();
        buddy.run();
    }

    /**
     * Starts the main execution loop of the chatbot.
     * Continues to read and process commands until the user says "bye".
     */
    public void run() {
        ui.printGreeting();
        while (true) {
            String line = ui.readCommand();
            if (Parser.getCommandWord(line).equals("bye")) {
                break;
            }
            try {
                processCommand(line);
            } catch (BuddyException e) {
                ui.printErrorMessage(e.getMessage());
            }
        }
        ui.printExitMessage();
    }

    /**
     * Processes a single user command string.
     *
     * @param line The raw input string from the user.
     * @throws BuddyException If the command is unrecognized or parsing fails.
     */
    private void processCommand(String line) throws BuddyException {
        String commandWord = Parser.getCommandWord(line);

        switch (commandWord) {
        case "list":
            ui.printTaskList(tasks.getAllTasks());
            break;
        case "mark":
            handleMarkTask(line);
            break;
        case "unmark":
            handleUnmarkTask(line);
            break;
        case "todo":
            addToDo(line);
            break;
        case "deadline":
            addDeadline(line);
            break;
        case "event":
            addEvent(line);
            break;
        case "delete":
            deleteTask(line);
            break;
        case "find":
            handleFind(line);
            break;
        default:
            throw new BuddyException("Whimper... I don't recognize that command. " +
                    "Try 'list', 'mark', 'unmark', 'todo', 'deadline', 'event', 'delete' or 'find'!");
        }
    }

    /**
     * Marks a specific task as completed based on the index provided in the input.
     *
     * @param line The raw user input containing the target task index.
     * @throws BuddyException If the index is invalid or cannot be parsed.
     */
    private void handleMarkTask(String line) throws BuddyException {
        int index = Parser.parseMarkIndex(line);
        Task t = tasks.markTask(index);
        storage.saveTasks(tasks.getAllTasks());
        ui.printStatusUpdate("Awesome! I've checked this off your list:", t);
    }

    /**
     * Reverts a specific task to an incomplete status based on the input index.
     *
     * @param line The raw user input containing the target task index.
     * @throws BuddyException If the index is invalid or cannot be parsed.
     */
    private void handleUnmarkTask(String line) throws BuddyException {
        int index = Parser.parseUnmarkIndex(line);
        Task t = tasks.unmarkTask(index);
        storage.saveTasks(tasks.getAllTasks());
        ui.printStatusUpdate("No problem, I've put this back on the list for you:", t);
    }

    /**
     * Adds a new To-Do task to the list after parsing the description.
     *
     * @param line The raw user input containing the task description.
     * @throws BuddyException If the description is empty or invalid.
     */
    private void addToDo(String line) throws BuddyException {
        Todo newTask = Parser.parseTodo(line);
        tasks.add(newTask);
        storage.saveTasks(tasks.getAllTasks());
        ui.printTaskAdded(newTask, tasks.size());
    }

    /**
     * Adds a new Deadline task to the list after parsing the description and date.
     *
     * @param line The raw user input containing the description and due date.
     * @throws BuddyException If the input format or date is incorrect.
     */
    private void addDeadline(String line) throws BuddyException {
        Task newTask = Parser.parseDeadline(line);
        tasks.add(newTask);
        storage.saveTasks(tasks.getAllTasks());
        ui.printTaskAdded(newTask, tasks.size());
    }

    /**
     * Adds a new Event task to the list after parsing the description and timeframe.
     *
     * @param line The raw user input containing description, start, and end info.
     * @throws BuddyException If the input format is missing required details.
     */
    private void addEvent(String line) throws BuddyException {
        Task newTask = Parser.parseEvent(line);
        tasks.add(newTask);
        storage.saveTasks(tasks.getAllTasks());
        ui.printTaskAdded(newTask, tasks.size());
    }

    /**
     * Removes a task from the list based on the index provided in the input.
     *
     * @param line The raw user input containing the index of the task to remove.
     * @throws BuddyException If the index is invalid or cannot be parsed.
     */
    private void deleteTask(String line) throws BuddyException {
        int index = Parser.parseDeleteIndex(line);
        Task removedTask = tasks.remove(index);
        storage.saveTasks(tasks.getAllTasks());
        ui.printTaskDeleted(removedTask, tasks.size());
    }

    /**
     * Searches for tasks matching a keyword and displays the results to the user.
     *
     * @param line The raw user input containing the search keyword.
     * @throws BuddyException If the keyword is missing or invalid.
     */
    private void handleFind(String line) throws BuddyException {
        String keyword = Parser.parseFindKeyword(line);
        ArrayList<Task> matchingTasks = tasks.findTasks(keyword);
        ui.printMatchingTasks(matchingTasks, keyword);
    }
}
