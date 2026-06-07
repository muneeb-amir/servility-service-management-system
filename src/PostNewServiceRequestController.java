import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class PostNewServiceRequestController {

    public String username;
    @FXML
    public void setUsername(String username){
        this.username = username;
        initialize1();
    }

    @FXML
    private ComboBox<String> jobTypeComboBox;  // Job Type dropdown

    @FXML
    private TextField priceField;

    @FXML
    private TextField timeField; // Renamed field still uses the same fx:id

    @FXML
    private Button submitButton;

    @FXML
    private Button backButton;

    // Initialize method to populate the ComboBox with job types
    @FXML
    public void initialize1() {
        // Populate jobTypeComboBox with options
        jobTypeComboBox.getItems().addAll("Driver", "Chef", "Househelper", "Watchman");
    }

    // Handle the "Submit" action
    @FXML
    private void handleSubmit() {
        String jobType = jobTypeComboBox.getValue();  // Get selected job type
        double price = Double.parseDouble(priceField.getText()); 
        String time = timeField.getText(); // Field logic remains unchanged

        if (jobType == null || time.isEmpty()) {
            showAlert("Error", "All fields must be filled.");
            return;
        }

        try {
            price = Double.parseDouble(priceField.getText()); // Parse price as a double
        } catch (NumberFormatException e) {
            showAlert("Error", "Price must be a valid number.");
            return;
        }


        ServiceRequest request = new ServiceRequest(username, jobType, time, price, "pending");
        ServiceManagementSystem.getRequests().add(request);

        showAlert("Success", "Your service request has been posted successfully!");
        
        // Navigate back to user_dashboard_seeker.fxml instead of closing the window
        navigateToDashboard();
        ServiceManagementSystem.saveDataToDatabase();
   
    }

    // Handle the "Back" action
    @FXML
    private void handleBack() {
        navigateToDashboard();
        ServiceManagementSystem.saveDataToDatabase();
    }

    // Helper method to show alert dialogs
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to navigate back to the user dashboard
    private void navigateToDashboard() {
        try {
            Stage stage = (Stage) submitButton.getScene().getWindow();

            // Load the user_dashboard_seeker.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("user_dashboard_seeker.fxml"));
            Parent root = loader.load();

            // Create the new scene and set it on the stage
            Scene scene = new Scene(root, 800, 600); // Same size as the dashboard
            stage.setScene(scene);
            stage.setTitle("Dashboard");
            UserDashboardSeekerController controller = loader.getController();
            controller.setUsername(username);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unable to navigate back to the dashboard.");
        }
    }
    

}