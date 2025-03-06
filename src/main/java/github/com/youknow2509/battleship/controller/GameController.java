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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;

public class GameController {
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

    private void setupPlayerClickEvents() {
        playerGrid.getChildren().forEach(node -> node.setOnMouseClicked(event -> {
            if (playerTurns == 1) return;

            int row = GridPane.getRowIndex(node);
            int col = GridPane.getColumnIndex(node);
            handlePlayerClick(row, col);
            node.setDisable(true);
        }));
    }

    private void handlePlayerClick(int row, int col) {
        Cell cell = playerBoard.getCell(row, col);
        if (cell.isHasShip()) {
            handleShipHit(playerGrid, cell, 0);
        } else {
            handleMiss(playerGrid, cell);
            botTurn();
        }
    }

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

    private void handleShipHit(GridPane grid, Cell cell, int player)    {
        StackPane pane = getStackPane(grid, cell.getPosition().getX(), cell.getPosition().getY());
        if (pane == null) return;

        new ImageViewUtils().setImageView(pane, Consts.PATH_IMAGE_DAME_HIT, Consts.SIZE_CELL, Consts.SIZE_CELL);
        cell.setHit(true);

        Ship ship = cell.getShipInCell();
        ship.setHitCount(ship.getHitCount() + 1);
        if (ship.isSunk()) {
            pane.getChildren().clear();
            ship.getCells().forEach(c -> c.getShipInCell().showShipInGridPane(grid));
            setShipOpacity(ship.getShipType(), player);
        }
    }

    private void handleMiss(GridPane grid, Cell cell) {
        playerTurns = playerTurns == 0 ? 1 : 0;
        StackPane pane = getStackPane(grid, cell.getPosition().getX(), cell.getPosition().getY());
        if (pane != null)
            new ImageViewUtils().setImageView(pane, Consts.PATH_IMAGE_DAME_MISS, Consts.SIZE_CELL, Consts.SIZE_CELL);
    }

    private void setShipOpacity(ShipType type, int player) {
        ImageView imageView = player == 1 ? botShips.get(type) : playerShips.get(type);
        if (imageView != null) imageView.setOpacity(0.5);
    }

    private void renderGrid(GridPane grid, Board board) {
        board.getCells().stream().filter(Cell::isHasShip).forEach(cell -> {
            StackPane pane = getStackPane(grid, cell.getPosition().getX(), cell.getPosition().getY());
            if (pane != null)
                pane.getChildren().add(new Rectangle(Consts.SIZE_CELL, Consts.SIZE_CELL, Color.RED));
        });
    }

    private StackPane getStackPane(GridPane grid, int row, int col) {
        return grid.getChildren().stream()
                .filter(node -> GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col)
                .map(node -> (StackPane) node).findFirst().orElse(null);
    }
}
