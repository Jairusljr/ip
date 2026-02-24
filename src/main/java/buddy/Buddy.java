package buddy;

import java.util.ArrayList;
import java.util.Scanner;
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

    private void handleMarkTask(String line) throws BuddyException {
        int index = Parser.parseMarkIndex(line);
        Task t = tasks.markTask(index);
        storage.saveTasks(tasks.getAllTasks());
        ui.printStatusUpdate("Awesome! I've checked this off your list:", t);
    }

    private void handleUnmarkTask(String line) throws BuddyException {
        int index = Parser.parseUnmarkIndex(line);
        Task t = tasks.unmarkTask(index);
        storage.saveTasks(tasks.getAllTasks());
        ui.printStatusUpdate("No problem, I've put this back on the list for you:", t);
    }

    private void addToDo(String line) throws BuddyException {
        Todo newTask = Parser.parseTodo(line);
        tasks.add(newTask);
        storage.saveTasks(tasks.getAllTasks());
        ui.printTaskAdded(newTask, tasks.size());
    }

    private void addDeadline(String line) throws BuddyException {
        Task newTask = Parser.parseDeadline(line);
        tasks.add(newTask);
        storage.saveTasks(tasks.getAllTasks());
        ui.printTaskAdded(newTask, tasks.size());
    }

    private void addEvent(String line) throws BuddyException {
        Task newTask = Parser.parseEvent(line);
        tasks.add(newTask);
        storage.saveTasks(tasks.getAllTasks());
        ui.printTaskAdded(newTask, tasks.size());
    }

    public void deleteTask(String line) throws BuddyException {
        int index = Parser.parseDeleteIndex(line);
        Task removedTask = tasks.remove(index);
        storage.saveTasks(tasks.getAllTasks());
        ui.printTaskDeleted(removedTask, tasks.size());
    }

    private void handleFind(String line) throws BuddyException {
        String keyword = Parser.parseFindKeyword(line);
        ArrayList<Task> matchingTasks = tasks.findTasks(keyword);
        ui.printMatchingTasks(matchingTasks,keyword);
    }
}
