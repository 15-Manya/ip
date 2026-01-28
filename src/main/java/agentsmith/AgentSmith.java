package agentsmith;

import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class AgentSmith {

    public static String name = "Agent Smith";

    TaskList tasklist;
    Ui ui;
    Storage storage;

    public AgentSmith(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        try {
            this.tasklist = new TaskList(storage.load());
        } catch (AgentSmithException e) {
            ui.print_line();
            System.out.println("\tThe task list is empty.");
            ui.print_line();
            this.tasklist = new TaskList();
        }
    }

    public void run() {
        Scanner sc = new Scanner(System.in);

        ui.print_logo();
        ui.print_intro(name);

        String input = sc.nextLine().trim();

        while (!input.equals("bye")) {
            try {
                Parser.parse(input, this);
            } catch (AgentSmithException e) {
                ui.print_line();
                System.out.println("\t" + e.getMessage());
                ui.print_line();
                System.out.println();
            }
            input = sc.nextLine().trim();
        }

        ui.print_bye();
    }

    public static void main(String[] args) {
        new AgentSmith("data/tasks.txt").run();
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
        storage.save_all(tasklist);
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
        storage.save_all(tasklist);
        System.out.println("\tAcknowledged! I've marked this task as done:");
        System.out.println("\t" + index + ". " + tasklist.get(index - 1).toString());
        ui.print_line();
        System.out.println();
    }

    public void unmark_task(int index) throws AgentSmithException {
        ui.print_line();
        tasklist.unmark_task(index);
        storage.save_all(tasklist);
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
        storage.save_all(tasklist);
        System.out.println("\tNow you have " + tasklist.size() + " tasks in the list.");
        ui.print_line();
        System.out.println();
    }
}
