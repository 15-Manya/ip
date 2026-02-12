package agentsmith;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {
    private LocalDateTime deadline;

    public Deadline(String description, LocalDateTime deadline) {
        super(description);
        this.deadline = deadline;
    }

    public LocalDateTime getDeadline() {
        return this.deadline;
    }

    public String getDeadlineString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy, h:mma");
        return formatter.format(this.deadline);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + getDeadlineString() + ")";
    }

    @Override
    public String saveString() {
        return "D " + super.saveString() + " | " + getDeadlineString();
    }
}
