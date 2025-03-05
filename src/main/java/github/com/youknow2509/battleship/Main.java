package github.com.youknow2509.battleship;

import github.com.youknow2509.battleship.config.Config;
import github.com.youknow2509.battleship.consts.Consts;
import github.com.youknow2509.battleship.controller.GameController;
import github.com.youknow2509.battleship.model.Board;
import github.com.youknow2509.battleship.utils.random.CreateBoardGame;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(Consts.XML_RESOURCE_GAME));
        // Init data for controller
        Config configGame = new Config();
        CreateBoardGame createBoardGame = new CreateBoardGame(
                configGame.getBoardCloumns(),
                configGame.getBoardRows(),
                configGame.getShips()
        );
        Board playerBoard = createBoardGame.getBoard();
        Board botBoard = createBoardGame.getBoard();
        // Controller instance
        GameController gameController = new GameController(playerBoard, botBoard);
        // Set controller to fxml
        fxmlLoader.setController(gameController);
        // Load fxml
        Parent root = fxmlLoader.load();
        // Set scene
        Scene scene = new Scene(root);
        stage.setTitle("Battleship Game");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}