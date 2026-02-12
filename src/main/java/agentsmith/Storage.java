package agentsmith;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Handles loading and saving of tasks to a file.
 */
public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        assert filePath != null : "File path should not be null";
        assert !filePath.isEmpty() : "File path should not be empty";
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the save file and returns them as a list.
     *
     * @return list of tasks loaded from disk (empty if file is missing or empty)
     * @throws AgentSmithException if there is a problem reading the file
     */
    public ArrayList<Task> load() throws AgentSmithException {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            File file = new File(filePath);
            File folder = file.getParentFile();
            if (!file.exists()) {
                throw new AgentSmithException("The task list file does not exist.");
            }
            if (folder != null && !folder.exists()) {
                throw new AgentSmithException("The data folder does not exist.");
            }
            if (file.length() <= 0) {
                return tasks;
            }
            if (file.exists()) {
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    Task task = lineToTask(line);
                    tasks.add(task);
                }
                sc.close();
            }
            return tasks;
        } catch (Exception e) {
            System.out.println("Error loading data.");
        }
        return tasks;
    }

    public void loadData(TaskList tasklist) {
        try {
            File file = new File(filePath);
            File folder = file.getParentFile();
            if (!file.exists()) {
                throw new AgentSmithException("The task list file does not exist.");
            }

            if (folder != null && !folder.exists()) {
                throw new AgentSmithException("The data folder does not exist.");
            }

            if (file.length() <= 0) {
                return;
            }

            if (file.exists()) {
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    Task task = lineToTask(line);
                    tasklist.addTask(task);
                }
                sc.close();
            }
        } catch (Exception e) {
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
        assert line != null : "Line to parse should not be null";
        assert !line.isEmpty() : "Line to parse should not be empty";

        try {
            String[] parts = line.split("\\s*\\|\\s*");
            assert parts.length >= 3 : "Line should have at least 3 parts (type, done, description)";
            String type = parts[0];
            boolean isDone = parts.length > 1 && parts[1].trim().equals("1");

            if (type.equals("T")) {
                ToDo todo = new ToDo(parts[2]);
                todo.setIsDone(isDone);
                return todo;
            } else if (type.equals("D")) {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("d MMMM yyyy, h:mma");
                LocalDateTime deadline = LocalDateTime.parse(parts[3], format);
                Deadline d = new Deadline(parts[2], deadline);
                d.setIsDone(isDone);
                return d;
            } else if (type.equals("E")) {
                String[] fromTo = parts[3].split("-");
                Event e = new Event(parts[2], fromTo[0], fromTo[1]);
                e.setIsDone(isDone);
                return e;
            } else {
                throw new AgentSmithException("Invalid task type: " + type);
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
     * @param tasklist task list to persist
     */
    public void saveAll(TaskList tasklist) {
        assert tasklist != null : "TaskList to save should not be null";

        try {
            FileWriter fw = new FileWriter(filePath);
            for (Task task : tasklist.getAll()) {
                fw.write(task.saveString() + System.lineSeparator());
            }
            fw.close();
        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
}

    
    
