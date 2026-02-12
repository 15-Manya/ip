package agentsmith;

/**
 * Represents a task that takes a fixed amount of time but has no fixed start/end time.
 * For example, "reading the sales report (needs 2 hours)".
 */
public class FixedDurationTask extends Task {
    private String duration;

    /**
     * Creates a new fixed duration task with the given description and duration.
     *
     * @param description the task description.
     * @param duration    the time required to complete the task (e.g., "2 hours").
     */
    public FixedDurationTask(String description, String duration) {
        super(description);
        this.duration = duration;
    }

    /**
     * Returns the duration of this task.
     *
     * @return the duration string.
     */
    public String getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "[F]" + super.toString() + " (needs: " + duration + ")";
    }

    @Override
    public String saveString() {
        return "F " + super.saveString() + " | " + duration;
    }
}
