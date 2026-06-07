import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SignupController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private VBox seekerFields;

    @FXML
    private TextField bankBalanceField;

    @FXML
    private VBox providerFields;

    @FXML
    private ComboBox<String> jobTypeComboBox;

    @FXML
    private TextField experienceField;

    @FXML
    private TextField idNumberField;

    @FXML
    private Hyperlink goToLogin;

    @FXML
    public void initialize() {
        // Set up ComboBox options
        roleComboBox.getItems().addAll("Service Seeker", "Service Provider");
      
        // Populate jobTypeComboBox options
        jobTypeComboBox.getItems().addAll("Driver", "Chef", "Househelper", "Watchman");

        // Listener to handle role selection
        roleComboBox.setOnAction(event -> handleRoleSelection());
    }

    private void handleRoleSelection() {
        String selectedRole = roleComboBox.getValue();

        if (selectedRole != null && selectedRole.equals("Service Provider")) {
            seekerFields.setVisible(false);
            seekerFields.setManaged(false);
            providerFields.setVisible(true);
            providerFields.setManaged(true);
        } else if (selectedRole != null && selectedRole.equals("Service Seeker")) {
            providerFields.setVisible(false);
            providerFields.setManaged(false);
            seekerFields.setVisible(true);
            seekerFields.setManaged(true);
        } else {
            seekerFields.setVisible(false);
            seekerFields.setManaged(false);
            providerFields.setVisible(false);
            providerFields.setManaged(false);
        }
    }

    @FXML
    private void handleSignup() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String role = roleComboBox.getValue();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || role == null) {
            showAlert("Please fill all fields.");
            return;
        }

        if (role.equals("Service Seeker")) {
            double bankBalance = Double.parseDouble(bankBalanceField.getText());
            if (bankBalance <= 0) {
                showAlert("Bank balance must be a positive number.");
                return;
            }
            ServiceManagementSystem.getSeekers().add(new ServiceSeeker(name, email, password, bankBalance));
            // Handle Service Seeker-specific logic (e.g., save data)
        }

        if (role.equals("Service Provider")) {
            String jobType = jobTypeComboBox.getValue();
            String experience = experienceField.getText();
            String idNumber = idNumberField.getText();

            if (jobType == null || experience.isEmpty() || idNumber.isEmpty()) {
                showAlert("Please fill all provider fields.");
                return;
            }
            ServiceManagementSystem.getProviders().add(new ServiceProvider(name, email, password, jobType));
            // Handle Service Provider-specific logic (e.g., save data)
        }
        ServiceManagementSystem.saveDataToDatabase();
        showAlert("Registration successful!");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent loginRoot = loader.load();
            Scene loginScene = new Scene(loginRoot);
            Stage currentStage = (Stage) goToLogin.getScene().getWindow();
            currentStage.setScene(loginScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error: Could not load the login screen.");
        }
    }
}
