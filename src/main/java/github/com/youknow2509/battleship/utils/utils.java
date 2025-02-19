package github.com.youknow2509.battleship.utils;

import github.com.youknow2509.battleship.model.Board;
import github.com.youknow2509.battleship.model.Cell;

public class utils {
    // out: place ship on the board 0 - have ship, 1 - no ship
    public static int[][] placeShip(Board board) {
        int[][] broad = new int[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                broad[i][j] = board.getGrid()[i][j].isHasShip() ? 0 : 1;
            }
        }
        return broad;
    }

    public static String[][] printResult(Board board) {
        String[][] res = new String[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                Cell c = board.getGrid()[i][j];
                res[i][j] =  c.isHasShip() ? String.valueOf(c.getShipInCell().getSize()) : "*";
            }
        }
        return res;
    }
}
