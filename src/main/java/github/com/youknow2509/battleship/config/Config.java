package github.com.youknow2509.battleship.config;

import github.com.youknow2509.battleship.model.ship.Ship;

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

    }
}
