import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PostNewServiceRequestproviderController {

    public String username;
    public String expertise;
    @FXML
    public void setUsername(String username){
        this.username = username;
    }

    @FXML
    private TextField jobTypeField;

    @FXML
    private TextField priceField;

    @FXML
    private Button backButton;

    @FXML
    private Button submitButton;

    // Handle the "Submit" action
    @FXML
    private void handleSubmit() {
        // Get data from the fields
        String jobType = jobTypeField.getText();
        double price = Double.parseDouble(priceField.getText()); 

        // Check if any field is empty
        if (jobType.isEmpty() ) {
            showAlert("Error", "Both fields must be filled.");
            return;
        }
        for (ServiceProvider provider : ServiceManagementSystem.getProviders()){
            if (provider.getName() == username)
            {
                expertise = provider.getExpertise();
            }
        }

       // Service service = new Service(name, expertise, description, price, "pending");
       // services.add(service);

        Service service = new Service(username, expertise, jobType, price, "pending");
        ServiceManagementSystem.getServices().add(service);
        ServiceManagementSystem.saveDataToDatabase();
        // Logic to handle the submission (e.g., save data to the database or backend)
        showAlert("Success", "Your service request has been posted successfully!");
     
        // Optionally, close the window after submission
        closeWindow();
    }

    // Handle the "Back" action
    @FXML
    private void handleBack() {
        try {
            this.closeWindow();
            // Load the previous screen (e.g., the dashboard)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserDashboardProvider.fxml"));
            Parent root = loader.load();

            // Set up the stage for the previous screen
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unable to navigate back to the dashboard.");
        }
    }

    // Helper method to show alert dialogs
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper method to close the window
    private void closeWindow() {
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }
}
