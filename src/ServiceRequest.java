import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ServiceRequest {

    private final StringProperty jobType;
    private final StringProperty location;
    private final StringProperty time;
    private final StringProperty description;

    // Constructor
    public ServiceRequest(String jobType, String location, String time, String description) {
        this.jobType = new SimpleStringProperty(jobType);
        this.location = new SimpleStringProperty(location);
        this.time = new SimpleStringProperty(time);
        this.description = new SimpleStringProperty(description);
    }

    // Getters for properties
    public StringProperty jobTypeProperty() {
        return jobType;
    }

    public StringProperty locationProperty() {
        return location;
    }

    public StringProperty timeProperty() {
        return time;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    // Getters for raw values (optional)
    public String getJobType() {
        return jobType.get();
    }

    public String getLocation() {
        return location.get();
    }

    public String getTime() {
        return time.get();
    }

    public String getDescription() {
        return description.get();
    }
}
