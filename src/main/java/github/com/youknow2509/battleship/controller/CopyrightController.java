package github.com.youknow2509.battleship.controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import github.com.youknow2509.battleship.consts.Consts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CopyrightController {

    @FXML
    private Hyperlink gitLink;

    @FXML
    private Hyperlink emailLink;

    @FXML
    private Button backButton;

    @FXML
    private Label dateTimeLabel;

    @FXML
    private Label userLabel;


    @FXML
    public void initialize() {
        // You can set the date/time and user programmatically if needed
        // Otherwise they're already set in the FXML with the values you provided
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dateTimeLabel.setText(LocalDateTime.now().format(formatter));

        // Get current user, or use the provided value
        String currentUser = System.getProperty("user.name");
        if (currentUser != null && !currentUser.isEmpty()) {
            userLabel.setText(currentUser);
        }
    }

    @FXML
    private void handleGitLink(ActionEvent event) {
        openWebPage(gitLink.getText());
    }

    @FXML
    private void handleEmailLink(ActionEvent event) {
        openEmail(emailLink.getText());
    }

    @FXML
    private void handleBackButton(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Consts.XML_RESOURCE_PAGE_ONE));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openWebPage(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            // You may want to show an error dialog here
        }
    }

    private void openEmail(String email) {
        try {
            Desktop.getDesktop().mail(new URI("mailto:" + email));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            // You may want to show an error dialog here
        }
    }
}