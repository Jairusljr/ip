package buddy;

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    private static final String FILE_PATH = "./data/buddy.txt";
    private static final String DIR_PATH = "./data/";
    // Constants
    private static final int TODO_OFFSET = 5;
    private static final int DEADLINE_OFFSET = 9;
    private static final int EVENT_OFFSET = 6;
    private static final int MARK_OFFSET = 5;
    private static final int UNMARK_OFFSET = 7;
    private static final int MAX_TASKS = 100;
    private static final String HORIZONTAL_LINE = "____________________________________________________________";

    // Data Fields
    private static final Task[] tasks = new Task[MAX_TASKS];
    private static int taskCount = 0;

    public static void main(String[] args) {
        loadTasks();
        Scanner in = new Scanner(System.in);
        printGreeting();
        runCommandLoop(in);
        printExitMessage();
    }

    private static void loadDataFile() throws IOException {
        // Represent the directory as a File object
        File directory = new File(DIR_PATH);
        if (!directory.exists()) {
            // Create the directory if it does not exist
            directory.mkdirs();
        }

        // Represent the data file as a File object
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            // Atomically create the new empty file if it doesn't exist
            file.createNewFile();
        }
    }

    private static void loadTasks() {
        try {
            loadDataFile();
            File f = new File(FILE_PATH);
            Scanner s = new Scanner(f);

            while (s.hasNext()) {
                String line = s.nextLine();
                String[] parts = line.split(" \\| ");
                if (parts.length < 3) continue;

                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String desc = parts[2];

                Task task = null;
                switch (type) {
                case "T":
                    task = new Todo(desc);
                    break;
                case "D":
                    task = new Deadline(desc, parts[3]);
                    break;
                case "E":
                    task = new Event(desc, parts[3], parts[4]);
                    break;
                }

                if (task != null) {
                    if (isDone) task.markAsDone();
                    tasks[taskCount++] = task;
                }
            }
        } catch (IOException e) {
            System.out.println("Woof! I couldn't load your previous list. Starting fresh!");
        }
    }

    private static void saveTasks() {
        try {
            FileWriter fw = new FileWriter(FILE_PATH);
            for (int i = 0; i < taskCount; i++) {
                fw.write(formatTaskForFile(tasks[i]) + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Whimper... I couldn't save your tasks!");
        }
    }

    private static String formatTaskForFile(Task t) {
        String type = "";
        String status = "";

        String extra = "";

        if (t.isDone()) {
            status = "1";
        } else {
            status = "0";
        }
        
        if (t instanceof Todo) {
            type = "T";
        } else if (t instanceof Deadline) {
            type = "D";
            extra = " | " + ((Deadline) t).getBy();
        } else if (t instanceof Event) {
            type = "E";
            extra = " | " + ((Event) t).getFrom() + " | " + ((Event) t).getTo();
        }
        return type + " | " + status + " | " + t.getDescription() + extra;
    }

    /**
     * Reads and processes user commands until 'bye' is received.
     *
     * @param in The Scanner object for reading input.
     */
    private static void runCommandLoop(Scanner in) {
        while (true) {
            String line = in.nextLine();

            if (line.equalsIgnoreCase("bye")) {
                return;
            }

            try {
                processCommand(line);
            } catch (BuddyException e) {
                printErrorMessage(e.getMessage());
            }
        }
    }

    private static void printErrorMessage(String message) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(" OOPS!!! " + message);
        System.out.println(HORIZONTAL_LINE);
    }

    private static void processCommand(String line) throws BuddyException {
        if (line.equals("list")) {
            printTaskList();
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
        } else {
            throw new BuddyException("Whimper... I don't recognize that command. " +
                    "Try 'list', 'mark', 'unmark', 'todo', 'deadline', or 'event'!");
        }
    }

    private static void printGreeting() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Woof! I'm Buddy, your loyal Task-Tracker.");
        System.out.println("What shall I add to the List for you?");
        System.out.println(HORIZONTAL_LINE);
    }

    private static void printTaskList() {
        System.out.println(HORIZONTAL_LINE);
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + 1) + ". " + tasks[i]);
        }
        System.out.println(HORIZONTAL_LINE);
    }

    private static void handleMarkTask(String line) throws BuddyException {
        try {
            if (line.trim().length() < MARK_OFFSET) {
                throw new BuddyException("Which task number am I marking? " +
                        "Format: mark [number]");
            }

            int index = Integer.parseInt(line.substring(MARK_OFFSET)) - 1;

            if (index < 0 || index >= taskCount) {
                throw new BuddyException("I can't mark that... Task " + (index + 1) + " doesn't exist!");
            }

            tasks[index].markAsDone();
            saveTasks();
            printStatusUpdate("Awesome! I've checked this off your list:", tasks[index]);
        } catch (NumberFormatException e) {
            throw new BuddyException("I need a number to mark the task, not words!");
        }
    }

    private static void handleUnmarkTask(String line) throws BuddyException {
        try {
            if (line.trim().length() < UNMARK_OFFSET) {
                throw new BuddyException("Which task number am I unmarking? " +
                        "Format: unmark [number]");
            }

            int index = Integer.parseInt(line.substring(UNMARK_OFFSET)) - 1;

            if (index < 0 || index >= taskCount) {
                throw new BuddyException("I can't unmark that... Task " + (index + 1) + " doesn't exist!");
            }

            tasks[index].unmarkAsDone();
            saveTasks();
            printStatusUpdate("No problem, I've put this back on the list for you:", tasks[index]);
        } catch (NumberFormatException e) {
            throw new BuddyException("I need a number to unmark the task, not words!");
        }
    }

    private static void printStatusUpdate(String message, Task task) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(message);
        System.out.println(task);
        System.out.println(HORIZONTAL_LINE);
    }

    private static void addToDo(String line) throws BuddyException {
        if (line.trim().length() <= TODO_OFFSET) {
            throw new BuddyException("What am I supposed to do?? Format: todo [name]");
        }

        String description = line.substring(TODO_OFFSET).trim();
        tasks[taskCount] = new Todo(description);
        taskCount++;
        saveTasks();
        printTaskAdded(tasks[taskCount - 1], taskCount);
    }

    private static void addDeadline(String line) throws BuddyException {
        if (!line.contains(" /by ")) {
            throw new BuddyException("When am I supposed to do this by?? " +
                    "Format: deadline [name] /by [deadline]!");
        }

        String[] parts = line.substring(DEADLINE_OFFSET).split(" /by ", 2);

        if (parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new BuddyException("Hello please fill in the description and deadline time!! " +
                    "Format: deadline [name] /by [deadline]!");
        }

        tasks[taskCount] = new Deadline(parts[0], parts[1]);
        taskCount++;
        saveTasks();
        printTaskAdded(tasks[taskCount - 1], taskCount);
    }

    private static void addEvent(String line) throws BuddyException {
        if (!line.contains(" /from") || !line.contains(" /to")) {
            throw new BuddyException("When does this event start and end?? " +
                    "Format: event [name] /from [start] /to [end]!!");
        }

        String[] parts = line.substring(EVENT_OFFSET).split(" /from | /to ", 3);

        if (parts.length < 3 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            throw new BuddyException("Your event is missing details! " +
                    "Format: event [name] /from [start] /to [end]!!");
        }

        tasks[taskCount] = new Event(parts[0], parts[1], parts[2]);
        taskCount++;
        saveTasks();
        printTaskAdded(tasks[taskCount - 1], taskCount);
    }

    private static void printTaskAdded(Task task, int taskCount) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Got it! I've added '" + task + "' to your pile.");
        System.out.println("You now have " + taskCount + " things on your list!");
        System.out.println(HORIZONTAL_LINE);
    }

    private static void printExitMessage() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(HORIZONTAL_LINE);
    }
}
