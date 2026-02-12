package agentsmith;

/**
 * Represents a single task with a description and completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a new task with the given description.
     *
     * @param description the task description.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns a visual icon representing the task's completion status.
     *
     * @return "X" if done, " " (space) otherwise.
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns the task description.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns whether the task is marked as done.
     *
     * @return true if done, false otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Sets the completion status of the task.
     *
     * @param isDone true to mark as done, false otherwise.
     */
    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Returns a string representation for saving to file.
     *
     * @return the save format string.
     */
    public String saveString() {
        return "| " + (isDone() ? 1 : 0) + " | " + getDescription();
    }
}
