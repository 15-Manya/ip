package agentsmith;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 * A simple dialog box consisting of a label to represent
 * either the user's input or AgentSmith's response.
 */
public class DialogBox extends HBox {

    private final Label label;

    private DialogBox(String text) {
        this.label = new Label(text);
        this.label.setWrapText(true);
        this.label.setFont(Font.font("Monospaced", 12));
        this.getChildren().add(this.label);
        this.setPadding(new Insets(5, 10, 5, 10));
        this.setSpacing(10);
    }

    /**
     * Creates a dialog box representing the user's input.
     *
     * @param text user's input text.
     * @return dialog box node.
     */
    public static DialogBox getUserDialog(String text) {
        return new DialogBox(text);
    }

    /**
     * Creates a dialog box representing AgentSmith's response.
     *
     * @param text AgentSmith's response text.
     * @return dialog box node.
     */
    public static DialogBox getAgentDialog(String text) {
        return new DialogBox(text);
    }
}

