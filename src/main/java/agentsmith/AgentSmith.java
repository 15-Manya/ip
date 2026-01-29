package agentsmith;

import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Main chatbot class that coordinates the UI, storage, and task list.
 */
public class AgentSmith {

    public static String name = "Agent Smith";

    TaskList taskList;
    Ui ui;
    Storage storage;

    /**
     * Creates an {@code AgentSmith} instance using the given save file path.
     *
     * @param filePath path to the save file.
     */
    public AgentSmith(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        try {
            this.taskList = new TaskList(storage.load());
        } catch (AgentSmithException e) {
            ui.printLine();
            System.out.println("\tThe task list is empty.");
            ui.printLine();
            this.taskList = new TaskList();
        }
    }

    /**
     * Runs the main interaction loop until the user enters {@code bye}.
     */
    public void run() {
        Scanner sc = new Scanner(System.in);

        ui.printLogo();
        ui.printIntro(name);

        String input = sc.nextLine().trim();

        while (!input.equals("bye")) {
            try {
                Parser.parse(input, this);
            } catch (AgentSmithException e) {
                ui.printLine();
                System.out.println("\t" + e.getMessage());
                ui.printLine();
                System.out.println();
            }
            input = sc.nextLine().trim();
        }

        ui.printBye();
    }

    /**
     * Entry point of the program.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        new AgentSmith("data/tasks.txt").run();
    }

    /**
     * Handles the creation of a todo task from the given input.
     *
     * @param input raw user command string.
     * @throws AgentSmithException if the description is missing or empty.
     */
    public void handleTodo(String input) throws AgentSmithException {
        if (input.length() <= 4) {
            throw new AgentSmithException(
                    "Ensure the description for your todo… is not empty. A void serves no purpose in the system.");
        }
        String description = input.substring(5).trim();
        if (description.isEmpty()) {
            throw new AgentSmithException(
                    "Ensure the description for your todo… is not empty. A void serves no purpose in the system.");
        }
        Task newTask = new ToDo(description);
        this.addTask(newTask);
    }

