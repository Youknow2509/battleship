package github.com.youknow2509.battleship.controller;

import github.com.youknow2509.battleship.consts.Consts;
import github.com.youknow2509.battleship.model.Board;
import github.com.youknow2509.battleship.model.Cell;
import github.com.youknow2509.battleship.model.ship.Ship;
import github.com.youknow2509.battleship.model.ship.ShipType;
import github.com.youknow2509.battleship.utils.image.ImageViewUtils;
import github.com.youknow2509.battleship.utils.utils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameController {
    @FXML
    private VBox root;
    @FXML
    private GridPane playerGrid, botGrid;
    @FXML
    private TextField playerTitle, botTitle;

    @FXML
    private ImageView battleship_player, aircraft_carrier_player, cruiser_player, submarine_player, destroyer_player;
    @FXML
    private ImageView battleship_bot, aircraft_carrier_bot, cruiser_bot, submarine_bot, destroyer_bot;

    private final Board playerBoard, botBoard;
    private int playerTurns = 0; // 0 = player, 1 = bot

    private final Map<ShipType, ImageView> playerShips = new HashMap<>(), botShips = new HashMap<>();

    public GameController(Board playerBoard, Board botBoard) {
        this.playerBoard = playerBoard;
        this.botBoard = botBoard;
        utils.printResult(playerBoard);
    }

    @FXML
    public void initialize() {
        botGrid.setDisable(true);
        setupShipMappings();
        renderGrid(botGrid, botBoard);
        setupPlayerClickEvents();
    }

    // handle click menu button - handle popup menu
    public void handleClickMenu(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Consts.XML_RESOURCE_GAME_MENU));
            Parent root = loader.load();

            // Get the controller instance from the FXMLLoader
            GameMenuController controller = loader.getController();

            // Get the current stage from the event source
            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            // Create a new stage and set it to the controller
            Stage newStage = new Stage();
            controller.initialize(currentStage);

            newStage.setTitle("Menu");
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // check winner
    private void checkWinner(int playerTurns) {
        switch (playerTurns) {
            case 0:
                if (playerBoard.isAllShipsSunk()) {
                    handleWinner(0);
                }
                break;
            case 1:
                if (botBoard.isAllShipsSunk()) {
                    handleWinner(1);
                }
                break;
            default:
                break;
        }
    }

    // Handle the winner of the game
    private void handleWinner(int player) {
        // Disable both grids when the game ends
        playerGrid.setDisable(true);
        botGrid.setDisable(true);
        botTitle.setOpacity(0.0);

        // Set the winner and loser titles
        if (player == 0) {
            playerTitle.setText("You Win!");
            playerTitle.setStyle("-fx-background-color: #00ff00");
            botGrid.setOpacity(0.2);
        } else {
            playerTitle.setText("You Lose!");
            playerTitle.setStyle("-fx-background-color: #ff0000");
            playerGrid.setOpacity(0.2);
        }

        // Add animation for the title change
        utils.animateTitle(playerTitle);
    }

    // Set image ship for player and bot
    private void setupShipMappings() {
        playerShips.put(ShipType.BATTLESHIP, battleship_player);
        playerShips.put(ShipType.CARRIER, aircraft_carrier_player);
        playerShips.put(ShipType.CRUISER, cruiser_player);
        playerShips.put(ShipType.SUBMARINE, submarine_player);
        playerShips.put(ShipType.DESTROYER, destroyer_player);

        botShips.put(ShipType.BATTLESHIP, battleship_bot);
        botShips.put(ShipType.CARRIER, aircraft_carrier_bot);
        botShips.put(ShipType.CRUISER, cruiser_bot);
        botShips.put(ShipType.SUBMARINE, submarine_bot);
        botShips.put(ShipType.DESTROYER, destroyer_bot);
    }

    // listen player click event in grid
    private void setupPlayerClickEvents() {
        playerGrid.getChildren().forEach(node -> node.setOnMouseClicked(event -> {
            if (playerTurns == 1) return;

            int row = GridPane.getRowIndex(node);
            int col = GridPane.getColumnIndex(node);
            handlePlayerClick(row, col);
            node.setDisable(true);
        }));
    }

    // handle player click event
    private void handlePlayerClick(int row, int col) {
        Cell cell = playerBoard.getCell(row, col);
        if (cell.isHasShip()) {
            handleShipHit(playerGrid, cell, 0);
        } else {
            handleMiss(playerGrid, cell);
            botTurn();
        }
    }

    // handle bot choose cell to hit
    private void botTurn() {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Cell cell;
            do {
                int row = (int) (Math.random() * botBoard.getRows());
                int col = (int) (Math.random() * botBoard.getColumns());
                cell = botBoard.getCell(row, col);
            } while (cell.isHit());

            Cell finalCell = cell;
            Platform.runLater(() -> {
                if (finalCell.isHasShip()) {
                    handleShipHit(botGrid, finalCell, 1);
                    botTurn();
                } else {
                    handleMiss(botGrid, finalCell);
                    playerTurns = 0;
                }
            });
        }).start();
    }

    // handle when ship hit for user or bot
    private void handleShipHit(GridPane grid, Cell cell, int player)     {
        StackPane pane = getStackPane(grid, cell.getPosition().getX(), cell.getPosition().getY());
        if (pane == null) return;

        new ImageViewUtils().setImageView(pane, Consts.PATH_IMAGE_DAME_HIT, Consts.SIZE_CELL, Consts.SIZE_CELL);
        cell.setHit(true);

        Ship ship = cell.getShipInCell();
        ship.setHitCount(ship.getHitCount() + 1);
        if (ship.isSunk()) {
            pane.getChildren().clear();
            // Show ship in grid
            ship.getCells().forEach(c -> c.getShipInCell().showShipInGridPane(grid));
            // opacity image hip in button grid
            setShipOpacity(ship.getShipType(), player);
            // check winner or loser
            checkWinner(player);
        }
    }

    // Change turn and handle view in grid when miss ship
    private void handleMiss(GridPane grid, Cell cell) {
        playerTurns = playerTurns == 0 ? 1 : 0;
        StackPane pane = getStackPane(grid, cell.getPosition().getX(), cell.getPosition().getY());
        if (pane != null)
            new ImageViewUtils().setImageView(pane, Consts.PATH_IMAGE_DAME_MISS, Consts.SIZE_CELL, Consts.SIZE_CELL);
    }

    // Set opacity for ship when it's sunk
    private void setShipOpacity(ShipType type, int player) {
        ImageView imageView = player == 1 ? botShips.get(type) : playerShips.get(type);
        if (imageView != null) imageView.setOpacity(0.5);
    }

    // render grid with ship - for bot
    private void renderGrid(GridPane grid, Board board) {
        board.getCells().stream().filter(Cell::isHasShip).forEach(cell -> {
            StackPane pane = getStackPane(grid, cell.getPosition().getX(), cell.getPosition().getY());
            if (pane != null)
                pane.getChildren().add(new Rectangle(Consts.SIZE_CELL, Consts.SIZE_CELL, Color.RED));
        });
    }

    // get stack pane in grid
    private StackPane getStackPane(GridPane grid, int row, int col) {
        return grid.getChildren().stream()
                .filter(node -> GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col)
                .map(node -> (StackPane) node).findFirst().orElse(null);
    }
}
