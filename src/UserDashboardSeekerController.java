import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class UserDashboardSeekerController {

    public String username;
	public double balance;
    @FXML
    public void setUsername(String username){
        this.username = username;
    }


    @FXML
    private Button postRequestButton;

    @FXML
    private Button viewRequestsButton;

    @FXML
    private Button searchProvidersButton;

    @FXML
    private Button viewHistoryButton;

    @FXML
    private Button manageProfileButton;

    @FXML
    private Button checkBalanceButton;

    @FXML
    private Button logoutButton;

    // Handle the "Post New Service Request" action
    @FXML
    private void handlePostRequest() {
        try {

            //System.out.println(username);
            // Load the "Post New Service Request" view
            Stage stage = (Stage) postRequestButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PostNewServiceRequest.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("Post New Service Request");
            
            PostNewServiceRequestController controller = loader.getController();
            controller.setUsername(username);
        } catch (IOException e) {
            showAlert("Error", "An error occurred while loading the service request page.");
        }
    }

    // Handle the "View Posted Requests" action
    @FXML
    private void handleViewRequests() {
        try {
            // Load the ViewRequests FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewRequests.fxml"));
            Parent root = loader.load();
            
            // Create a new stage and show the View Requests window
            Stage stage = new Stage();
            stage.setTitle("Your Posted Service Requests");
            stage.setScene(new Scene(root));
            stage.show();
            ViewRequestsController controller = loader.getController();
            controller.setUsername(username);

        } catch (IOException e) {
            e.printStackTrace();  // Handle loading errors if necessary
        }
    }

    // Handle the "Search Services" action
    @FXML
    private void handleSearchServices() {
        try {
            // Load the SearchServices FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Search_Services.fxml"));
            Parent root = loader.load();
            
            // Create a new stage and show the Search Services window
            Stage stage = new Stage();
            stage.setTitle("Search Service Providers");
            stage.setScene(new Scene(root));
            stage.show();
            SearchServicesController controller = loader.getController();
            controller.setUsername(username);
        } catch (IOException e) {
            e.printStackTrace();  // Handle loading errors if necessary
        }
    }

    // Handle the "View Service History" action
    @FXML
    private void handleViewHistory() {
        try {
            // Load the ViewServiceHistory FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewServiceHistory.fxml"));
            Parent root = loader.load();
    
            // Create a new stage and show the Service History window
            Stage stage = new Stage();
            stage.setTitle("Service History");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();  // Handle loading errors if necessary
        }
    }
    
    // Handle the "Manage Profile" action
    @FXML
    private void handleManageProfile()
    {
        try 
        {
            // Load the ManageProfile FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageProfile.fxml"));
            Parent root = loader.load();

            // Create a new stage and show the Manage Profile window
            Stage stage = new Stage();
            stage.setTitle("Manage Profile");
            stage.setScene(new Scene(root));
            stage.show();
            ManageProfileController controller = loader.getController();
            controller.setUsername(username);
            System.out.println("mun"+username);
        } catch (IOException e) {
            e.printStackTrace();  // Handle loading errors if necessary
        }
    }

    // Handle the "Check Balance" action
    @FXML
    private void handleCheckBalance() {
        for (ServiceSeeker seeker : ServiceManagementSystem.getSeekers()) {
                if (seeker.name == username){
                    balance = seeker.getBalance();
                }
        }
        showAlert("Check Balance", "Your current balance is : $"+ balance );
    }

    // Handle the "Logout" action
    @FXML
    private void handleLogout() {
        try {
            ServiceManagementSystem.saveDataToDatabase();
            Stage stage = (Stage) logoutButton.getScene().getWindow(); // Get the current stage

            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            // Set the scene with the login page
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            stage.setTitle("Login Page");
        
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to navigate to the login page.");
        }
    }

    // Helper method to show alert dialogs
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}