import java.util.Scanner;

public class Buddy {

    public static final int MAX_TASKS = 100;
    public static final String HORIZONTAL_LINE = "____________________________________________________________";

    private static Task[] tasks = new Task[MAX_TASKS];
    private static int taskCount = 0;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        printGreeting();

        while (true) {
            String line = in.nextLine();

            if (line.equals("bye")) {
                break;
            }

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
                addNormalTask(line);
            }
        }

        printExitMessage();
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

    private static void handleMarkTask(String line) {
        int index = Integer.parseInt(line.substring(5)) - 1;

        if (index < 0 || index >= taskCount) {
            System.out.println(HORIZONTAL_LINE);
            System.out.println("Wait! That task number doesn't exist!");
            System.out.println(HORIZONTAL_LINE);
            return;
        }

        System.out.println(HORIZONTAL_LINE);
        tasks[index].markAsDone();
        System.out.println("Awesome! I've checked this off your list:");
        System.out.println(tasks[index]);
        System.out.println(HORIZONTAL_LINE);
    }

    private static void handleUnmarkTask(String line) {
        int index = Integer.parseInt(line.substring(7)) - 1;

        if (index < 0 || index >= taskCount) {
            System.out.println(HORIZONTAL_LINE);
            System.out.println("Wait! That task number doesn't exist!");
            System.out.println(HORIZONTAL_LINE);
            return;
        }

        System.out.println(HORIZONTAL_LINE);
        tasks[index].unmarkAsDone();
        System.out.println("No problem, I've put this back on the list for you:");
        System.out.println(tasks[index]);
        System.out.println(HORIZONTAL_LINE);
    }

    private static void addToDo(String line) {
        String description = line.substring(5).trim();
        tasks[taskCount] = new Todo(description);
        taskCount++;
        printTaskAdded(tasks[taskCount - 1], taskCount);
    };

    private static void addDeadline(String line) {
        String[] parts = line.substring(9).split(" /by ");
        tasks[taskCount] = new Deadline(parts[0], parts[1]);
        taskCount++;
        printTaskAdded(tasks[taskCount - 1], taskCount);
    };

    private static void addEvent(String line) {
        String[] parts = line.substring(6).split(" /from | /to ");
        tasks[taskCount] = new Event(parts[0], parts[1], parts[2]);
        taskCount++;
        printTaskAdded(tasks[taskCount - 1], taskCount);
    };

    private static void addNormalTask(String line) {
        tasks[taskCount] = new Task(line);
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
