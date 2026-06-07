public class Application {
    private String jobType;
    private String location;
    private String time;
    private String description;
    private boolean completed;
    private boolean statusLocked;  // New field to lock the status

    // Constructor
    public Application(String jobType, String location, String time, String description, boolean completed) {
        this.jobType = jobType;
        this.location = location;
        this.time = time;
        this.description = description;
        this.completed = completed;
        this.statusLocked = false;  // Initially not locked
    }

    // Getters and setters
    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isStatusLocked() {
        return statusLocked;
    }

    public void setStatusLocked(boolean statusLocked) {
        this.statusLocked = statusLocked;
    }
}
