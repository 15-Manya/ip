package agentsmith;

/**
 * Represents an exception specific to the AgentSmith application.
 * Thrown when the application encounters user-facing errors.
 */
public class AgentSmithException extends Exception {

    /**
     * Constructs a new AgentSmithException with the specified message.
     *
     * @param message the detail message describing the error.
     */
    public AgentSmithException(String message) {
        super(message);
    }
}
