package agentsmith;

import java.util.ArrayList;

/**
 * Used to manage the list of tasks and provide operations on it.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list backed by the given list.
     *
     * @param tasks underlying list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns true if there are no tasks in the list.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns the task at the given 0-based index.
     *
     * @param index 0-based index of the task.
     * @return task at the given index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the underlying list of tasks.
     *
     * @return list of tasks.
     */
    public ArrayList<Task> getAll() {
        return tasks;
    }

    /**
     * Adds a task to the list, enforcing a maximum capacity of 100 tasks.
     *
     * @param task task to add.
     * @throws AgentSmithException if the list is already at capacity.
     */
    public void addTask(Task task) throws AgentSmithException {
        if (tasks.size() >= 100) {
            throw new AgentSmithException(
                    "The task list is at capacity.The system rejects further anomalies… prune or perish.");
        }
        tasks.add(task);
    }

    /**
     * Marks the task at the given 1-based index as done.
     *
     * @param index 1-based index of task to mark.
     * @throws AgentSmithException if the index is out of range.
     */
    public void markTask(int index) throws AgentSmithException {
        if (index < 1 || index > tasks.size()) {
            throw new AgentSmithException("Invalid task number");
        }
        tasks.get(index - 1).setIsDone(true);
    }

    /**
     * Marks the task at the given 1-based index as not done.
     *
     * @param index 1-based index of task to unmark.
     * @throws AgentSmithException if the index is out of range.
     */
    public void unmarkTask(int index) throws AgentSmithException {
        if (index < 1 || index > tasks.size()) {
            throw new AgentSmithException("Invalid task number");
        }
        tasks.get(index - 1).setIsDone(false);
    }

    /**
     * Deletes the task at the given 1-based index from the list.
     *
     * @param index 1-based index of task to delete.
     * @throws AgentSmithException if the index is out of range.
     */
    public void deleteTask(int index) throws AgentSmithException {
        if (index < 1 || index > tasks.size()) {
            throw new AgentSmithException("Invalid task number");
        }
        tasks.remove(index - 1);
    }

    public void find(String keyword) throws AgentSmithException {
        if (keyword.isEmpty()) {
            throw new AgentSmithException("The keyword is empty.");
        }
        for (Task task : tasks) {
            if (task.toString().contains(keyword)) {
                System.out.println(task.toString());
            }
        }
    }
}
