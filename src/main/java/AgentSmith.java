import java.util.Scanner;

public class AgentSmith {

    public static String name = "Agent Smith";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

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
            System.out.println("     ________________________________________________________");
            System.out.println("\t" + input);
            System.out.println("     ________________________________________________________");
            System.out.println();
            input = sc.nextLine();
        }

        print_bye();
    }

    public static void print_intro() {
        System.out.println("     ________________________________________________________");
        System.out.println("\tHello! I'm " + name);
        System.out.println("\tWhat can I do for you?");
        System.out.println("     ________________________________________________________");
        System.out.println();
    }

    public static void print_bye() {
        System.out.println("     ________________________________________________________");
        System.out.println("\tBye. Hope to see you again soon!");
        System.out.println("     ________________________________________________________");
    }
}
