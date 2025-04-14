package github.com.youknow2509.battleship.controller;

import github.com.youknow2509.battleship.Main;
import github.com.youknow2509.battleship.config.Config;
import github.com.youknow2509.battleship.consts.Consts;
import github.com.youknow2509.battleship.model.Board;
import github.com.youknow2509.battleship.model.ship.Ship;
import github.com.youknow2509.battleship.utils.random.CreateBoardGame;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class GameBotInitController {
    // variables fxml
    @FXML
    private VBox root;
    @FXML
    private ImageView btnBack, btnRandomGame;
    @FXML
    private GridPane gridGame;

    // variables controller
    private Config configGame;
    private CreateBoardGame randomGame;
    private Board boardGame;

    // Constructor
    public GameBotInitController() {
        this.configGame = new Config();
        this.randomGame = new CreateBoardGame(
                configGame.getBoardCloumns(),
                configGame.getBoardRows(),
                configGame.getShips()
        );
        this.boardGame = this.randomGame.getBoard();
    }

    @FXML
    public void initialize() {
        gridGame.setDisable(true);
        // render view ship
        showShip();
    }

    // handle event click image view play game
    public void handleBtnPlayGame(MouseEvent mouseEvent) {
        // Get the current stage from the event source
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        // create game board for bot
        Board boardGameBot = this.randomGame.getBoard();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(Consts.XML_RESOURCE_GAME));
            // Controller instance
            GameController gameController = new GameController(boardGameBot, boardGame);
            // Set controller to fxml
            fxmlLoader.setController(gameController);
            // Load fxml
            Parent root = fxmlLoader.load();
            // Set scene
            Scene scene = new Scene(root);
            // set stage center
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            stage.setX(bounds.getMinX() + (bounds.getWidth() - scene.getWidth()) / 2);
            stage.setY(bounds.getMinY() + (bounds.getHeight() - scene.getHeight()) / 2);

            stage.setTitle("Battleship Game");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // show list ship in grid pane
    private void showShip() {
        List<Ship> listShip = boardGame.getShips();
        for (Ship ship : listShip) {
            ship.getCells().forEach(c -> c.getShipInCell().showShipInGridPane(gridGame));
        }
    }

    // handle event click image view load new game
    public void handleBtnRandomGame(MouseEvent mouseEvent) {
        this.boardGame = this.randomGame.getBoard();
        clearDataInCell();
        showShip();
    }

    // handle event click image view back
    public void handleBtnBack(MouseEvent mouseEvent) {
        // Get the current stage from the event source
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(Main.class.getResource(Consts.XML_RESOURCE_PAGE_ONE));
            Scene scene = new Scene(root);
            stage.setTitle("Battleship Game");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // clear data in cell from grid pane
    public void clearDataInCell() {
        for (int i = 0; i < configGame.getBoardRows(); i++) {
            for (int j = 0; j < configGame.getBoardCloumns(); j++) {
                StackPane cell = (StackPane) gridGame.getChildren().get(i * configGame.getBoardCloumns() + j);
                cell.getChildren().clear();
                // add <Rectangle fill="#c3bcbc" height="32" width="32" />
                Rectangle rectangle = new Rectangle(32, 32);
                rectangle.setFill(Color.web("#c3bcbc"));
                cell.getChildren().add(rectangle);
            }
        }
    }
}
