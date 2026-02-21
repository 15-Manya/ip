package agentsmith;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Main chatbot class that coordinates the UI, storage, and task list.
 */
public class AgentSmith {

    /** Name of the chatbot. */
    public static final String NAME = "Agent Smith";

    /** Error message for empty or invalid input. */
    private static final String ERROR_INVALID_INPUT =
            "Hey! Ensure your input is valid. Ambiguity serves no protocol… only chaos.";

    /** Error message for empty description. */
    private static final String ERROR_EMPTY_DESCRIPTION =
            "Ensure the description for your todo… is not empty. A void serves no purpose in the system.";

    private TaskList taskList;
    private Ui ui;
    private Storage storage;

    /**
     * Creates an {@code AgentSmith} instance using the given save file path.
     *
     * @param filePath path to the save file.
     */
    public AgentSmith(String filePath) {
        this(filePath, true);
    }

    /**
     * Creates an {@code AgentSmith} instance using the given save file path
     * and an option to control whether to print the greeting on startup.
     *
     * @param filePath     path to the save file.
     * @param showGreeting true if the greeting should be printed, false otherwise.
     */
    public AgentSmith(String filePath, boolean showGreeting) {
        assert filePath != null : "File path should not be null";
        assert !filePath.isEmpty() : "File path should not be empty";

        this.ui = new Ui();
        this.storage = new Storage(filePath);

        if (showGreeting) {
            ui.printLogo();
            ui.printIntro(NAME);
        }

        try {
            this.taskList = new TaskList(storage.load());
        } catch (AgentSmithException e) {
            ui.printLine();
            System.out.println("\tThe task list is empty.");
            ui.printLine();
            this.taskList = new TaskList();
        }

        assert this.ui != null : "UI should be initialized";
        assert this.storage != null : "Storage should be initialized";
        assert this.taskList != null : "TaskList should be initialized";
    }

    /**
     * Runs the main interaction loop until the user enters {@code bye}.
     */
    public void run() {
        Scanner sc = new Scanner(System.in);

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
        String absolutePath = new File("data/tasks.txt").getAbsolutePath();
        new AgentSmith(absolutePath).run();
    }

    /**
     * Extracts the arguments portion from a command input by removing the command word.
     *
     * @param input       full user input string.
     * @param commandWord the command word to strip.
     * @return the arguments after the command word, trimmed.
     * @throws AgentSmithException if there are no arguments.
     */
    private String extractArguments(String input, String commandWord) throws AgentSmithException {
        if (input.length() <= commandWord.length()) {
            throw new AgentSmithException(ERROR_INVALID_INPUT);
        }
        String arguments = input.substring(commandWord.length()).trim();
        if (arguments.isEmpty()) {
            throw new AgentSmithException(ERROR_INVALID_INPUT);
        }
        return arguments;
    }

    /**
     * Validates that a description is not empty.
     *
     * @param description the description to validate.
     * @throws AgentSmithException if the description is empty.
     */
    private void validateDescription(String description) throws AgentSmithException {
        if (description.isEmpty()) {
            throw new AgentSmithException(ERROR_EMPTY_DESCRIPTION);
        }
    }

