package Client;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ClientMain extends Application {

    private Stage primaryStage;
    public Scene clientView;

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Create the FXMLLoader
            FXMLLoader loader = new FXMLLoader();
            // Path to the FXML File
            loader.setLocation(ClientMain.class.getResource("clientfxml.fxml"));
            AnchorPane anchorPane = (AnchorPane) loader.load();
            // Create the Scene
            Scene scene = new Scene(anchorPane);
            // Set the Scene to the Stage
            primaryStage.setScene(scene);
            // Set the Title to the Stage
            primaryStage.setTitle("ClientView");
            // Display the Stage
            primaryStage.show();


        } catch (IOException e) {
            System.out.println("Failed to open ClientView : /n" + e);
        }
    }
}
