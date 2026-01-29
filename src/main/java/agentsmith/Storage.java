package agentsmith;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles loading and saving of tasks to a file.
 */
public class Storage {

    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the save file and returns them as a list.
     *
     * @return list of tasks loaded from disk (empty if file is missing or empty).
     * @throws AgentSmithException if the file or folder does not exist.
     */
    public ArrayList<Task> load() throws AgentSmithException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        File folder = file.getParentFile();

        if (folder != null && !folder.exists()) {
            throw new AgentSmithException("The data folder does not exist.");
        }
        if (!file.exists()) {
            throw new AgentSmithException("The task list file does not exist.");
        }
        if (file.length() <= 0) {
            System.out.println("The task list is empty.");
            return tasks;
        }

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Task task = lineToTask(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading data.");
        }
        return tasks;
    }

    /**
     * Loads tasks from the save file into the given task list.
     *
     * @param taskList task list to populate.
     */
    public void loadData(TaskList taskList) throws AgentSmithException {
        File file = new File(filePath);
        File folder = file.getParentFile();

        if (folder != null && !folder.exists()) {
            throw new AgentSmithException("The data folder does not exist.");
        }
        if (!file.exists()) {
            throw new AgentSmithException("The task list file does not exist.");
        }
        if (file.length() <= 0) {
            System.out.println("The task list is empty.");
            return;
        }

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Task task = lineToTask(line);
                if (task != null) {
                    taskList.addTask(task);
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading data.");
        }
    }

    /**
     * Converts a single line from the save file into a {@link Task} instance.
     *
     * @param line line from the save file
     * @return reconstructed task, or {@code null} if the line is invalid
     */
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

    /**
     * Saves all tasks in the given {@link TaskList} to the save file,
     * overwriting any existing contents.
     *
     * @param taskList task list to persist.
     */
    public void saveAll(TaskList taskList) {
        try (FileWriter fw = new FileWriter(filePath)) {
            for (Task task : taskList.getAll()) {
                fw.write(task.saveString() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
}
