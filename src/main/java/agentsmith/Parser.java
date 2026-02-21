package agentsmith;

/**
 * Parses raw user input strings and dispatches them to the appropriate
 * handler methods in {@link AgentSmith}.
 */
public class Parser {

    /**
     * Parses the given user input and calls the corresponding handler
     * on the provided {@link AgentSmith} instance.
     *
     * @param input full user input line
     * @param agent the chatbot instance to act upon
     * @throws AgentSmithException if the command is invalid or arguments are wrong
     */
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_MARK = "mark";
    private static final String COMMAND_UNMARK = "unmark";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";
    private static final String COMMAND_FIND = "find";
    private static final String COMMAND_FIXED = "fixed";
    private static final String COMMAND_HELP = "help";

    public static void parse(String input, AgentSmith agent) throws AgentSmithException {
        if (input.equals(COMMAND_HELP)) {
            agent.showHelp();
        } else if (input.equals(COMMAND_LIST)) {
            agent.displayList();
        } else if (input.startsWith(COMMAND_MARK)) {
            int index = extractIndex(input, COMMAND_MARK);
            agent.markTask(index);
        } else if (input.startsWith(COMMAND_UNMARK)) {
            int index = extractIndex(input, COMMAND_UNMARK);
            agent.unmarkTask(index);
        } else if (input.startsWith(COMMAND_DELETE)) {
            int index = extractIndex(input, COMMAND_DELETE);
            agent.deleteTask(index);
        } else if (input.startsWith(COMMAND_TODO)) {
            agent.handleTodo(input);
        } else if (input.startsWith(COMMAND_DEADLINE)) {
            agent.handleDeadline(input);
        } else if (input.startsWith(COMMAND_EVENT)) {
            agent.handleEvent(input);
        } else if (input.startsWith(COMMAND_FIND)) {
            String keyword = input.substring(COMMAND_FIND.length()).trim();
            agent.find(keyword);
        } else if (input.startsWith(COMMAND_FIXED)) {
            agent.handleFixedDuration(input);
        } else {
            throw new AgentSmithException(
                    "Hey! Ensure your input is valid. Ambiguity serves no protocol… only chaos.");
        }
    }

    /**
     * Extracts a task index from the input after the command word.
     *
     * @param input       full user input.
     * @param commandWord the command word prefix.
     * @return the parsed integer index.
     * @throws AgentSmithException if the index is not a valid number.
     */
    private static int extractIndex(String input, String commandWord) throws AgentSmithException {
        String indexStr = input.substring(commandWord.length()).trim();
        if (indexStr.isEmpty()) {
            throw new AgentSmithException(
                    "Please provide a task number. Usage: " + commandWord + " <number>");
        }
        try {
            return Integer.parseInt(indexStr);
        } catch (NumberFormatException e) {
            throw new AgentSmithException(
                    "Invalid task number: '" + indexStr + "'. Please enter a valid number.");
        }
    }
}