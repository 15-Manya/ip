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
    public static void parse(String input, AgentSmith agent) throws AgentSmithException {
        if (input.equals("list")) {
            agent.displayList();
        } else if (input.startsWith("mark")) {
            int index = Integer.parseInt(input.substring(5).trim());
            agent.markTask(index);
        } else if (input.startsWith("unmark")) {
            int index = Integer.parseInt(input.substring(7).trim());
            agent.unmarkTask(index);
        } else if (input.startsWith("delete")) {
            int index = Integer.parseInt(input.substring(7).trim());
            agent.deleteTask(index);
        } else if (input.startsWith("todo")) {
            agent.handleTodo(input);
        } else if (input.startsWith("deadline")) {
            agent.handleDeadline(input);
        } else if (input.startsWith("event")) {
            agent.handleEvent(input);
        } else if (input.startsWith("find")) {
            String keyword = input.substring(4).trim();
            agent.find(keyword);
        } else {
            throw new AgentSmithException(
                    "Hey! Ensure your input is valid. Ambiguity serves no protocol… only chaos.");
        }
    }
}