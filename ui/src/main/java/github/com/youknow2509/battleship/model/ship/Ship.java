package github.com.youknow2509.battleship.model.ship;

import github.com.youknow2509.battleship.consts.Consts;
import github.com.youknow2509.battleship.model.Cell;
import github.com.youknow2509.battleship.model.Position;
import github.com.youknow2509.battleship.utils.image.ImageViewUtils;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public abstract class Ship {
    // variables
    private ShipType shipType;
    private int size;
    private int hitCount;
    private List<Cell> cells;
    private boolean isSunk;
    private boolean isHorizontal;

    // constructor
    public Ship() {
        this.hitCount = 0;
        this.cells = new ArrayList<>();
        this.isSunk = false;
    }

    public Ship(ShipType shipType) {
        this.shipType = shipType;
        this.size = shipType.getLength();
        this.hitCount = 0;
        this.cells = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            cells.add(null); // Initialize with null or default Cell objects
        }

        this.isSunk = false;
        this.isHorizontal = true;
    }

    public Ship(ShipType shipType, boolean isHorizontal) {
        this.shipType = shipType;
        this.isHorizontal = isHorizontal;

        this.size = shipType.getLength();
        this.hitCount = 0;
        this.cells = new ArrayList<>();
        this.isSunk = false;
    }

    public Ship(Ship other) {
        this.shipType = other.shipType;
        this.size = other.size;
        this.hitCount = other.hitCount;
        this.cells = new ArrayList<>(other.cells);
        this.isSunk = other.isSunk;
        this.isHorizontal = other.isHorizontal;
    }

    /** Show the ship on the broad
     *
     * @param gridPane GridPane - The gridPane of the game
     */
    public void showShipInGridPane(GridPane gridPane) {
        System.out.println("Show ship in grid pane");
        ImageViewUtils imageViewUtils = new ImageViewUtils();
        for (Cell c: getCells()) {
            int row = c.getPosition().getX();
            int col = c.getPosition().getY();
            int index = c.getPositionShip();
            String pathImage = getElementPathImageShip(index);
            // Get stack pane from grid pane
            StackPane stackPane = getStackPane(gridPane, row, col);
            imageViewUtils.setImageView(
                    stackPane,
                    pathImage,
                    Consts.SIZE_CELL,
                    Consts.SIZE_CELL,
                    isHorizontal()
            );
        }
    }

    // helper get path boat with index of ship
    public String getElementPathImageShip(int index) {
        return "";
    }

    // Get stack pane from grid pane
    public StackPane getStackPane(GridPane grid, int row, int col) {
        for (javafx.scene.Node node : grid.getChildren()) {
            if (GridPane.getRowIndex(node) != null && GridPane.getColumnIndex(node) != null) {
                if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                    return (StackPane) node;
                }
            }
        }
        return null;
    }

    /**
     * Check Move the ship from cell to cell
     *
     * @param cellFrom Cell - The cell from the ship
     * @param cellTo Cell - The cell to the ship
     * @param broad Cell[][] - The broad of the game
     * @return boolean - True if the ship is moved, false otherwise
     */
    public boolean isMoveTo(Cell cellFrom, Cell cellTo, Cell[][] broad) {
        // TODO
        return true;
    }

    /**
     * Handle Rotate the ship
     *
     * @param broad Cell[][] - The broad of the game
     * @param cell Cell - The cell of the ship
     * @return boolean - True if the ship is rotated, false otherwise
     */
    public boolean rotate(Cell[][] broad, Cell cell) {
        if (!isRotate(broad, cell)) {
            return false;
        }
        // TODO
        return true;
    }

    /**
     * Check Rotate the ship
     *
     * @param broad Cell[][] - The broad of the game
     * @param cell Cell - The cell of the ship
     * @return boolean - True if the ship is rotated, false otherwise
     */
    public boolean isRotate(Cell[][] broad,  Cell cell) {
        if (isHorizontal()) {
            for (int i = 0; i < cell.getPositionShip(); i++) {
                if (broad[cell.getPosition().getX()][cell.getPosition().getY() - i].isHasShip()) {
                    return false;
                }
            }
            for (int i = 0; i < getSize() - cell.getPositionShip(); i++) {
                if (broad[cell.getPosition().getX()][cell.getPosition().getY() + i].isHasShip()) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < cell.getPositionShip(); i++) {
                if (broad[cell.getPosition().getX() - i][cell.getPosition().getY()].isHasShip()) {
                    return false;
                }
            }
            for (int i = 0; i < getSize() - cell.getPositionShip(); i++) {
                if (broad[cell.getPosition().getX() + i][cell.getPosition().getY()].isHasShip()) {
                    return false;
                }
            }
        }
        return true;
    }

    // add cell to ship
    public void addCell(Cell cell) {
        if (cells.size() == size) {
            throw new IllegalStateException("Ship is already full");
        }
        cells.add(cell);
    }

    // set cell to position in ship
    public void setCell(int index, Cell cell) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Invalid index");
        }
        if (cells.size() <= index) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for length " + cells.size());
        }
        cells.set(index, cell);
    }

    // check if ship is sunk
    public boolean isSunk() {
        return hitCount == size;
    }

    // getter and setter
    public boolean isHorizontal() {
        return isHorizontal;
    }

    public void setHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public void setSunk(boolean sunk) {
        isSunk = sunk;
    }
}
