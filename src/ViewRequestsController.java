import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ViewRequestsController {

    private String username;

    @FXML
    private TableView<ServiceRequest> requestsTable;

    @FXML
    private TableColumn<ServiceRequest, String> seekerNameColumn;

    @FXML
    private TableColumn<ServiceRequest, String> expertiseColumn;

    @FXML
    private TableColumn<ServiceRequest, String> budgetColumn;

    @FXML
    private TableColumn<ServiceRequest, String> descriptionColumn;

    @FXML
    private TableColumn<ServiceRequest, String> statusColumn;

    @FXML
    private TableColumn<ServiceRequest, String> providerNameColumn;

    @FXML
    private Button backButton;

    private final ObservableList<ServiceRequest> requestList = FXCollections.observableArrayList();

    public void setUsername(String username) {
        this.username = username;
        loadServiceRequests();
    }

    @FXML
    public void initialize() {
        // Set up columns to map ServiceRequest attributes
        seekerNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSeekerName()));
        expertiseColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getExpertise()));
        budgetColumn.setCellValueFactory(data -> new SimpleStringProperty("$" + data.getValue().getBudget()));
        descriptionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDetails()));
        statusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));
        providerNameColumn.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getpname() == null || data.getValue().getpname().isEmpty() ? "None" : data.getValue().getpname()));

        // Load the service requests
        loadServiceRequests();
    }

    private void loadServiceRequests() {
        // Clear the current list
        requestList.clear();

        // Fetch requests specific to the logged-in seeker
        for (ServiceRequest request : ServiceManagementSystem.getRequests()) {
            if (request.getSeekerName().equals(username)) {
                requestList.add(request);
            }
        }

        // Set the updated list to the TableView
        requestsTable.setItems(requestList);
    }

    @FXML
    private void handleBack() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        ServiceManagementSystem.saveDataToDatabase();
    }
}