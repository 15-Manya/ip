package agentsmith;

/**
 * Represents a simple task without any date or time attached.
 */
public class ToDo extends Task {

    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String saveString() {
        return "T " + super.saveString();
    }
}
