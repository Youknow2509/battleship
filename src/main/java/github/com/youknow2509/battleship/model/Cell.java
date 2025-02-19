package github.com.youknow2509.battleship.model;

import github.com.youknow2509.battleship.model.ship.ShipType;

public class Cell {
    // variables
    private boolean hasShip;
    private boolean isHit;
    private Position position;
    private ShipType shipType;

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

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }
}
