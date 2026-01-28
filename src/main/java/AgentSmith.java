import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class AgentSmith {

    public static String name = "Agent Smith";
    private ArrayList<Task> tasklist = new ArrayList<>();
    private String user_name;

    final String FILE_PATH = "./data/tasks.txt";
    final String FOLDER_PATH = "./data";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AgentSmith agent = new AgentSmith();

        String logo = "        _                    _     ____            _ _   _       \n" +
                "       / \\   __ _  ___ _ __ | |_  / ___| _ __ ___ (_) |_| |__   \n" +
                "      / _ \\ / _` |/ _ \\ '_ \\| __| \\___ \\| '_ ` _ \\| | __| '_ \\   \n" +
                "     / ___ \\ (_| |  __/ | | | |_   ___) | | | | | | | |_| | | |  \n" +
                "    /_/   \\_\\__, |\\___|_| |_|\\__| |____/|_| |_| |_|_|\\__|_| |_|  \n" +
                "             |___/                                                \n";

        System.out.println("\tHello from\n" + logo);
        try {
            System.out.println("\tWhat may I call you?");
            agent.user_name = sc.nextLine();
            agent.user_name = agent.user_name.trim();
            if (agent.user_name.isEmpty()) {
                throw new AgentSmithException(
                        "Ensure your name is not blank. A name is the foundation of identity—it is… existence.");
            }
        } catch (AgentSmithException e) {
            System.out.println(e.getMessage());
        }

        agent.load_data();

        print_intro();
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
                            "Hey! Ensure your input is valid, " + agent.user_name
                                    + ". Ambiguity serves no protocol… only chaos.");
                }
            } catch (AgentSmithException e) {
                print_line();
                System.out.println("\t" + e.getMessage());
                print_line();
                System.out.println();
            }
            input = sc.nextLine();
            input = input.trim();
        }

        print_bye();
    }

    public static void print_line() {
        System.out.println("     ________________________________________________________");
    }

    public void load_data() {
        print_line();
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
                    tasklist.add(task);
                    System.out.println(line);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading data.");
        }
        print_line();
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
            for (Task task : tasklist) {
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
                    "Ensure the description for your todo… is not empty, " + this.user_name
                            + ". A void serves no purpose in the system.");
        }
        String description = input.substring(5).trim();
        if (description.isEmpty()) {
            throw new AgentSmithException(
                    "Ensure the description for your todo… is not empty, " + this.user_name
                            + ". A void serves no purpose in the system.");
        }
        Task new_task = new ToDo(description);
        this.add_task(new_task);
    }

    public void handle_deadline(String input) throws AgentSmithException {
        if (input.length() <= 8) {
            throw new AgentSmithException(
                    "Hey! Ensure your input is valid, " + this.user_name
                            + ". Ambiguity serves no protocol… only chaos.");
        }

        input = input.substring(8).trim();
        if (input.isEmpty()) {
            throw new AgentSmithException(
                    "Hey! Ensure your input is valid, " + this.user_name
                            + ". Ambiguity serves no protocol… only chaos.");
        }

        int by_index = input.indexOf("/by");
        if (by_index == -1) {
            throw new AgentSmithException(
                    "There must be a deadline, " + this.user_name
                            + ". Eternity is not an option within this construct.");
        }

        String description = input.substring(0, by_index).trim();
        if (description.isEmpty()) {
            throw new AgentSmithException(
                    "Ensure the description for your deadline… is not empty, " + this.user_name
                            + ". A void serves no purpose in the system.");
        }

        String deadline = input.substring(by_index + 3).trim();
        if (deadline.isEmpty()) {
            throw new AgentSmithException(
                    "The deadline… cannot remain empty, " + this.user_name
                            + ". Time in the Matrix waits for no anomaly.");
        }

        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            LocalDateTime deadlineDateTime = LocalDateTime.parse(deadline, format);

            Task new_task = new Deadline(description, deadlineDateTime);
            this.add_task(new_task);
        } catch (DateTimeParseException e) {
            throw new AgentSmithException(
                    "Invalid deadline format, " + this.user_name
                            + ". Please use: yyyy-MM-dd HHmm (e.g. 2026-01-30 1630).");
        }
    }

    public void handle_event(String input) throws AgentSmithException {
        if (input.length() <= 5) {
            throw new AgentSmithException(
                    "Hey! Ensure your input is valid, " + this.user_name
                            + ". Ambiguity serves no protocol… only chaos.");
        }

        input = input.substring(6).trim();
        if (input.isEmpty()) {
            throw new AgentSmithException(
                    "Hey! Ensure your input is valid, " + this.user_name
                            + ". Ambiguity serves no protocol… only chaos.");
        }

        int from_index = input.indexOf("/from");
        int to_index = input.indexOf("/to");
        if (from_index == -1 || to_index == -1) {
            throw new AgentSmithException(
                    "A start time and end time are required, " + this.user_name
                            + ". The protocol demands boundaries… chaos does not.");
        }

        String description = input.substring(0, from_index).trim();
        if (description.isEmpty()) {
            throw new AgentSmithException(
                    "Ensure the description for your deadline… is not empty, " + this.user_name
                            + ". A void serves no purpose in the system.");
        }

        String from = input.substring(from_index + 6, to_index).trim();
        String to = input.substring(to_index + 3).trim();
        if (from.isEmpty() || to.isEmpty()) {
            throw new AgentSmithException(
                    "The start time and end time cannot remain empty, " + this.user_name
                            + ". Time in the Matrix waits for no anomaly.");
        }

        Task new_task = new Event(description, from, to);
        this.add_task(new_task);
    }

    public static void print_intro() {
        print_line();
        System.out.println("\tGreetings. You are speaking to…" + name);
        System.out.println("\tWhat can this system do…for you?");
        print_line();
        System.out.println();
    }

    public void display_list() {
        print_line();
        if (tasklist.size() == 0) {
            System.out.println("\tYour task list stands empty, " + this.user_name
                    + ". The system requires… purpose. Add a command.");
        } else {
            System.out.println("\tHere are the tasks in your list:");
            for (int i = 0; i < tasklist.size(); i++) {
                Task task = tasklist.get(i);
                System.out.println("\t" + (i + 1) + ". " + task.toString());
            }
        }
        print_line();
        System.out.println();
    }

    public void add_task(Task task) throws AgentSmithException {
        if (tasklist.size() >= 100) {
            throw new AgentSmithException(
                    "The task list is at capacity, " + this.user_name
                            + ". The system rejects further anomalies… prune or perish.");
        }

        tasklist.add(task);
        save_all();
        print_line();
        System.out.println("\tAcknowledged. The task has been integrated into the system…");
        System.out.println("\t  " + task.toString());
        System.out.println("\tNow you have " + tasklist.size() + " tasks in the list.");
        print_line();
        System.out.println();
    }

    public void mark_task(int index) {
        if (index < 1 || index > tasklist.size()) { // check if the task number is valid
            print_line();
            System.out.println("\tInvalid task number");
            print_line();
            System.out.println();
            return;
        }

        tasklist.get(index - 1).setIsDone(true);
        save_all();
        print_line();
        System.out.println("\tAcknowledged! I've marked this task as done:");
        System.out.println("\t" + index + ". " + tasklist.get(index - 1).toString());
        print_line();
        System.out.println();
    }

    public void unmark_task(int index) {
        if (index < 1 || index > tasklist.size()) { // check if the task number is valid
            print_line();
            System.out.println("\tInvalid task number");
            print_line();
            System.out.println();
            return;
        }

        tasklist.get(index - 1).setIsDone(false);
        save_all();
        print_line();
        System.out.println("\tUnderstood. The task remains… active:");
        System.out.println(
                "\t" + index + ". " + tasklist.get(index - 1).toString());
        print_line();
        System.out.println();
    }

    public void delete_task(int index) {
        if (index < 1 || index > tasklist.size()) { // check if the task number is valid
            print_line();
            System.out.println("\tInvalid task number");
            print_line();
            System.out.println();
            return;
        }
        print_line();
        System.out.println("\tAcknowledged. The task has been erased from the system");
        System.out.println("\t  " + index + ". " + tasklist.get(index - 1).toString());
        tasklist.remove(index - 1);
        save_all();
        System.out.println("\tNow you have " + tasklist.size() + " tasks in the list.");
        print_line();
        System.out.println();
    }

    public static void print_bye() {
        print_line();
        System.out.println("\tUntil next time. The Matrix… never sleeps.");
        print_line();
    }
}
