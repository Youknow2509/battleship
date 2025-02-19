package github.com.youknow2509.battleship.model.ship;

import github.com.youknow2509.battleship.model.Cell;

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

    /**
     * Move the ship from cell to cell
     *
     * @param cellFrom Cell - The cell from the ship
     * @param cellTo Cell - The cell to the ship
     * @param broad Cell[][] - The broad of the game
     * @return boolean - True if the ship is moved, false otherwise
     */
    public boolean moveTo(Cell cellFrom, Cell cellTo, Cell[][] broad) {
        // TODO
        return true;
    }

    /**
     * Rotate the ship
     *
     * @param broad Cell[][] - The broad of the game
     * @return boolean - True if the ship is rotated, false otherwise
     */
    public boolean rotate(Cell[][] broad) {
        // TODO
        return true;
    }

    // add cell to ship
    public void addCell(Cell cell) {
        if (cells.size() == size) {
            throw new IllegalStateException("Ship is already full");
        }
        cells.add(cell);
    }

    // check if ship is sunk
    public boolean isSunk() {
        return hitCount == size;
    }

    // getter and setter
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
