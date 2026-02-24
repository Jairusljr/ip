package buddy;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import buddy.task.Deadline;
import buddy.task.Event;
import buddy.task.Task;
import buddy.task.Todo;

public class Storage {
    private final String FILE_PATH;
    private final String DIR_PATH;

    public Storage(String FILE_PATH, String DIR_PATH) {
        this.FILE_PATH = FILE_PATH;
        this.DIR_PATH = DIR_PATH;
    }

    public void loadDataFile() throws IOException {
        File directory = new File(DIR_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
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
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Woof! I couldn't load your previous list. Starting fresh!");
        }
        return tasks;
    }

    public void saveTasks(ArrayList<Task> tasks) {
        try {
            FileWriter fw = new FileWriter(FILE_PATH);
            for (Task t : tasks) {
                fw.write(formatTaskForFile(t) + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Whimper... I couldn't save your tasks!");
        }
    }

    public String formatTaskForFile(Task t) {
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
}
