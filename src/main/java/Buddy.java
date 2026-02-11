import java.util.Scanner;

public class Buddy {
    private static final int TODO_OFFSET = 5;
    private static final int DEADLINE_OFFSET = 9;
    private static final int EVENT_OFFSET = 6;
    private static final int MARK_OFFSET = 5;
    private static final int UNMARK_OFFSET = 7;

    public static final int MAX_TASKS = 100;
    public static final String HORIZONTAL_LINE = "____________________________________________________________";

    private static final Task[] tasks = new Task[MAX_TASKS];
    private static int taskCount = 0;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        printGreeting();
        runCommandLoop(in);
        printExitMessage();
    }

    private static void runCommandLoop(Scanner in) {
        while (true) {
            String line = in.nextLine();

            if (line.equalsIgnoreCase("bye")) {
                return;
            }
            try {
                processCommand(line);
            } catch (InvalidTask e) {
                printErrorBox(e.getMessage());
            }
        }
    }

    private static void printErrorBox(String message) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(" OOPS!!! " + message);
        System.out.println(HORIZONTAL_LINE);
    }

    private static void processCommand(String line) throws InvalidTask {
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
            throw new InvalidTask("Whimper... I don't recognize that command. Try 'todo', 'deadline', or 'event'!");
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

    private static void handleMarkTask(String line) throws InvalidTask {
        try {
            if (line.trim().length() <= MARK_OFFSET - 1) {
                throw new InvalidTask("Which task number am I marking? Use: mark [number]");
            }

            int index = Integer.parseInt(line.substring(MARK_OFFSET)) - 1;

            if (index < 0 || index >= taskCount) {
                throw new InvalidTask("I can't mark that... Task " + (index + 1) + " doesn't exist!");
            }

            tasks[index].markAsDone();
            printStatusUpdate("Awesome! I've checked this off your list:", tasks[index]);
        } catch (NumberFormatException e) {
            throw new InvalidTask("I need a number to mark the task, not words!");
        }
    }

    private static void handleUnmarkTask(String line) throws InvalidTask {
        try {
            if (line.trim().length() <= UNMARK_OFFSET - 1) {
                throw new InvalidTask("Which task number am I unmarking? Use: unmark [number]");
            }

            int index = Integer.parseInt(line.substring(UNMARK_OFFSET)) - 1;

            if (index < 0 || index >= taskCount) {
                throw new InvalidTask("I can't mark that... Task " + (index + 1) + " doesn't exist!");
            }

            tasks[index].unmarkAsDone();
            printStatusUpdate("No problem, I've put this back on the list for you:", tasks[index]);
        } catch (NumberFormatException e) {
            throw new InvalidTask("I need a number to unmark the task, not words!");
        }
    }

    private static void printStatusUpdate(String message, Task task) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(message);
        System.out.println(task);
        System.out.println(HORIZONTAL_LINE);
    }

    private static void addToDo(String line) throws InvalidTask {
        if (line.trim().length() <= TODO_OFFSET) {
            throw new InvalidTask("What am I supposed to do??");
        }

        String description = line.substring(TODO_OFFSET).trim();
        tasks[taskCount] = new Todo(description);
        taskCount++;
        printTaskAdded(tasks[taskCount - 1], taskCount);
    }

    private static void addDeadline(String line) throws InvalidTask {
        if (!line.contains(" /by ")) {
            throw new InvalidTask("When am I supposed to do this by?? Please add a '/by' time!");
        }

        String[] parts = line.substring(DEADLINE_OFFSET).split(" /by ");

        if (parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new InvalidTask("Hello please fill in the description and deadline time!!");
        }

        tasks[taskCount] = new Deadline(parts[0], parts[1]);
        taskCount++;
        printTaskAdded(tasks[taskCount - 1], taskCount);
    }

    private static void addEvent(String line) throws InvalidTask {
        if (!line.contains(" /from") || !line.contains(" /to")) {
            throw new InvalidTask("When does this event start and end?? Please add a '/from' and '/to' time!");
        };

        String[] parts = line.substring(EVENT_OFFSET).split(" /from | /to ");

        if (parts.length < 3 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            throw new InvalidTask("Your event is missing details! Format: event [name] /from [start] /to [end]!!");
        }

        tasks[taskCount] = new Event(parts[0], parts[1], parts[2]);
        taskCount++;
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
