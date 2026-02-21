package agentsmith;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;

/**
 * A dialog box consisting of a circular avatar and a styled message bubble.
 */
public class DialogBox extends HBox {

    private static final double AVATAR_SIZE = 40.0;

    private final Label label;
    private final ImageView avatar;

    private DialogBox(String text, Image image, boolean isUser) {
        // Create avatar - force square dimensions
        this.avatar = new ImageView(image);
        this.avatar.setFitWidth(AVATAR_SIZE);
        this.avatar.setFitHeight(AVATAR_SIZE);
        this.avatar.setPreserveRatio(false);

        // Make avatar circular
        Circle clip = new Circle(AVATAR_SIZE / 2, AVATAR_SIZE / 2, AVATAR_SIZE / 2);
        this.avatar.setClip(clip);

        // Create message label
        this.label = new Label(text);
        this.label.setWrapText(true);
        this.label.setMaxWidth(280);
        this.label.setPadding(new Insets(10));

        // Style based on user or agent
        if (isUser) {
            this.label.setStyle(
                    "-fx-background-color: #007AFF; " +
                    "-fx-text-fill: white; " +
                    "-fx-background-radius: 15; " +
                    "-fx-font-size: 13px;");
            this.setAlignment(Pos.CENTER_RIGHT);
            this.getChildren().addAll(this.label, this.avatar);
        } else {
            this.label.setStyle(
                    "-fx-background-color: #E5E5EA; " +
                    "-fx-text-fill: black; " +
                    "-fx-background-radius: 15; " +
                    "-fx-font-size: 13px;");
            this.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().addAll(this.avatar, this.label);
        }

        this.setPadding(new Insets(5, 10, 5, 10));
        this.setSpacing(10);

        // Make HBox fill the width
        HBox.setHgrow(this, Priority.ALWAYS);
        this.setMinWidth(Region.USE_PREF_SIZE);
        this.setMaxWidth(Double.MAX_VALUE);
        this.setFillHeight(true);
    }

    /**
     * Creates a dialog box representing the user's input (aligned right).
     *
     * @param text  user's input text.
     * @param image user's avatar image.
     * @return dialog box node.
     */
    public static DialogBox getUserDialog(String text, Image image) {
        return new DialogBox(text, image, true);
    }

    /**
     * Creates a dialog box representing AgentSmith's response (aligned left).
     *
     * @param text  AgentSmith's response text.
     * @param image agent's avatar image.
     * @return dialog box node.
     */
    public static DialogBox getAgentDialog(String text, Image image) {
        return new DialogBox(text, image, false);
    }

    /**
     * Creates a centered logo box for displaying the ASCII art header.
     *
     * @param logoText the ASCII art logo text.
     * @return dialog box node with centered logo.
     */
    public static HBox getLogoBox(String logoText) {
        Label logoLabel = new Label(logoText);
        logoLabel.setStyle(
                "-fx-font-family: 'Monospaced'; " +
                "-fx-font-size: 10px; " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-background-color: rgba(0,0,0,0.6); " +
                "-fx-background-radius: 10; " +
                "-fx-padding: 10;");

        HBox box = new HBox(logoLabel);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(15, 10, 10, 10));
        box.setMaxWidth(Double.MAX_VALUE);
        return box;
    }
}
