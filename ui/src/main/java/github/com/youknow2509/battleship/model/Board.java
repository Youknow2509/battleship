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

    public Board(int columns, int rows, Cell[][] grid, List<Ship> ships) {
        this.columns = columns;
        this.rows = rows;
        this.grid = grid;
        this.ships = ships;
    }

    // get list of cells
    public List<Cell> getCells() {
        List<Cell> cells = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                cells.add(grid[row][col]);
            }
        }
        return cells;
    }

    // check all ship sunk
    public boolean isAllShipsSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    // Get cell by position
    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    // getter and setter
    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public void setGrid(Cell[][] grid) {
        this.grid = grid;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }
}
