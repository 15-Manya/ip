import java.util.Scanner;

public class AgentSmith {

    public static String name = "Agent Smith";
    private String tasklist[] = new String[100];
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

        while (!input.equals("bye")) { /* loops until the user inputs 'bye' */
            if (input.equals("list")) {
                agent.display_list();
            } else {
                agent.add_task(input);
            }
            input = sc.nextLine();
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
            for (int i = 0; i < tasklist_size; i++) {
                System.out.println("\t" + (i + 1) + ". " + tasklist[i]);
            }
        }
        print_line();
        System.out.println();
    }

    public void add_task(String task) {
        tasklist[tasklist_size] = task;
        tasklist_size++;
        print_line();
        System.out.println("\tAdded: " + task);
        print_line();
        System.out.println();
    }

    public static void print_bye() {
        print_line();
        System.out.println("\tBye. Hope to see you again soon!");
        print_line();
    }
}
