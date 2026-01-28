package agentsmith;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDateTime deadline;
    protected boolean isDone;

    public Deadline(String description, LocalDateTime deadline) {
        super(description);
        this.deadline = deadline;
    }

    public LocalDateTime getDeadline() {
        return this.deadline;
    }

    public String getDeadlineString() {
        DateTimeFormatter deadline = DateTimeFormatter.ofPattern("d MMMM yyyy, h:mma");
        return deadline.format(this.deadline);
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
