import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ManageProfileController {

    public String username;
    @FXML
    public void setUsername(String username){
        this.username = username;
        initialize1();
    }

    @FXML
    private TextField nameField; // TextField for the provider's name

    @FXML
    private TextField emailField; // TextField for the provider's email

    @FXML
    private PasswordField passwordField; // PasswordField for the provider's password

    @FXML
    private Button saveButton; // Save button

    @FXML
    private Button backButton; // Back button

    
    private String providerName = username;
    private String providerEmail = username+"@example.com";
    private String providerPassword = "password";

    @FXML
    public void initialize1() {
        
        nameField.setText(username);
        emailField.setText(username+"@example.com");
        passwordField.setText("password");
    }

    // Handle the "Save Changes" action
    @FXML
    private void handleSaveChanges() {
        // Retrieve updated data from the fields
        providerName = nameField.getText();
        providerEmail = emailField.getText();
        providerPassword = passwordField.getText();
        ServiceManagementSystem.manageprofileseeker(username,providerName,providerEmail,providerPassword);

        // In a real application, save the updated data to the database or backend
        showAlert("Profile Updated", "Your profile details have been saved.");
        ServiceManagementSystem.Updated(username,providerName,providerEmail,providerPassword);

    }

    // Handle the "Back" action
    @FXML
    private void handleBack() {
        // Logic to close the current window and return to the previous screen
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        ServiceManagementSystem.saveDataToDatabase();
   
    }

    // Helper method to show alert dialogs
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
