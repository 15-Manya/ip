public class Deadline extends Task {
    protected String deadline;
    protected boolean isDone;

    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    public String getDeadline() {
        return this.deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadline + ")";
    }

    @Override
    public String saveString() {
        return "D " + super.saveString() + " | " + deadline;
    }
}
