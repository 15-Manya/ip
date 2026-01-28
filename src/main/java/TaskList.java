import java.util.ArrayList;

//used to manage the task list
public class TaskList {
    private ArrayList<Task> tasklist;

    public TaskList() {
        this.tasklist = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasklist) {
        this.tasklist = tasklist;
    }

    public int size() {
        return tasklist.size();
    }

    public boolean isEmpty() {
        return tasklist.isEmpty();
    }

    public Task get(int index) {
        return tasklist.get(index);
    }

    public ArrayList<Task> getAll() {
        return tasklist;
    }

    public void add_task(Task task) throws AgentSmithException {
        if (tasklist.size() >= 100) {
            throw new AgentSmithException(
                    "The task list is at capacity.The system rejects further anomalies… prune or perish.");
        }
        tasklist.add(task);
    }

    public void mark_task(int index) throws AgentSmithException {
        if (index < 1 || index > tasklist.size()) {
            throw new AgentSmithException("Invalid task number");
        }
        tasklist.get(index - 1).setIsDone(true);
    }

    public void unmark_task(int index) throws AgentSmithException {
        if (index < 1 || index > tasklist.size()) {
            throw new AgentSmithException("Invalid task number");
        }
        tasklist.get(index - 1).setIsDone(false);
    }

    public void delete_task(int index) throws AgentSmithException {
        if (index < 1 || index > tasklist.size()) {
            throw new AgentSmithException("Invalid task number");
        }
        tasklist.remove(index - 1);
    }
}
