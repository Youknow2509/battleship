package github.com.youknow2509.battleship.utils.random;

import github.com.youknow2509.battleship.model.Board;
import github.com.youknow2509.battleship.model.Cell;
import github.com.youknow2509.battleship.model.Position;
import github.com.youknow2509.battleship.model.ship.Ship;

import java.util.ArrayList;
import java.util.List;

public class CreateBroadGame {
    // variables
    private int columns, rows;
    private Cell[][] grid;
    private List<Ship> ships;

    // constructor
    public CreateBroadGame() {
    }

    public CreateBroadGame(int columns, int rows, List<Ship> ships) {
        this.columns = columns;
        this.rows = rows;

        this.grid = new Cell[rows][columns];
        this.ships = ships;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = new Cell(new Position(i, j));
            }
        }
    }

    // random ship placement on the board
    public void randomShipPlacement() {
        for (Ship ship : ships) {
            int index = 0;
            boolean isPlaced = false;
            while (!isPlaced) {
                // random position
                int x = (int) (Math.random() * rows);
                int y = (int) (Math.random() * columns);
                // random direction
                boolean isHorizontal = Math.random() < 0.5;

                if(isHorizontal) {
                    if (y + ship.getSize() < columns) {
                        boolean canPlace = true;
                        for (int i = 0; i < ship.getSize(); i++) {
                            if (grid[x][y + i].isHasShip()) {
                                canPlace = false;
                                break;
                            }
                        }
                        if (canPlace) {
                            for (int i = 0; i < ship.getSize(); i++) {
                                // set information for cell
                                grid[x][y + i].setHasShip(true);
                                grid[x][y + i].setShipInCell(ship);
                                // set information for ship
                                ship.setCell(index++, grid[x][y + i]);
                                ship.setHorizontal(isHorizontal);
                            }
                            isPlaced = true;
                        }
                    }
                } else {
                    if (x + ship.getSize() < rows) {
                        boolean canPlace = true;
                        for (int i = 0; i < ship.getSize(); i++) {
                            if (grid[x + i][y].isHasShip()) {
                                canPlace = false;
                                break;
                            }
                        }
                        if (canPlace) {
                            for (int i = 0; i < ship.getSize(); i++) {
                                // set information for cell
                                grid[x + i][y].setHasShip(true);
                                grid[x + i][y].setShipInCell(ship);
                                // set information for ship
                                ship.setCell(index++, grid[x + 1][y]);
                                ship.setHorizontal(isHorizontal);
                            }
                            isPlaced = true;
                        }
                    }
                }
            }
            index = 0;
        }
    }

    // get board
    public Board getBoard() {
        randomShipPlacement();
        return new Board(columns, rows, grid, ships);
    }
}
