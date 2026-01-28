package agentsmith;

public class Parser {
    public static void parse(String input, AgentSmith agent) throws AgentSmithException {
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
    }
}
