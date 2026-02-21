package agentsmith;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Controller for the main JavaFX window of AgentSmith.
 */
public class MainWindow {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox dialogContainer;

    @FXML
    private TextField userInput;

    @FXML
    private Button sendButton;

    private AgentSmith agentSmith;

    private Image userImage;
    private Image agentImage;

    /**
     * Initializes the main window.
     * This method is called automatically after the FXML is loaded.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        sendButton.setDefaultButton(true);
        userInput.setOnAction(event -> handleUserInput());

        // Load default images (will be replaced when user provides custom ones)
        userImage = new Image(getClass().getResourceAsStream("/images/user.png"));
        agentImage = new Image(getClass().getResourceAsStream("/images/agent.png"));
    }

    /**
     * Injects the {@link AgentSmith} instance to be used by this window.
     *
     * @param agentSmith chatbot instance.
     */
    public void setAgentSmith(AgentSmith agentSmith) {
        this.agentSmith = agentSmith;

        // Show logo at top (centered)
        Ui ui = new Ui();
        HBox logoBox = DialogBox.getLogoBox(ui.getLogoText());
        logoBox.prefWidthProperty().bind(dialogContainer.widthProperty().subtract(20));
        dialogContainer.getChildren().add(logoBox);

        // Show welcome message
        String welcome = "Greetings. You are speaking to… " + AgentSmith.NAME + "\n"
                + "What can this system do… for you?\n\n"
                + "Type 'help' for available commands.";
        addDialog(DialogBox.getAgentDialog(welcome, agentImage));

        // Show existing tasks if any
        String rawLines = agentSmith.getRawTaskLines();
        if (!rawLines.isBlank()) {
            String taskMessage = "Your existing tasks:\n" + rawLines;
            addDialog(DialogBox.getAgentDialog(taskMessage, agentImage));
        }
    }

    /**
     * Handles user input from the text field and displays
     * the corresponding response from AgentSmith.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input == null || input.isBlank()) {
            return;
        }

        String response = agentSmith.getResponse(input);
        // Remove the divider lines for cleaner GUI display
        String cleanResponse = response
                .replaceAll("\\s*_{5,}\\s*", "")
                .replaceAll("\\t", "")
                .trim();

        addDialog(DialogBox.getUserDialog(input, userImage));
        if (!cleanResponse.isEmpty()) {
            addDialog(DialogBox.getAgentDialog(cleanResponse, agentImage));
        }

        userInput.clear();
    }

    /**
     * Adds a dialog node to the container with proper width binding.
     */
    private void addDialog(HBox dialog) {
        dialog.prefWidthProperty().bind(dialogContainer.widthProperty().subtract(20));
        dialogContainer.getChildren().add(dialog);
    }
}
