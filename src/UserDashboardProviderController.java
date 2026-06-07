import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UserDashboardProviderController {

    public String username;
    public double earnings;
    @FXML
    public void setUsername(String username){
        this.username = username;
    }


    @FXML
    private Button viewRequestsButton;

    @FXML
    private Button postServiceRequestButton;

    @FXML
    private Button viewEarningsButton;

    @FXML
    private Button logoutButton;

    // Handle the "View and Accept Requests" action
    @FXML
    private void handleViewAndAcceptRequests() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewOpenRequestsProvider.fxml"));
            AnchorPane root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("View and Accept Requests");
            stage.setScene(new Scene(root));
            stage.show();

            ViewOpenRequestsProviderController controller = loader.getController();
            controller.setUsername(username);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the View and Accept Requests view.");
        }
    }

    // Handle the "Post New Service Request" action
    @FXML
    private void handlePostNewServiceRequest() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PostNewServiceRequestProvider.fxml"));
            AnchorPane root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Post New Service Request");
            stage.setScene(new Scene(root));
            stage.show();
            PostNewServiceRequestproviderController controller = loader.getController();
            controller.setUsername(username);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the Post New Service Request view.");
        }
    }

    @FXML
    private void handleViewEarnings() {{
            for (ServiceProvider provider : ServiceManagementSystem.getProviders()) {
                    if (provider.name == username){
                        earnings = provider.getBalance();
                    }
            }
            showAlert("Check Balance", "Your current balance is : $"+ earnings );
        }
    }

    // Handle the "Logout" action
    @FXML
    private void handleLogout() {
        try {
            ServiceManagementSystem.saveDataToDatabase();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            AnchorPane root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current user dashboard window
            Stage currentStage = (Stage) logoutButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the Login view.");
        }
    }

    // Handle hover events for buttons (general styling for all buttons except logout)
    @FXML
    private void handleButtonHover(javafx.scene.input.MouseEvent event) {
        Button button = (Button) event.getSource();
        if (button != logoutButton) {  // Avoid changing the color of logoutButton
            button.setStyle("-fx-background-color: #005cbf; -fx-text-fill: white; -fx-padding: 10; -fx-background-radius: 5;");
        }
    }

    @FXML
    private void handleButtonHoverExit(javafx.scene.input.MouseEvent event) {
        Button button = (Button) event.getSource();
        if (button != logoutButton) {  // Avoid resetting the color of logoutButton
            button.setStyle("-fx-background-color: #0078d7; -fx-text-fill: white; -fx-padding: 10; -fx-background-radius: 5;");
        }
    }

    // Helper method to show alert dialogs
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
