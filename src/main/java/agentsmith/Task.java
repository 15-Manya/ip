package agentsmith;

/**
 * Represents a single task with a description and completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    public String saveString() {
        return "| " + (isDone() ? 1 : 0) + " | " + getDescription();
    }
}
