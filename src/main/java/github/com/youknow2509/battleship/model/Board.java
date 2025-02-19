package github.com.youknow2509.battleship.model;

import github.com.youknow2509.battleship.model.ship.Ship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Board {
    // variables
    private int columns, rows;
    private Cell[][] grid;
    private List<Ship> ships;
    private HashMap<Cell, Ship> cellShipMap;

    // constructor
    Board(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;

        this.grid = new Cell[rows][columns];
        this.ships = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = new Cell(new Position(i, j));
            }
        }
    }

    //
}
