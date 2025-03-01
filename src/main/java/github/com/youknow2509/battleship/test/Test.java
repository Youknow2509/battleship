package github.com.youknow2509.battleship.test;

import github.com.youknow2509.battleship.config.Config;
import github.com.youknow2509.battleship.model.Board;
import github.com.youknow2509.battleship.utils.random.CreateBroadGame;
import github.com.youknow2509.battleship.utils.utils;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        System.out.println("Testing ...");
        Config config = new Config();
        CreateBroadGame createBroadGame = new CreateBroadGame(
                config.getBoardCloumns(),
                config.getBoardRows(),
                config.getShips()
        );

        Board board = createBroadGame.getBoard();
        System.out.println("Place Ship: ");
        String[][] placeShip = utils.printResult(board);
//        int[][] placeShip = utils.placeShip(board);
        for (String[] row : placeShip) {
            System.out.println(Arrays.toString(row));
        }

        System.out.println("Creating broad...");
    }
}
