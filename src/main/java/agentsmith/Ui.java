package agentsmith;

/**
    * Handles all user interface output for the AgentSmith chatbot.
    */
public class Ui {

    /**
     * Prints the ASCII art logo and greeting header.
     */
    public void print_logo() {
        String logo = "        _                    _     ____            _ _   _       \n" +
                "       / \\   __ _  ___ _ __ | |_  / ___| _ __ ___ (_) |_| |__   \n" +
                "      / _ \\ / _` |/ _ \\ '_ \\| __| \\___ \\| '_ ` _ \\| | __| '_ \\   \n" +
                "     / ___ \\ (_| |  __/ | | | |_   ___) | | | | | | | |_| | | |  \n" +
                "    /_/   \\_\\__, |\\___|_| |_|\\__| |____/|_| |_| |_|_|\\__|_| |_|  \n" +
                "             |___/                                                \n";

        System.out.println("\tHello from\n" + logo);
    }

    /**
     * Prints a horizontal separator line.
     */
    public void print_line() {
        System.out.println("     ________________________________________________________");
    }

    /**
     * Prints the initial greeting message that introduces the bot.
     *
     * @param name the name of the chatbot
     */
    public void print_intro(String name) {
        print_line();
        System.out.println("\tGreetings. You are speaking to…" + name);
        System.out.println("\tWhat can this system do…for you?");
        print_line();
        System.out.println();
    }

    /**
     * Prints the farewell message when the program is about to exit.
     */
    public void print_bye() {
        print_line();
        System.out.println("\tUntil next time. The Matrix… never sleeps.");
        print_line();
    }
}
