package agentsmith;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskListTest {

    @Test
    public void addTask_increasesSizeAndStoresTask() throws AgentSmithException {
        TaskList list = new TaskList();
        Task task = new ToDo("read book");

        list.addTask(task);

        assertEquals(1, list.size(), "Size should be 1 after adding one task");
        assertSame(task, list.get(0), "The added task should be retrievable at index 0");
    }

    @Test
    public void addTask_overCapacity_throwsAgentSmithException() throws AgentSmithException {
        TaskList list = new TaskList();
        // Fill the list up to capacity
        for (int i = 0; i < 100; i++) {
            list.addTask(new ToDo("task " + i));
        }

        AgentSmithException ex = assertThrows(
                AgentSmithException.class,
                () -> list.addTask(new ToDo("overflow task")),
                "Adding more than 100 tasks should throw AgentSmithException"
        );

        assertTrue(
                ex.getMessage().toLowerCase().contains("capacity"),
                "Exception message should mention capacity"
        );
    }
}