    /**
     * Handles the creation of a deadline task from the given input.
     *
     * @param input raw user command string.
     * @throws AgentSmithException if the description or deadline is invalid.
     */
    public void handleDeadline(String input) throws AgentSmithException {
        if (input.length() <= 8) {
            throw new AgentSmithException(
                    "Hey! Ensure your input is valid. Ambiguity serves no protocol… only chaos.");
        }

        input = input.substring(8).trim();
        if (input.isEmpty()) {
            throw new AgentSmithException(
                    "Hey! Ensure your input is valid. Ambiguity serves no protocol… only chaos.");
        }

        int byIndex = input.indexOf("/by");
        if (byIndex == -1) {
            throw new AgentSmithException(
                    "There must be a deadline. Eternity is not an option within this construct.");
        }

        String description = input.substring(0, byIndex).trim();
        if (description.isEmpty()) {
            throw new AgentSmithException(
                    "Ensure the description for your deadline… is not empty. A void serves no purpose in the system.");
        }

        String deadline = input.substring(byIndex + 3).trim();
        if (deadline.isEmpty()) {
            throw new AgentSmithException(
                    "The deadline… cannot remain empty. Time in the Matrix waits for no anomaly.");
        }

        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            LocalDateTime deadlineDateTime = LocalDateTime.parse(deadline, format);

            Task newTask = new Deadline(description, deadlineDateTime);
            this.addTask(newTask);
        } catch (DateTimeParseException e) {
            throw new AgentSmithException(
                    "Invalid deadline format. Please use: yyyy-MM-dd HHmm (e.g. 2026-01-30 1630).");
        }
    }

    /**
     * Handles the creation of an event task from the given input.
     *
     * @param input raw user command string.
     * @throws AgentSmithException if the description or time range is invalid.
     */
    public void handleEvent(String input) throws AgentSmithException {
        if (input.length() <= 5) {
            throw new AgentSmithException(
                    "Hey! Ensure your input is valid. Ambiguity serves no protocol… only chaos.");
        }

        input = input.substring(6).trim();
        if (input.isEmpty()) {
            throw new AgentSmithException(
                    "Hey! Ensure your input is valid. Ambiguity serves no protocol… only chaos.");
        }

        int fromIndex = input.indexOf("/from");
        int toIndex = input.indexOf("/to");
        if (fromIndex == -1 || toIndex == -1) {
            throw new AgentSmithException(
                    "A start time and end time are required. The protocol demands boundaries… chaos does not.");
        }

        String description = input.substring(0, fromIndex).trim();
        if (description.isEmpty()) {
            throw new AgentSmithException(
                    "Ensure the description for your deadline… is not empty. A void serves no purpose in the system.");
        }

        String from = input.substring(fromIndex + 6, toIndex).trim();
        String to = input.substring(toIndex + 3).trim();
        if (from.isEmpty() || to.isEmpty()) {
            throw new AgentSmithException(
                    "The start time and end time cannot remain empty. Time in the Matrix waits for no anomaly.");
        }

        Task newTask = new Event(description, from, to);
        this.addTask(newTask);
    }

    /**
     * Displays all tasks in the current task list.
     */
    public void displayList() {
        ui.printLine();
        if (taskList.size() == 0) {
            System.out.println("\tYour task list stands empty. The system requires… purpose. Add a command.");
        } else {
            System.out.println("\tHere are the tasks in your list:");
            for (int i = 0; i < taskList.size(); i++) {
                Task task = taskList.get(i);
                System.out.println("\t" + (i + 1) + ". " + task.toString());
            }
        }
        ui.printLine();
        System.out.println();
    }

    /**
     * Adds the given task to the list and persists the updated list.
     *
     * @param task task to add.
     * @throws AgentSmithException if the list is at capacity.
     */
    public void addTask(Task task) throws AgentSmithException {
        taskList.addTask(task);
        storage.saveAll(taskList);
        ui.printLine();
        System.out.println("\tAcknowledged. The task has been integrated into the system…");
        System.out.println("\t  " + task.toString());
        System.out.println("\tNow you have " + taskList.size() + " tasks in the list.");
        ui.printLine();
        System.out.println();
    }

    /**
     * Marks the task at the given 1-based index as done and saves the list.
     *
     * @param index 1-based index of the task to mark.
     * @throws AgentSmithException if the index is invalid.
     */
    public void markTask(int index) throws AgentSmithException {
        ui.printLine();
        taskList.markTask(index);
        storage.saveAll(taskList);
        System.out.println("\tAcknowledged! I've marked this task as done:");
        System.out.println("\t" + index + ". " + taskList.get(index - 1).toString());
        ui.printLine();
        System.out.println();
    }

    /**
     * Marks the task at the given 1-based index as not done and saves the list.
     *
     * @param index 1-based index of the task to unmark.
     * @throws AgentSmithException if the index is invalid.
     */
    public void unmarkTask(int index) throws AgentSmithException {
        ui.printLine();
        taskList.unmarkTask(index);
        storage.saveAll(taskList);
        System.out.println("\tUnderstood. The task remains… active:");
        System.out.println(
                "\t" + index + ". " + taskList.get(index - 1).toString());
        ui.printLine();
        System.out.println();
    }

    public void find(String keyword) throws AgentSmithException {
        ui.printLine();
        taskList.find(keyword);
        ui.printLine();
        System.out.println();
    }

    /**
     * Deletes the task at the given 1-based index and saves the list.
     *
     * @param index 1-based index of the task to delete.
     * @throws AgentSmithException if the index is invalid.
     */
    public void deleteTask(int index) throws AgentSmithException {
        ui.printLine();
        System.out.println("\tAcknowledged. The task has been erased from the system");
        System.out.println("\t  " + index + ". " + taskList.get(index - 1).toString());
        taskList.deleteTask(index);
        storage.saveAll(taskList);
        System.out.println("\tNow you have " + taskList.size() + " tasks in the list.");
        ui.printLine();
        System.out.println();
    }
}
