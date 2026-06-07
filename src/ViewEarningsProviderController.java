    import javafx.fxml.FXML;
    import javafx.scene.control.*;
    import javafx.stage.Stage;

    public class ViewEarningsProviderController {

        @FXML
        private Label totalEarningsLabel; // Label to display total earnings

        @FXML
        private ListView<String> earningsListView; // List to display earnings breakdown

        @FXML
        private Button backButton; // Back button to return to the previous screen

        // Sample data for earnings (in a real scenario, this data would come from a database or backend system)
        private final String[] earningsBreakdown = {
            "Job: Fix Plumbing in Kitchen - $150.00",
            "Job: Clean Garage - $100.00",
            "Job: Paint the Fence - $200.00",
            "Job: Install New Electrical Outlet - $120.00"
        };

        // Total earnings (calculated from the breakdown for demonstration purposes)
        private final double totalEarnings = 150.00 + 100.00 + 200.00 + 120.00;

        // Initialize the earnings view
        @FXML
        public void initialize() {
            // Populate the ListView with earnings breakdown
            earningsListView.getItems().addAll(earningsBreakdown);

            // Set the total earnings label
            totalEarningsLabel.setText(String.format("$%.2f", totalEarnings));
        }

        // Handle the "Back" action
        @FXML
        private void handleBack() {
            // Logic to close the current window and return to the previous screen
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
        }
    }
