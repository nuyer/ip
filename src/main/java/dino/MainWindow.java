package dino;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Dino dino;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private final Image dinoImage = new Image(this.getClass().getResourceAsStream("/images/DaDino.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setDino(Dino d) {
        dino = d;
        try {
            dino.tasks = dino.storage.load();
            dialogContainer.getChildren().add(DialogBox.getDinoDialog(dino.getGreeting(), dinoImage));
        } catch (Exception e) {
            dialogContainer.getChildren().add(DialogBox.getDinoDialog("Issues occurred while loading tasks: " + e.getMessage(), dinoImage));
        }
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Dino's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        if (dino.shouldTerminate) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.exit();
        }

        String input = userInput.getText();
        String response = dino.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDinoDialog(response, dinoImage)
        );
        userInput.clear();
    }
}

