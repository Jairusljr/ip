import java.util.Scanner;

public class Buddy {

    public static final int MAX_TASKS = 100;
    public static final String HORIZONTAL_LINE = "____________________________________________________________";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;

        printGreeting();

        while (true) {
            String line = in.nextLine();

            if (line.equals("bye")) {
                break;
            }

            if (line.equals("list")) {
                printTaskList(tasks, taskCount);
            } else if (line.startsWith("mark")) {
                handleMarkTask(line, tasks, taskCount);
            } else if (line.startsWith("unmark")) {
                handleUnmarkTask(line, tasks, taskCount);
            } else {
                tasks[taskCount] = new Task(line);
                taskCount++;
                printTaskAdded(line, taskCount);
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

    private static void printTaskList(Task[] tasks, int taskCount) {
        System.out.println(HORIZONTAL_LINE);
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + 1) + ". " + tasks[i]);
        }
        System.out.println(HORIZONTAL_LINE);
    }

    private static void handleMarkTask(String line, Task[] tasks, int taskCount) {
        int index = Integer.parseInt(line.substring(5)) - 1;
        System.out.println(HORIZONTAL_LINE);
        if (index >= 0 && index < taskCount) {
            tasks[index].markAsDone();
            System.out.println("Awesome! I've checked this off your list:");
            System.out.println(tasks[index]);
        } else {
            System.out.println("Wait! That task number doesn't exist!");
        }
        System.out.println(HORIZONTAL_LINE);
    }

    private static void handleUnmarkTask(String line, Task[] tasks, int taskCount) {
        int index = Integer.parseInt(line.substring(7)) - 1;
        System.out.println(HORIZONTAL_LINE);
        if (index >= 0 && index < taskCount) {
            tasks[index].unmarkAsDone();
            System.out.println("No problem, I've put this back on the list for you:");
            System.out.println(tasks[index]);
        } else {
            System.out.println("Wait! That task number doesn't exist!");
        }
        System.out.println(HORIZONTAL_LINE);
    }

    private static void printTaskAdded(String description, int taskCount) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Got it! I've added '" + description + "' to your pile.");
        System.out.println("You now have " + taskCount + " things on your list!");
        System.out.println(HORIZONTAL_LINE);
    }

    private static void printExitMessage() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(HORIZONTAL_LINE);
    }
}
