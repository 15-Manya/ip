package agentsmith;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.File;

/**
 * The main JavaFX entry point for the AgentSmith application.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
        AnchorPane root = fxmlLoader.load();

        MainWindow mainWindow = fxmlLoader.getController();
        String absolutePath = new File("data/tasks.txt").getAbsolutePath();
        AgentSmith agentSmith = new AgentSmith(absolutePath, false);
        mainWindow.setAgentSmith(agentSmith);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Agent Smith");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

