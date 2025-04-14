package github.com.youknow2509.battleship.model;

import github.com.youknow2509.battleship.model.ship.Ship;

public class Cell {
    // variables
    private boolean hasShip;
    private boolean isHit;
    private Position position;
    private Ship shipInCell;
    private int positionShip;

    // constructor
    public Cell() {
        this.hasShip = false;
        this.isHit = false;
    }

    public Cell(Position position) {
        this.position = position;
        this.hasShip = false;
        this.isHit = false;
    }

    // getters and setters
    public Ship getShipInCell() {
        return shipInCell;
    }
    public void setShipInCell(Ship shipInCell) {
        this.shipInCell = shipInCell;
    }
    public int getPositionShip() {
        return positionShip;
    }
    public void setPositionShip(int positionShip) {
        this.positionShip = positionShip;
    }
    public boolean isHasShip() {
        return hasShip;
    }

    public void setHasShip(boolean hasShip) {
        this.hasShip = hasShip;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
