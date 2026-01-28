package agentsmith;

public class Event extends Task {
    protected String from, to;
    protected boolean isDone;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    @Override
    public String saveString() {
        return "E " + super.saveString() + " | " + from + "-" + to;
    }
}
