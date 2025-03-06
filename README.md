# Contact:
- **Mail**: *lytranvinh.work@gmail.com*
- **Github**: *https://github.com/Youknow2509*

# Introduction:
- Clone Game Battleship with JavaFx
- Game play with computer bot. Bot use AI to play game.

package github.com.youknow2509.battleship.controller;

import github.com.youknow2509.battleship.consts.Consts;
import github.com.youknow2509.battleship.model.Board;
import github.com.youknow2509.battleship.model.Cell;
import github.com.youknow2509.battleship.model.ship.Ship;
import github.com.youknow2509.battleship.utils.image.ImageViewUtils;
import github.com.youknow2509.battleship.utils.utils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;

public class GameController {

    /**
     * Variables from FXML
     */
    @FXML private GridPane playerGrid;
    @FXML private GridPane botGrid;
    @FXML private TextField playerTitle;
    @FXML private TextField botTitle;
    @FXML private ImageView battleship;
    @FXML private ImageView aircraftCarrier;
    @FXML private ImageView cruiser;
    @FXML private ImageView submarine;
    @FXML private ImageView destroyer;

    /**
     * Game Variables
     */
    private final Board playerBoard;
    private final Board botBoard;
    private int playerTurns = 0; // 0 = player, 1 = bot

    // Constructor - Receives game data
    public GameController(Board playerBoard, Board botBoard) {
        this.playerBoard = playerBoard;
        this.botBoard = botBoard;

        utils.printResult(playerBoard);
        utils.printResult(botBoard);
    }

    // Initializes UI when FXML is loaded
    @FXML
    public void initialize() {
        System.out.println("initialize");
//        showDataToGrid(playerGrid, playerBoard);
//        showDataToGrid(botGrid, botBoard);

        clickInGridPane(playerGrid, playerBoard);
        clickInGridPane(botGrid, botBoard);
    }

    // Handle click in grid
    private void clickInGridPane(GridPane gridPane, Board board) {
        for (int row = 0; row < gridPane.getRowCount(); row++) {
            for (int col = 0; col < gridPane.getColumnCount(); col++) {
                StackPane cell = getStackPane(gridPane, row, col);
                int rowClick = row;
                int colClick = col;
                cell.setOnMouseClicked((MouseEvent event) -> {
                    System.out.println("Clicked on cell: " + rowClick + " " + colClick);
                    Cell cellData = board.getCell(rowClick, colClick);
                    if (cellData.isHasShip()) {
                        System.out.println("Hit!");
                        clickInShip(gridPane, board, cellData);
                    } else {
                        System.out.println("Miss!");
                        this.clickMiss(gridPane, board, cellData);
                    }
                    cell.setDisable(true);
                });
            }
        }
    }

    /** Handle click is ship */
    private void clickInShip(GridPane gridPane, Board board, Cell cellData) {
        // Change image in cell
        StackPane stackPane = getStackPane(gridPane, cellData.getPosition().getX(), cellData.getPosition().getY());
        if (stackPane == null)
            return;
        stackPane.getChildren().clear();
        ImageViewUtils imageViewUtils = new ImageViewUtils();
        imageViewUtils.setImageView(stackPane, Consts.PATH_IMAGE_DAME_HIT, Consts.SIZE_CELL, Consts.SIZE_CELL);
        // Set data when hit
        cellData.setHit(true);
        Ship ship = cellData.getShipInCell();
        ship.setHitCount(ship.getHitCount() + 1);
        // Check if ship is sunk
        if (ship != null) {
            if (ship.isSunk()) {
                System.out.println("Ship " + ship.getShipType().getName() + " is sunk!");                // Change image in all cells of ship
                ship.getCells().forEach(cell -> {
                    StackPane stackPaneShip = getStackPane(gridPane, cell.getPosition().getX(), cell.getPosition().getY());
                    if (stackPaneShip == null)
                        return;
                    stackPaneShip.getChildren().clear();
                    // handle show ship when sunk
                    showShipWhenSunk(gridPane, ship);
                });
            }
        }
    }

    /** Handle click miss */
    private void clickMiss(GridPane gridPane, Board board, Cell cellData) {
        // Change player turn
        this.playerTurns = this.playerTurns == 0 ? 1 : 0;
        // Change image in cell to miss
        StackPane stackPane = getStackPane(gridPane, cellData.getPosition().getX(), cellData.getPosition().getY());
        if (stackPane == null)
            return;
        stackPane.getChildren().clear();
        ImageViewUtils imageViewUtils = new ImageViewUtils();
        imageViewUtils.setImageView(stackPane, Consts.PATH_IMAGE_DAME_MISS, Consts.SIZE_CELL, Consts.SIZE_CELL);
    }

    /** handle show ship when sunk */
    private void showShipWhenSunk(GridPane gridPane, Ship ship) {
        // TODO
    }
        
    /** Render board onto GridPane */
    private void showDataToGrid(GridPane grid, Board board) {

        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getColumns(); col++) {
                Cell cellData = board.getCell(row, col);
                if (cellData.isHasShip()) {
                    StackPane stackPane = getStackPane(grid, row, col);
                    stackPane.getChildren().add(new Rectangle(30, 30, Color.RED));
                }
            }
        }
    }

    /** Helper function to get a StackPane from the GridPane */
    private StackPane getStackPane(GridPane grid, int row, int col) {
        for (javafx.scene.Node node : grid.getChildren()) {
            if (GridPane.getRowIndex(node) != null && GridPane.getColumnIndex(node) != null) {
                if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                    return (StackPane) node;
                }
            }
        }
        return null;
    }
}

xử lí yêu cầu người chơi, chơi với bot, nếu luợt của người chơi thì có thể chọn thuyền trên bảng, nếu khôn phải lượt không thể chọn, nếu bắn trúng thì được bắn liên tiếp, bắt trượt đổi lượt. Xử lí bớt bắn tương tự người - bot có thể random ô hoặc call api, trong thời gian lượt của bot người dùng không được thao tác trên bảng ? 