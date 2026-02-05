package agentsmith;

/**
 * Handles all user interface output for the AgentSmith chatbot.
 */
public class Ui {

    private static final String LINE = "     ________________________________________________________";

    /**
     * Returns the ASCII art logo and greeting header text.
     *
     * @return logo text.
     */
    public String getLogoText() {
        String logo = "        _                    _     ____            _ _   _       \n"
                + "       / \\   __ _  ___ _ __ | |_  / ___| _ __ ___ (_) |_| |__   \n"
                + "      / _ \\ / _` |/ _ \\ '_ \\| __| \\___ \\| '_ ` _ \\| | __| '_ \\   \n"
                + "     / ___ \\ (_| |  __/ | | | |_   ___) | | | | | | | |_| | | |  \n"
                + "    /_/   \\_\\__, |\\___|_| |_|\\__| |____/|_| |_| |_|_|\\__|_| |_|  \n"
                + "             |___/                                                \n";
        return "\tHello from\n" + logo;
    }

    /**
     * Returns the horizontal separator line text.
     *
     * @return separator line text.
     */
    public String getLineText() {
        return LINE;
    }

    /**
     * Returns the initial greeting message that introduces the bot.
     *
     * @param name the name of the chatbot.
     * @return greeting text.
     */
    public String getIntroText(String name) {
        return LINE + System.lineSeparator()
                + "\tGreetings. You are speaking to…" + name + System.lineSeparator()
                + "\tWhat can this system do…for you?" + System.lineSeparator()
                + LINE;
    }

    /**
     * Prints the ASCII art logo and greeting header.
     */
    public void printLogo() {
        System.out.println(getLogoText());
    }

    /**
     * Prints a horizontal separator line.
     */
    public void printLine() {
        System.out.println(LINE);
    }

    /**
     * Prints the initial greeting message that introduces the bot.
     *
     * @param name the name of the chatbot.
     */
    public void printIntro(String name) {
        System.out.println(getIntroText(name));
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

