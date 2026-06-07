import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField Email;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private Hyperlink goToSignUp;

    @FXML
    public void initialize() {
        // Manually populate the ComboBox with login roles
        roleComboBox.getItems().add("Service Seeker");
        roleComboBox.getItems().add("Service Provider");
        roleComboBox.getItems().add("Admin");
    }

    @FXML
    private void handleLogin() {
        String username = Email.getText();
        String password = passwordField.getText();
        String selectedRole = roleComboBox.getValue();

        if (selectedRole == null) {
            showAlert("Login Failed", "Please select a role to login.");
            return;
        }

        // Validate user credentials based on selected role
        authenticateUser(username, password, selectedRole);
    }

    @FXML
    private void goToSignUp() {
        try {
            // Load the SignUp FXML file
            FXMLLoader signUpLoader = new FXMLLoader(getClass().getResource("signup.fxml"));
            AnchorPane signUpRoot = signUpLoader.load();

            // Create a new Scene with the SignUp root and set it on the current stage
            Scene signUpScene = new Scene(signUpRoot);
            Stage stage = (Stage) goToSignUp.getScene().getWindow(); // Get the current stage

            // Set the new scene for the stage
            stage.setScene(signUpScene);
            stage.setTitle("Sign Up Page");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Authenticate user based on role
     */
    private boolean authenticateUser(String email, String password, String role) {
        try {
            ServiceManagementSystem.loadDataFromDatabase();
            if (role.equals("Service Seeker")) {
                // Authenticate seeker
                ServiceSeeker seeker = ServiceManagementSystem.authenticateSeeker(email, password);
                if (seeker != null) {
                    // Open the seeker dashboard
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("user_dashboard_seeker.fxml"));
                    AnchorPane seekerRoot = loader.load();

                    //two line
                    UserDashboardSeekerController controller = loader.getController();
	    	   	         controller.setUsername(seeker.getName());
                         
                         System.out.println(seeker.getName());
    
                    // Set the new scene for the seeker dashboard
                    Scene seekerScene = new Scene(seekerRoot);
                    Stage stage = (Stage) roleComboBox.getScene().getWindow();
                    stage.setScene(seekerScene);
                    stage.setTitle("Service Seeker Dashboard");
                    stage.show();
    
                    return true; // Authentication successful
                }
            } else if (role.equals("Service Provider")) {
                // Authenticate provider
                ServiceProvider provider = ServiceManagementSystem.authenticateProvider(email, password);
                if (provider != null) {
                    // Open the provider dashboard
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("UserDashboardProvider.fxml"));
                    AnchorPane providerRoot = loader.load();
    
                    UserDashboardProviderController controller = loader.getController();
	    	   	         controller.setUsername(provider.getName());
                    // Set the new scene for the provider dashboard
                    Scene providerScene = new Scene(providerRoot);
                    Stage stage = (Stage) roleComboBox.getScene().getWindow();
                    stage.setScene(providerScene);
                    stage.setTitle("Service Provider Dashboard");
                    stage.show();
    
                    return true; // Authentication successful
                }
            } 
            /*else if (role.equals("Admin")) {
                // Admin logic (example; you can replace it with actual logic)
                if (email.equals("admin@example.com") && password.equals("admin123")) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_dashboard.fxml"));
                    AnchorPane adminRoot = loader.load();
    
                    // Set the new scene for the admin dashboard
                    Scene adminScene = new Scene(adminRoot);
                    Stage stage = (Stage) roleComboBox.getScene().getWindow();
                    stage.setScene(adminScene);
                    stage.setTitle("Admin Dashboard");
                    stage.show();
    
                    return true; // Authentication successful
                }
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return false; // Authentication failed
    }
    
}
