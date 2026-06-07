import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the login FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("user_dashboard_seeker.fxml"));
        AnchorPane loginRoot = loader.load();

        // Create the Scene with the loaded root and set it on the primary stage
        Scene loginScene = new Scene(loginRoot);
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login Page");
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
