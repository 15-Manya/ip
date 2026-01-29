package agentsmith;

/**
 * Handles all user interface output for the AgentSmith chatbot.
 */
public class Ui {

    /**
     * Prints the ASCII art logo and greeting header.
     */
    public void printLogo() {
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
    public void printLine() {
        System.out.println("     ________________________________________________________");
    }

    /**
     * Prints the initial greeting message that introduces the bot.
     *
     * @param name the name of the chatbot
     */
    public void printIntro(String name) {
        printLine();
        System.out.println("\tGreetings. You are speaking to…" + name);
        System.out.println("\tWhat can this system do…for you?");
        printLine();
        System.out.println();
    }

    /**
     * Prints the farewell message when the program is about to exit.
     */
    public void printBye() {
        printLine();
        System.out.println("\tUntil next time. The Matrix… never sleeps.");
        printLine();
    }
}
