package agentsmith;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {

    @Test
    public void getDeadlineString_formatsFullMonthAndTimeCorrectly() {
        LocalDateTime dt = LocalDateTime.of(2024, 1, 30, 16, 0);
        Deadline deadline = new Deadline("return book", dt);

        String formatted = deadline.getDeadlineString();

        assertEquals(
                "30 January 2024, 4:00PM",
                formatted,
                "Deadline string should use full month name and 12-hour time with AM/PM");
    }

    @Test
    public void saveString_includesTypeStatusDescriptionAndDate() {
        LocalDateTime dt = LocalDateTime.of(2024, 1, 30, 16, 0);
        Deadline deadline = new Deadline("return book", dt);

        String saved = deadline.saveString();

        assertEquals(
                "D | 0 | return book | 30 January 2024, 4:00PM",
                saved,
                "saveString should match the persisted format for Deadline tasks");
    }
}
