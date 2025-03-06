package github.com.youknow2509.battleship.utils.random;

import github.com.youknow2509.battleship.model.Board;
import github.com.youknow2509.battleship.model.Cell;
import github.com.youknow2509.battleship.model.Position;
import github.com.youknow2509.battleship.model.ship.Ship;
import github.com.youknow2509.battleship.model.ship.ShipType;
import github.com.youknow2509.battleship.utils.create.FactoryCreateShip;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CreateBoardGame {
    private int columns, rows;
    private Cell[][] grid;
    private List<Ship> ships;
    private List<Ship> ships_null; // Backup of original ships

    // Constructor
    public CreateBoardGame(int columns, int rows, List<Ship> ships) {
        this.columns = columns;
        this.rows = rows;

        // Create a deep copy of the ships list
        this.ships_null = new ArrayList<>();
        for (Ship s : ships) {
            ShipType type = s.getShipType();
            Ship shipCopy = FactoryCreateShip.create(type);
            ships_null.add(shipCopy);
        }

        initializeGrid();
    }

    // Initialize grid with empty cells
    private void initializeGrid() {
        grid = new Cell[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = new Cell(new Position(i, j));
            }
        }
    }

    // Randomly place ships on the board
    private void randomShipPlacement() {
        initializeGrid(); // Reset grid before placing ships

        // Restore the original ships list
        ships = new ArrayList<>();
        for (Ship ship : ships_null) {
            ShipType type = ship.getShipType();
            Ship shipCopy = FactoryCreateShip.create(type);
            ships.add(shipCopy);
        }

        for (Ship ship : ships) {
            boolean isPlaced = false;

            while (!isPlaced) {
                int x = ThreadLocalRandom.current().nextInt(rows);
                int y = ThreadLocalRandom.current().nextInt(columns);
                boolean isHorizontal = ThreadLocalRandom.current().nextBoolean();

                if (canPlaceShip(ship, x, y, isHorizontal)) {
                    placeShip(ship, x, y, isHorizontal);
                    isPlaced = true;
                }
            }
        }
    }

    // Check if a ship can be placed at the given position
    private boolean canPlaceShip(Ship ship, int x, int y, boolean isHorizontal) {
        int size = ship.getSize();

        if (isHorizontal) {
            if (y + size > columns) return false;
            for (int i = 0; i < size; i++) {
                if (grid[x][y + i].isHasShip()) return false;
            }
        } else {
            if (x + size > rows) return false;
            for (int i = 0; i < size; i++) {
                if (grid[x + i][y].isHasShip()) return false;
            }
        }
        return true;
    }

    // Place a ship on the board
    private void placeShip(Ship ship, int x, int y, boolean isHorizontal) {
        for (int i = 0; i < ship.getSize(); i++) {
            int newX = isHorizontal ? x : x + i;
            int newY = isHorizontal ? y + i : y;
            //
            ship.setCell(i, grid[newX][newY]);
            // set data cell
            grid[newX][newY].setHasShip(true);
            grid[newX][newY].setShipInCell(ship);
            grid[newX][newY].setPositionShip(i);
        }
        ship.setHorizontal(isHorizontal);
    }

    // Get the board with randomly placed ships
    public Board getBoard() {
        randomShipPlacement();
        return new Board(columns, rows, grid, ships);
    }
}
