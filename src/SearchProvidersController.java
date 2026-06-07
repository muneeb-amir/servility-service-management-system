import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SearchProvidersController {

    @FXML
    private TextField jobTypeField;
    
    @FXML
    private TextField locationField;
    
    @FXML
    private TextField serviceTypeField;
    
    @FXML
    private Button searchButton;
    
    @FXML
    private ListView<String> providersListView;
    
    @FXML
    private Button backButton;

    // Handle when the "Search" button is clicked
    @FXML
    private void handleSearch() {
        // Get data from the search fields
        String jobType = jobTypeField.getText();
        String location = locationField.getText();
        String serviceType = serviceTypeField.getText();

        // Validate inputs
        if (jobType.isEmpty() && location.isEmpty() && serviceType.isEmpty()) {
            showAlert("Error", "Please enter at least one search criterion.");
            return;
        }

        // Simulate searching for providers (replace with actual search logic)
        List<String> searchResults = searchProviders(jobType, location, serviceType);

        // Clear existing items
        providersListView.getItems().clear();

        // Display search results
        if (searchResults.isEmpty()) {
            providersListView.getItems().add("No providers found.");
        } else {
            providersListView.getItems().addAll(searchResults);
        }
    }

    // Simulated provider search (replace with actual backend or database search)
    private List<String> searchProviders(String jobType, String location, String serviceType) {
        // Example static list of service providers (replace with actual search logic)
        List<String> allProviders = new ArrayList<>();
        allProviders.add("Provider 1: Plumbing, New York, General Plumbing");
        allProviders.add("Provider 2: Electrical, San Francisco, Electrical Wiring");
        allProviders.add("Provider 3: Carpentry, New York, Furniture Assembly");
        
        // Filter the list based on search criteria
        List<String> filteredProviders = new ArrayList<>();
        for (String provider : allProviders) {
            if ((jobType.isEmpty() || provider.contains(jobType)) &&
                (location.isEmpty() || provider.contains(location)) &&
                (serviceType.isEmpty() || provider.contains(serviceType))) {
                filteredProviders.add(provider);
            }
        }
        
        return filteredProviders;
    }

    // Handle when the "Back to Dashboard" button is clicked
    @FXML
    private void handleBack() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close(); // Close the current window
    }

    // Helper method to show alert dialogs
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