    /**
     * Parses a date-time string in the format "yyyy-MM-dd HHmm".
     *
     * @param dateTimeString the string to parse.
     * @return the parsed LocalDateTime.
     * @throws AgentSmithException if the format is invalid.
     */
    private LocalDateTime parseDateTime(String dateTimeString) throws AgentSmithException {
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
            return LocalDateTime.parse(dateTimeString, format);
        } catch (DateTimeParseException e) {
            throw new AgentSmithException(
                    "Invalid deadline format. Please use: yyyy-MM-dd HHmm (e.g. 2026-01-30 1630).");
        }
    }

    /**
     * Handles the creation of a todo task from the given input.
     *
     * @param input raw user command string.
     * @throws AgentSmithException if the description is missing or empty.
     */
    public void handleTodo(String input) throws AgentSmithException {
        String description = extractArguments(input, "todo");
        validateDescription(description);
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
        String arguments = extractArguments(input, "deadline");
        String[] parts = splitByDelimiter(arguments, "/by",
                "There must be a deadline. Eternity is not an option within this construct.");

        String description = parts[0];
        String deadlineString = parts[1];

        validateDescription(description);
        validateNotEmpty(deadlineString,
                "The deadline… cannot remain empty. Time in the Matrix waits for no anomaly.");

        LocalDateTime deadlineDateTime = parseDateTime(deadlineString);
        Task newTask = new Deadline(description, deadlineDateTime);
        this.addTask(newTask);
    }

    /**
     * Splits arguments by a delimiter and returns the two parts.
     *
     * @param arguments    the arguments string to split.
     * @param delimiter    the delimiter to split by.
     * @param errorMessage error message if delimiter is not found.
     * @return array with [beforeDelimiter, afterDelimiter], both trimmed.
     * @throws AgentSmithException if delimiter is not found.
     */
    private String[] splitByDelimiter(String arguments, String delimiter, String errorMessage)
            throws AgentSmithException {
        int index = arguments.indexOf(delimiter);
        if (index == -1) {
            throw new AgentSmithException(errorMessage);
        }
        String before = arguments.substring(0, index).trim();
        String after = arguments.substring(index + delimiter.length()).trim();
        return new String[]{before, after};
    }

    /**
     * Validates that a string is not empty.
     *
     * @param value        the string to validate.
     * @param errorMessage the error message if empty.
     * @throws AgentSmithException if the string is empty.
     */
    private void validateNotEmpty(String value, String errorMessage) throws AgentSmithException {
        if (value.isEmpty()) {
            throw new AgentSmithException(errorMessage);
        }
    }

    /**
     * Handles the creation of an event task from the given input.
     *
     * @param input raw user command string.
     * @throws AgentSmithException if the description or time range is invalid.
     */
    public void handleEvent(String input) throws AgentSmithException {
        String arguments = extractArguments(input, "event");

        String[] descAndTimes = splitByDelimiter(arguments, "/from",
                "A start time and end time are required. The protocol demands boundaries… chaos does not.");
        String description = descAndTimes[0];
        String timesString = descAndTimes[1];

        String[] times = splitByDelimiter(timesString, "/to",
                "A start time and end time are required. The protocol demands boundaries… chaos does not.");
        String from = times[0];
        String to = times[1];

        validateDescription(description);
        validateNotEmpty(from,
                "The start time and end time cannot remain empty. Time in the Matrix waits for no anomaly.");
        validateNotEmpty(to,
                "The start time and end time cannot remain empty. Time in the Matrix waits for no anomaly.");

        Task newTask = new Event(description, from, to);
        this.addTask(newTask);
    }

    /**
     * Handles the creation of a fixed duration task from the given input.
     * Format: fixed DESCRIPTION /needs DURATION
     *
     * @param input raw user command string.
     * @throws AgentSmithException if the description or duration is invalid.
     */
    public void handleFixedDuration(String input) throws AgentSmithException {
        String arguments = extractArguments(input, "fixed");
        String[] parts = splitByDelimiter(arguments, "/needs",
                "A duration is required. Use: fixed DESCRIPTION /needs DURATION");

        String description = parts[0];
        String duration = parts[1];

        validateDescription(description);
        validateNotEmpty(duration,
                "The duration cannot be empty. Specify how long the task needs.");

        Task newTask = new FixedDurationTask(description, duration);
        this.addTask(newTask);
    }

    /**
     * Displays the help message with available commands.
     */
    public void showHelp() {
        ui.printLine();
        System.out.println("\tAvailable commands:");
        System.out.println("\t  list                           - Show all tasks");
        System.out.println("\t  todo DESCRIPTION               - Add a todo task");
        System.out.println("\t  deadline DESC /by DATE         - Add a deadline (DATE: yyyy-MM-dd HHmm)");
        System.out.println("\t  event DESC /from START /to END - Add an event");
        System.out.println("\t  fixed DESC /needs DURATION     - Add a fixed duration task");
        System.out.println("\t  mark NUMBER                    - Mark task as done");
        System.out.println("\t  unmark NUMBER                  - Mark task as not done");
        System.out.println("\t  delete NUMBER                  - Delete a task");
        System.out.println("\t  find KEYWORD                   - Find tasks containing keyword");
        System.out.println("\t  help                           - Show this help message");
        System.out.println("\t  bye                            - Exit the program");
        ui.printLine();
        System.out.println();
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
        assert task != null : "Task to add should not be null";

        int sizeBefore = taskList.size();
        taskList.addTask(task);
        storage.saveAll(taskList);

        assert taskList.size() == sizeBefore + 1 : "Task list size should increase by 1";

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

    /**
     * Finds and displays all tasks that contain the given keyword.
     *
     * @param keyword the keyword to search for in task descriptions.
     * @throws AgentSmithException if the keyword is empty.
     */
    public void find(String keyword) throws AgentSmithException {
        ui.printLine();
        taskList.find(keyword);
        ui.printLine();
        System.out.println();
    }

    /**
     * Returns AgentSmith's response to a single line of user input.
     * This method is intended for use by the JavaFX UI.
     *
     * @param input user input string.
     * @return response text.
     */
    public String getResponse(String input) {
        if (input == null || input.isBlank()) {
            return "";
        }
        if ("bye".equals(input.trim())) {
            Ui tempUi = new Ui();
            StringBuilder sb = new StringBuilder();
            sb.append(tempUi.getLineText()).append(System.lineSeparator())
                    .append("\tUntil next time. The Matrix… never sleeps.").append(System.lineSeparator())
                    .append(tempUi.getLineText()).append(System.lineSeparator());
            return sb.toString();
        }

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream tempOut = new PrintStream(buffer);
        PrintStream originalOut = System.out;

        try {
            System.setOut(tempOut);
            Parser.parse(input, this);
        } catch (AgentSmithException e) {
            // Match CLI error formatting: line, message, line, blank line.
            Ui tempUi = new Ui();
            StringBuilder sb = new StringBuilder();
            sb.append(tempUi.getLineText()).append(System.lineSeparator())
                    .append("\t").append(e.getMessage()).append(System.lineSeparator())
                    .append(tempUi.getLineText()).append(System.lineSeparator())
                    .append(System.lineSeparator());
            return sb.toString();
        } finally {
            System.setOut(originalOut);
        }

        return buffer.toString();
    }

    /**
     * Returns the raw persisted representation of all tasks,
     * one line per task, in the same format as stored in {@code data/tasks.txt}.
     *
     * @return raw task lines.
     */
    public String getRawTaskLines() {
        StringBuilder sb = new StringBuilder();
        for (Task task : taskList.getAll()) {
            sb.append(task.saveString()).append(System.lineSeparator());
        }
        return sb.toString().trim();
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
