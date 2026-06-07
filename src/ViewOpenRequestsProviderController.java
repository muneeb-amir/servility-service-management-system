import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ViewOpenRequestsProviderController {

    public String username;
    private String providerExpertise;  // Store the provider's expertise

    @FXML
    public void setUsername(String username){
        this.username = username;
        initialize1();
    }

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
    private TableColumn<ServiceRequest, Void> actionColumn;

    @FXML
    private Button backButton;

    private final ObservableList<ServiceRequest> requestList = FXCollections.observableArrayList();

    @FXML
    public void initialize1() {
        // Set provider's expertise based on the username
       
        for (ServiceProvider provider : ServiceManagementSystem.getProviders()) {
            if (provider.getName().equals(username)) 
            {
                providerExpertise = provider.getExpertise();  // Store expertise
                System.out.println("providerexpertise : "+providerExpertise);
                                break;
            }
        }

        // Map columns to ServiceRequest attributes
        seekerNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSeekerName()));
        expertiseColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getExpertise()));
        budgetColumn.setCellValueFactory(data -> new SimpleStringProperty("$" + data.getValue().getBudget()));
        descriptionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDetails()));
        statusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));

        // Add action buttons to the table
        addActionButtonToTable();

        // Load filtered requests based on provider's expertise
        loadRequests();
    }

    private void loadRequests() {
        // Clear existing data
        requestList.clear();

        // Fetch data from a request management system (mock data used here for illustration)
        for (ServiceRequest request : ServiceManagementSystem.getRequests()) {
            // Filter requests where the job type matches the provider's expertise
            System.out.println("providerexpertise : "+providerExpertise);
            if (request.getExpertise().equals(providerExpertise)) {
                requestList.add(request);
            }
        }

        // Set the filtered list to the TableView
        requestsTable.setItems(requestList);
    }

    private void addActionButtonToTable() {
        Callback<TableColumn<ServiceRequest, Void>, TableCell<ServiceRequest, Void>> cellFactory = param -> new TableCell<>() {
            private final Button acceptButton = new Button("Accept");

            {
                acceptButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-size: 12px;");
                acceptButton.setOnAction(event -> {
                    ServiceRequest selectedRequest = getTableView().getItems().get(getIndex());
                    handleAcceptRequest(selectedRequest);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    ServiceRequest request = getTableView().getItems().get(getIndex());
                    if ("pending".equals(request.getStatus())) {
                        setGraphic(acceptButton);
                    } else {
                        setGraphic(null);
                    }
                }
            }
        };

        actionColumn.setCellFactory(cellFactory);
    }

    private void handleAcceptRequest(ServiceRequest request) {
        if (request != null) {
            request.setpname(username);
            showAlert("Request Accepted", "You accepted the request for: " + request.getExpertise() + " from " + request.getSeekerName());
            request.setStatus("accepted"); // Update the request status
            requestsTable.refresh(); // Refresh the table to reflect the updated status
            for (ServiceProvider provider : ServiceManagementSystem.getProviders()) {
                if (provider.getName().equals(username)) {
                    provider.setBalance(provider.getBalance() + request.getBudget());
                }
            }
            for (ServiceSeeker seek : ServiceManagementSystem.getSeekers()) {
                if (seek.getName().equals(request.getSeekerName())) {
                    seek.deductBalance(request.getBudget());
                }
            }
        }
    }

    @FXML
    private void handleBack() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
