package buddy;

import java.util.ArrayList;
import java.util.Scanner;
import buddy.task.Task;

public class Ui {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void printGreeting() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Woof! I'm Buddy, your loyal Task-Tracker.");
        System.out.println("What shall I add to the List for you?");
        System.out.println(HORIZONTAL_LINE);
    }

    public void printExitMessage() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(HORIZONTAL_LINE);
    }

    public void printErrorMessage(String message) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(" OOPS!!! " + message);
        System.out.println(HORIZONTAL_LINE);
    }

    public void printTaskAdded(Task task, int taskCount) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Got it! I've added '" + task + "' to your pile.");
        System.out.println("You now have " + taskCount + " things on your list!");
        System.out.println(HORIZONTAL_LINE);
    }

    public void printStatusUpdate(String message, Task task) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(message);
        System.out.println(task);
        System.out.println(HORIZONTAL_LINE);
    }

    public void printTaskDeleted(Task removedTask, int totalTasks) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("Noted. I've removed this task:");
        System.out.println(removedTask);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
        System.out.println(HORIZONTAL_LINE);
    }

    public void printTaskList(ArrayList<Task> tasks) {
        System.out.println(HORIZONTAL_LINE);
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
        System.out.println(HORIZONTAL_LINE);
    }

    public void printMatchingTasks(ArrayList<Task> matchingTasks, String keyword) {
        if(matchingTasks.isEmpty()) {
            System.out.println(HORIZONTAL_LINE);
            System.out.println("I couldn't find any tasks containing " + keyword +"!");
            System.out.println(HORIZONTAL_LINE);
            return;
        }

        System.out.println(HORIZONTAL_LINE);
        System.out.println("Here are the tasks containing " + keyword + " in your list:");
        for (int i = 0; i < matchingTasks.size(); i++) {
            System.out.println((i + 1) + "." + matchingTasks.get(i));
        }
        System.out.println(HORIZONTAL_LINE);
    }
}
