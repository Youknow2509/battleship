package github.com.youknow2509.battleship.config;

import github.com.youknow2509.battleship.model.ship.Ship;
import github.com.youknow2509.battleship.model.ship.ShipType;
import github.com.youknow2509.battleship.utils.create.FactoryCreateShip;

import java.util.ArrayList;
import java.util.List;

public class Config {
    // variables
    private List<Ship> ships;
    private int boardCloumns;
    private int boardRows;

    // constructor
    public Config() {
        this.boardCloumns = 10;
        this.boardRows = 10;
        this.ships = new ArrayList<>();

        // ships
        this.ships.add(FactoryCreateShip.create(ShipType.CARRIER));
        this.ships.add(FactoryCreateShip.create(ShipType.BATTLESHIP));
        this.ships.add(FactoryCreateShip.create(ShipType.CRUISER));
        this.ships.add(FactoryCreateShip.create(ShipType.SUBMARINE));
        this.ships.add(FactoryCreateShip.create(ShipType.DESTROYER));
    }

    // getters and setters
    public List<Ship> getShips() {
        return ships;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }

    public int getBoardCloumns() {
        return boardCloumns;
    }

    public void setBoardCloumns(int boardCloumns) {
        this.boardCloumns = boardCloumns;
    }

    public int getBoardRows() {
        return boardRows;
    }

    public void setBoardRows(int boardRows) {
        this.boardRows = boardRows;
    }
}
