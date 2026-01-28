import java.util.Scanner;

public class Echo {
    public static void main(String[] args) {
        String line;
        Scanner in = new Scanner(System.in);
        Task[] tasks  = new Task[100];
        int taskCount = 0;
        String horizontalLine = "    ____________________________________________________________";

        // Print Greeting
        System.out.println(horizontalLine);
        System.out.println(" Hello! I'm Echo");
        System.out.println(" What shall I add to the List for you?");
        System.out.println(horizontalLine);

        while (true) {
            line = in.nextLine();

            if (line.equals("bye")) {
                break;
            }

            if (line.equals("list")) {
                System.out.println(horizontalLine);
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
                System.out.println(horizontalLine);
            } else if (line.startsWith("mark")) {
                int index = Integer.parseInt(line.substring(5)) - 1;
                tasks[index].markAsDone();
                System.out.println(horizontalLine);
                System.out.println("Nice! I've marked this task as done:");
                System.out.println(tasks[index]);
                System.out.println(horizontalLine);
            } else if (line.startsWith("unmark")) {
                int index = Integer.parseInt(line.substring(7)) - 1;
                tasks[index].unmarkAsDone();
                System.out.println(horizontalLine);
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println(tasks[index]);
                System.out.println(horizontalLine);
            } else {
                tasks[taskCount] = new Task(line);
                taskCount++;
                System.out.println(horizontalLine);
                System.out.println("added: " + line);
                System.out.println(horizontalLine);
            }
        }

        // Print Exit Message
        System.out.println(horizontalLine);
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(horizontalLine);
    }
}
