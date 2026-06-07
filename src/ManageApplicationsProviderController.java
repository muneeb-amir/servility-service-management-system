import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ManageApplicationsProviderController {

    @FXML
    private TableView<Application> applicationsProviderTable;

    @FXML
    private TableColumn<Application, String> jobTypeColumn;

    @FXML
    private TableColumn<Application, String> locationColumn;

    @FXML
    private TableColumn<Application, String> timeColumn;

    @FXML
    private TableColumn<Application, String> descriptionColumn;

    @FXML
    private TableColumn<Application, RadioButton> statusColumn;

    @FXML
    private Button confirmButton;

    @FXML
    private Button backButton;

    private ObservableList<Application> applications = FXCollections.observableArrayList(
            new Application("Fix Plumbing", "New York", "10:00 AM", "Leaking kitchen sink", false),
            new Application("Clean Garage", "Los Angeles", "2:00 PM", "Deep cleaning required", false),
            new Application("Paint Fence", "Chicago", "9:00 AM", "Repaint wooden fence", false),
            new Application("Install Outlet", "Houston", "4:00 PM", "Electrical outlet installation", false)
    );

    @FXML
    public void initialize() {
        // Mapping table columns to Application properties
        jobTypeColumn.setCellValueFactory(new PropertyValueFactory<>("jobType"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Custom cell factory for the status column
        statusColumn.setCellFactory(column -> new TableCell<Application, RadioButton>() {
            private final RadioButton radioButton = new RadioButton();

            @Override
            protected void updateItem(RadioButton item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    Application application = getTableView().getItems().get(getIndex());
                    radioButton.setSelected(application.isCompleted());

                    // Lock the radio button if the status is locked
                    if (application.isStatusLocked()) {
                        radioButton.setDisable(true);  // Disable if the status is locked
                    } else {
                        radioButton.setDisable(false);
                        radioButton.setOnAction(event -> {
                            if (radioButton.isSelected()) {
                                confirmButton.setVisible(true); // Show the confirm button
                            }
                        });
                    }

                    setGraphic(radioButton);
                }
            }
        });

        // Add data to the table
        applicationsProviderTable.setItems(applications);
    }

    // Handle the "Confirm" button click
    @FXML
    private void handleConfirm() {
        // Get the selected application from the table
        Application selectedApplication = applicationsProviderTable.getSelectionModel().getSelectedItem();
        if (selectedApplication != null) {
            // Mark the application as completed
            selectedApplication.setCompleted(true);
            // Disable the radio button to prevent unchecking
            disableRadioButton(selectedApplication);
            confirmButton.setVisible(false); // Hide the confirm button after it's clicked
            showAlert("Application Completed", "You have marked the application as completed.");

            // Lock the status to prevent further changes
            selectedApplication.setStatusLocked(true);
        }
    }

    // Disable the radio button for the selected application to prevent unchecking
    private void disableRadioButton(Application application) {
        for (int i = 0; i < applicationsProviderTable.getItems().size(); i++) {
            if (applicationsProviderTable.getItems().get(i).equals(application)) {
                TableRow<Application> row = (TableRow<Application>) applicationsProviderTable.lookup(".table-row-cell:" + i);
                if (row != null) {
                    RadioButton radioButton = (RadioButton) row.lookup(".radio-button");
                    if (radioButton != null) {
                        radioButton.setDisable(true); // Disable radio button
                    }
                }
            }
        }
    }

    // Handle the "Back" action
    @FXML
    private void handleBack() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    // Helper method to show alert dialogs
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
