public class Ui {

    public void print_logo() {
        String logo = "        _                    _     ____            _ _   _       \n" +
                "       / \\   __ _  ___ _ __ | |_  / ___| _ __ ___ (_) |_| |__   \n" +
                "      / _ \\ / _` |/ _ \\ '_ \\| __| \\___ \\| '_ ` _ \\| | __| '_ \\   \n" +
                "     / ___ \\ (_| |  __/ | | | |_   ___) | | | | | | | |_| | | |  \n" +
                "    /_/   \\_\\__, |\\___|_| |_|\\__| |____/|_| |_| |_|_|\\__|_| |_|  \n" +
                "             |___/                                                \n";

        System.out.println("\tHello from\n" + logo);
    }

    public void print_line() {
        System.out.println("     ________________________________________________________");
    }

    public void print_intro(String name) {
        print_line();
        System.out.println("\tGreetings. You are speaking to…" + name);
        System.out.println("\tWhat can this system do…for you?");
        print_line();
        System.out.println();
    }

    public void print_bye() {
        print_line();
        System.out.println("\tUntil next time. The Matrix… never sleeps.");
        print_line();
    }
}
