package github.com.youknow2509.battleship.controller;

import github.com.youknow2509.battleship.Main;
import github.com.youknow2509.battleship.consts.Consts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class PageOneController {
    // variable fxml
    @FXML
    private ImageView btnPlay, btnQuit;
    // variable controller

    // constructor
    public PageOneController() {
    }

    // init view
    @FXML
    public void initialize() {
        // initialize
    }

    // handle quit
    public void handleQuit(MouseEvent mouseEvent) {
        // get scene
        Stage stage = (javafx.stage.Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        // close scene
        stage.close();
    }

    // handle change to init game
    public void handleChangeToInitGame(MouseEvent mouseEvent) {
        // get scene
        Scene scene = ((Node) mouseEvent.getSource()).getScene();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(Consts.XML_RESOURCE_INIT_GAME));
            Parent root = fxmlLoader.load();
            Scene newScene = new Scene(root);
            Stage stage = (Stage) scene.getWindow();
            // set stage center
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            stage.setX(bounds.getMinX() + (bounds.getWidth() - scene.getWidth()) / 2);
            stage.setY(bounds.getMinY() + (bounds.getHeight() - scene.getHeight()) / 2);
            stage.setTitle("Battleship Game");
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
