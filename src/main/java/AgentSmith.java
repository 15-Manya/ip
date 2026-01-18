public class AgentSmith {

    public static String name = "Agent Smith";

    public static void main(String[] args) {
        String logo = "        _                    _     ____            _ _   _       \n" +
                "       / \\   __ _  ___ _ __ | |_  / ___| _ __ ___ (_) |_| |__   \n" +
                "      / _ \\ / _` |/ _ \\ '_ \\| __| \\___ \\| '_ ` _ \\| | __| '_ \\   \n" +
                "     / ___ \\ (_| |  __/ | | | |_   ___) | | | | | | | |_| | | |  \n" +
                "    /_/   \\_\\__, |\\___|_| |_|\\__| |____/|_| |_| |_|_|\\__|_| |_|  \n" +
                "             |___/                                                \n";

        System.out.println("Hello from\n" + logo);

        print_intro();
        print_bye();
    }

    public static void print_intro() {
        System.out.println("________________________________________________________");
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do for you?");
        System.out.println("________________________________________________________");
    }

    public static void print_bye() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("________________________________________________________");
    }
}
