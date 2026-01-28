import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.ArrayList;

public class Storage {
    final String FILE_PATH = "./data/tasks.txt";
    final String FOLDER_PATH = "./data";

    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws AgentSmithException {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                throw new AgentSmithException("The task list file does not exist.");
            }
            if (!new File(FOLDER_PATH).exists()) {
                throw new AgentSmithException("The data folder does not exist.");
            }
            if (file.length() <= 0) {
                System.out.println("The task list is empty.");
                return tasks;
            }
            if (file.exists()) {
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    Task task = lineToTask(line);
                    tasks.add(task);
                }
            }
            return tasks;
        } catch (Exception e) {
            System.out.println("Error loading data.");
        }
        return tasks;
    }

    public void load_data(TaskList tasklist) {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                throw new AgentSmithException("The task list file does not exist.");
            }

            if (!new File(FOLDER_PATH).exists()) {
                throw new AgentSmithException("The data folder does not exist.");
            }

            if (file.length() <= 0) {
                System.out.println("The task list is empty.");
                return;
            }

            if (file.exists()) {
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    Task task = lineToTask(line);
                    tasklist.add_task(task);
                    System.out.println(line);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading data.");
        }
    }

    // this method is used to convert the task line stored in the file to a Task
    // object
    public Task lineToTask(String line) {
        try {
            String[] parts = line.split("\\s*\\|\\s*");
            if (parts[0].equals("T")) {
                return new ToDo(parts[2]);
            } else if (parts[0].equals("D")) {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("d MMMM yyyy, h:mma");
                LocalDateTime deadline = LocalDateTime.parse(parts[3], format);
                return new Deadline(parts[2], deadline);
            } else if (parts[0].equals("E")) {
                String[] fromTo = parts[3].split("-");
                return new Event(parts[2], fromTo[0], fromTo[1]);
            } else {
                throw new AgentSmithException("Invalid task type: " + parts[0]);
            }
        } catch (AgentSmithException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void save_all(TaskList tasklist) {
        try {
            FileWriter fw = new FileWriter(FILE_PATH);
            for (Task task : tasklist.getAll()) {
                fw.write(task.saveString() + System.lineSeparator());
            }
            fw.close();
        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
}
