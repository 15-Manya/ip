package agentsmith;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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

    /**
     * Initializes the main window.
     * This method is called automatically after the FXML is loaded.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        sendButton.setDefaultButton(true);
        userInput.setOnAction(event -> handleUserInput());
    }

    /**
     * Injects the {@link AgentSmith} instance to be used by this window.
     *
     * @param agentSmith chatbot instance.
     */
    public void setAgentSmith(AgentSmith agentSmith) {
        this.agentSmith = agentSmith;

        Ui ui = new Ui();
        String intro = ui.getLogoText() + System.lineSeparator() + ui.getIntroText(AgentSmith.name);
        dialogContainer.getChildren().add(DialogBox.getAgentDialog(intro));

        // Show the raw saved task lines exactly as in data/tasks.txt
        String rawLines = agentSmith.getRawTaskLines();
        if (!rawLines.isBlank()) {
            dialogContainer.getChildren().add(DialogBox.getAgentDialog(rawLines));
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

        dialogContainer.getChildren().add(DialogBox.getUserDialog(input));
        dialogContainer.getChildren().add(DialogBox.getAgentDialog(response));

        userInput.clear();
    }
}

