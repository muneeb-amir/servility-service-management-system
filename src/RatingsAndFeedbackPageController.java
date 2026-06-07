import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RatingsAndFeedbackPageController {

    @FXML
    private Slider ratingSlider; // Slider to capture rating
    @FXML
    private Label ratingLabel; // Label to display the selected rating
    @FXML
    private TextArea feedbackTextArea; // TextArea for user feedback
    @FXML
    private Button submitButton; // Submit button
    @FXML
    private Button backButton; // Back button

    // Initialize the page, setting the initial rating label
    @FXML
    public void initialize() {
        // Display the current rating from the slider
        ratingSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            ratingLabel.setText(String.format("%.0f", newValue));
        });
    }

    // Handle the submit button click
    @FXML
    private void handleSubmit() {
        double rating = ratingSlider.getValue();
        String feedback = feedbackTextArea.getText();

        // Check if the feedback is provided and the rating is set
        if (feedback.isEmpty()) {
            showAlert("Error", "Please provide your feedback.");
        } else {
            // Here, you can save the rating and feedback to the database or handle accordingly
            showAlert("Thank You!", "Your rating and feedback have been submitted.\nRating: " + rating + "\nFeedback: " + feedback);
        }
    }

    // Handle the back button click (close the current window)
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
