import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SearchServicesController {

    public String username;
    @FXML
    public void setUsername(String username){
        this.username = username;
    }

    @FXML
    private TableView<Service> servicesTable;

    @FXML
    private TableColumn<Service, String> providerNameColumn;

    @FXML
    private TableColumn<Service, String> jobTypeColumn;

    @FXML
    private TableColumn<Service, String> priceColumn;

    @FXML
    private TableColumn<Service, String> descriptionColumn;

    @FXML
    private TableColumn<Service, String> statusColumn;

    @FXML
    private TableColumn<Service, Void> actionColumn;

    @FXML
    private Button backButton;

    private final ObservableList<Service> serviceList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up columns to map Service attributes
        providerNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProviderName()));
        jobTypeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getExpertise()));
        priceColumn.setCellValueFactory(data -> new SimpleStringProperty("$" + data.getValue().getPrice()));
        descriptionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription()));
        statusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getstatus()));

        // Add action buttons to the table
        addActionButtonToTable();

        // Populate the table with data from ServiceManagementSystem
        loadServices();
    }

    private void loadServices() {
        // Clear existing data
        serviceList.clear();

        // Populate serviceList from ServiceManagementSystem
        for (Service service : ServiceManagementSystem.getServices()) {
            serviceList.add(service);
        }

        // Set the updated list to the TableView
        servicesTable.setItems(serviceList);
    }

    private void addActionButtonToTable() {
        Callback<TableColumn<Service, Void>, TableCell<Service, Void>> cellFactory = param -> new TableCell<>() {
            private final Button acceptButton = new Button("Accept");

            {
                acceptButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
                acceptButton.setOnAction(event -> {
                    Service selectedService = getTableView().getItems().get(getIndex());
                    handleAcceptService(selectedService);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Service service = getTableView().getItems().get(getIndex());
                    if ("pending".equals(service.getstatus())) {
                        setGraphic(acceptButton);
                    } else {
                        setGraphic(null);
                    }
                }
            }
        };

        actionColumn.setCellFactory(cellFactory);
    }

    private void handleAcceptService(Service service) {
        if (service != null) {
            service.setSname(username);
            showAlert("Service Accepted", "You selected the service: " + service.getExpertise() + " from " + service.getProviderName());
            service.setStatus("completed"); // Update the service status
            servicesTable.refresh(); // Refresh the table to reflect the status change
            for(ServiceProvider  provider: ServiceManagementSystem.getProviders())
            {
                if(provider.getName().equals(service.getProviderName()))
                {
                    provider.setBalance(provider.getBalance()+service.getPrice());
                }
            }
            for(ServiceSeeker  seek: ServiceManagementSystem.getSeekers())
            {
                if(seek.getName().equals(username))
                {
                    //System.out.println("balance : "+(seek.getBalance()-service.getPrice()));
                    seek.deductBalance(service.getPrice());
                }
            }    
        }
    }

    @FXML
    private void handleBack() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        ServiceManagementSystem.saveDataToDatabase();
   
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
