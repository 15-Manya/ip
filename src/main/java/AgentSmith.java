import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class AgentSmith {

    public static String name = "Agent Smith";

    TaskList tasklist;
    Ui ui;

    final String FILE_PATH = "./data/tasks.txt";
    final String FOLDER_PATH = "./data";

    public AgentSmith() {
        this.tasklist = new TaskList();
        this.ui = new Ui();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AgentSmith agent = new AgentSmith();

        agent.ui.print_logo();

        agent.load_data();

        agent.ui.print_intro(name);

        String input = sc.nextLine();
        input = input.trim();

        while (!input.equals("bye")) { /* loops until the user inputs 'bye' */
            try {
                if (input.equals("list")) {
                    agent.display_list();
                } else if (input.startsWith("mark")) {
                    int index = Integer.parseInt(input.substring(5).trim());
                    agent.mark_task(index);
                } else if (input.startsWith("unmark")) {
                    int index = Integer.parseInt(input.substring(7).trim());
                    agent.unmark_task(index);
                } else if (input.startsWith("delete")) {
                    int index = Integer.parseInt(input.substring(7).trim());
                    agent.delete_task(index);
                } else if (input.startsWith("todo")) {
                    agent.handle_todo(input);
                } else if (input.startsWith("deadline")) {
                    agent.handle_deadline(input);
                } else if (input.startsWith("event")) {
                    agent.handle_event(input);
                } else {
                    throw new AgentSmithException(
                            "Hey! Ensure your input is valid. Ambiguity serves no protocol… only chaos.");
                }
            } catch (AgentSmithException e) {
                agent.ui.print_line();
                System.out.println("\t" + e.getMessage());
                agent.ui.print_line();
                System.out.println();
            }
            input = sc.nextLine();
            input = input.trim();
        }

        agent.ui.print_bye();
    }

    public void load_data() {
        ui.print_line();
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
        ui.print_line();
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

    public void save_all() {
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

    public void handle_todo(String input) throws AgentSmithException {
        if (input.length() <= 4) {
            throw new AgentSmithException(
                    "Ensure the description for your todo… is not empty. A void serves no purpose in the system.");
        }
        String description = input.substring(5).trim();
        if (description.isEmpty()) {
            throw new AgentSmithException(
                    "Ensure the description for your todo… is not empty. A void serves no purpose in the system.");
        }
        Task new_task = new ToDo(description);
        this.add_task(new_task);
    }

    public void handle_deadline(String input) throws AgentSmithException {
        if (input.length() <= 8) {
            throw new AgentSmithException(
                    "Hey! Ensure your input is valid. Ambiguity serves no protocol… only chaos.");
        }

        input = input.substring(8).trim();
        if (input.isEmpty()) {
            throw new AgentSmithException(
                    "Hey! Ensure your input is valid. Ambiguity serves no protocol… only chaos.");
        }

        int by_index = input.indexOf("/by");
        if (by_index == -1) {
            throw new AgentSmithException(
                    "There must be a deadline. Eternity is not an option within this construct.");
        }

        String description = input.substring(0, by_index).trim();
        if (description.isEmpty()) {
            throw new AgentSmithException(
                    "Ensure the description for your deadline… is not empty. A void serves no purpose in the system.");
        }

        String deadline = input.substring(by_index + 3).trim();
        if (deadline.isEmpty()) {
            throw new AgentSmithException(
                    "The deadline… cannot remain empty. Time in the Matrix waits for no anomaly.");
        }

        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            LocalDateTime deadlineDateTime = LocalDateTime.parse(deadline, format);

            Task new_task = new Deadline(description, deadlineDateTime);
            this.add_task(new_task);
        } catch (DateTimeParseException e) {
            throw new AgentSmithException(
                    "Invalid deadline format. Please use: yyyy-MM-dd HHmm (e.g. 2026-01-30 1630).");
        }
    }

    public void handle_event(String input) throws AgentSmithException {
        if (input.length() <= 5) {
            throw new AgentSmithException(
                    "Hey! Ensure your input is valid. Ambiguity serves no protocol… only chaos.");
        }

        input = input.substring(6).trim();
        if (input.isEmpty()) {
            throw new AgentSmithException(
                    "Hey! Ensure your input is valid. Ambiguity serves no protocol… only chaos.");
        }

        int from_index = input.indexOf("/from");
        int to_index = input.indexOf("/to");
        if (from_index == -1 || to_index == -1) {
            throw new AgentSmithException(
                    "A start time and end time are required. The protocol demands boundaries… chaos does not.");
        }

        String description = input.substring(0, from_index).trim();
        if (description.isEmpty()) {
            throw new AgentSmithException(
                    "Ensure the description for your deadline… is not empty. A void serves no purpose in the system.");
        }

        String from = input.substring(from_index + 6, to_index).trim();
        String to = input.substring(to_index + 3).trim();
        if (from.isEmpty() || to.isEmpty()) {
            throw new AgentSmithException(
                    "The start time and end time cannot remain empty. Time in the Matrix waits for no anomaly.");
        }

        Task new_task = new Event(description, from, to);
        this.add_task(new_task);
    }

    public void display_list() {
        ui.print_line();
        if (tasklist.size() == 0) {
            System.out.println("\tYour task list stands empty. The system requires… purpose. Add a command.");
        } else {
            System.out.println("\tHere are the tasks in your list:");
            for (int i = 0; i < tasklist.size(); i++) {
                Task task = tasklist.get(i);
                System.out.println("\t" + (i + 1) + ". " + task.toString());
            }
        }
        ui.print_line();
        System.out.println();
    }

    public void add_task(Task task) throws AgentSmithException {
        tasklist.add_task(task);
        save_all();
        ui.print_line();
        System.out.println("\tAcknowledged. The task has been integrated into the system…");
        System.out.println("\t  " + task.toString());
        System.out.println("\tNow you have " + tasklist.size() + " tasks in the list.");
        ui.print_line();
        System.out.println();
    }

    public void mark_task(int index) throws AgentSmithException {
        ui.print_line();
        tasklist.mark_task(index);
        save_all();
        System.out.println("\tAcknowledged! I've marked this task as done:");
        System.out.println("\t" + index + ". " + tasklist.get(index - 1).toString());
        ui.print_line();
        System.out.println();
    }

    public void unmark_task(int index) throws AgentSmithException {
        ui.print_line();
        tasklist.unmark_task(index);
        save_all();
        System.out.println("\tUnderstood. The task remains… active:");
        System.out.println(
                "\t" + index + ". " + tasklist.get(index - 1).toString());
        ui.print_line();
        System.out.println();
    }

    public void delete_task(int index) throws AgentSmithException {
        ui.print_line();
        System.out.println("\tAcknowledged. The task has been erased from the system");
        System.out.println("\t  " + index + ". " + tasklist.get(index - 1).toString());
        tasklist.delete_task(index);
        save_all();
        System.out.println("\tNow you have " + tasklist.size() + " tasks in the list.");
        ui.print_line();
        System.out.println();
    }
}
