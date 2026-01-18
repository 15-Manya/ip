import java.util.Scanner;

public class AgentSmith {

    public static String name = "Agent Smith";
    private Task tasklist[] = new Task[100];
    private int tasklist_size = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AgentSmith agent = new AgentSmith();

        String logo = "        _                    _     ____            _ _   _       \n" +
                "       / \\   __ _  ___ _ __ | |_  / ___| _ __ ___ (_) |_| |__   \n" +
                "      / _ \\ / _` |/ _ \\ '_ \\| __| \\___ \\| '_ ` _ \\| | __| '_ \\   \n" +
                "     / ___ \\ (_| |  __/ | | | |_   ___) | | | | | | | |_| | | |  \n" +
                "    /_/   \\_\\__, |\\___|_| |_|\\__| |____/|_| |_| |_|_|\\__|_| |_|  \n" +
                "             |___/                                                \n";

        System.out.println("\tHello from\n" + logo);

        print_intro();

        String input = sc.nextLine();
        input = input.trim();

        while (!input.equals("bye")) { /* loops until the user inputs 'bye' */
            if (input.equals("list")) {
                agent.display_list();
            } else if (input.startsWith("mark")) {
                int index = Integer.parseInt(input.substring(5).trim());
                agent.mark_task(index);
            } else if (input.startsWith("unmark")) {
                int index = Integer.parseInt(input.substring(7).trim());
                agent.unmark_task(index);
            } else {
                agent.add_task(input);
            }
            input = sc.nextLine();
            input = input.trim();
        }

        print_bye();
    }

    public static void print_line() {
        System.out.println("     ________________________________________________________");
    }

    public static void print_intro() {
        print_line();
        System.out.println("\tHello! I'm " + name);
        System.out.println("\tWhat can I do for you?");
        print_line();
        System.out.println();
    }

    public void display_list() {
        print_line();
        if (tasklist_size == 0) {
            System.out.println("\tTask list is empty");
        } else {
            System.out.println("\tHere are the tasks in your list:");
            for (int i = 0; i < tasklist_size; i++) {
                Task task = tasklist[i];
                System.out.println("\t" + (i + 1) + ". " + "[" + task.getStatusIcon() + "] " + task.getDescription());
            }
        }
        print_line();
        System.out.println();
    }

    public void add_task(String task) {
        Task new_task = new Task(task);
        tasklist[tasklist_size] = new_task;
        tasklist_size++;
        print_line();
        System.out.println("\tAdded: " + new_task.getDescription());
        print_line();
        System.out.println();
    }

    public void mark_task(int index) {
        if (index < 1 || index > tasklist_size) { // check if the task number is valid
            print_line();
            System.out.println("\tInvalid task number");
            print_line();
            System.out.println();
            return;
        }

        tasklist[index - 1].setIsDone(true);
        print_line();
        System.out.println("\tNice! I've marked this task as done:");
        System.out.println(
                "\t" + index + ". " + "[" + tasklist[index - 1].getStatusIcon() + "] "
                        + tasklist[index - 1].getDescription());
        print_line();
        System.out.println();
    }

    public void unmark_task(int index) {
        if (index < 1 || index > tasklist_size) { // check if the task number is valid
            print_line();
            System.out.println("\tInvalid task number");
            print_line();
            System.out.println();
            return;
        }

        tasklist[index - 1].setIsDone(false);
        print_line();
        System.out.println("\tOK, I've marked this task as not done yet:");
        System.out.println(
                "\t" + index + ". " + "[" + tasklist[index - 1].getStatusIcon() + "] "
                        + tasklist[index - 1].getDescription());
        print_line();
        System.out.println();
    }

    public static void print_bye() {
        print_line();
        System.out.println("\tBye. Hope to see you again soon!");
        print_line();
    }
}
