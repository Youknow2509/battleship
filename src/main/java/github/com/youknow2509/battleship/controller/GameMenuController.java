package github.com.youknow2509.battleship.controller;

import github.com.youknow2509.battleship.Main;
import github.com.youknow2509.battleship.consts.Consts;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GameMenuController {
    // variable fxml

    // variable controller
    private Stage stageBefore;

    // Initialize
    public void initialize(Stage stage) {
        this.stageBefore = stage;
    }

    // handle click btn tiep tuc
    public void handleClickBtnTiepTuc(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    // handle click btn thoat application
    public void handleClickBtnThoat(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        stageBefore.close();
        stage.close();
    }

    // handle click btn game moi
    public void handleClickBtnGameMoi(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        // change to init game
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(Consts.XML_RESOURCE_INIT_GAME));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage1 = new Stage();
            // set stage center
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            stage.setX(bounds.getMinX() + (bounds.getWidth() - scene.getWidth()) / 2);
            stage.setY(bounds.getMinY() + (bounds.getHeight() - scene.getHeight()) / 2);

            stage1.setTitle("Battleship Game");
            stage1.setScene(scene);
            stage1.show();

            stageBefore.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
