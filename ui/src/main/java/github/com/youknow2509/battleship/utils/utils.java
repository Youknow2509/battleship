package github.com.youknow2509.battleship.utils;

import github.com.youknow2509.battleship.model.Board;
import github.com.youknow2509.battleship.model.Cell;
import github.com.youknow2509.battleship.model.ship.Ship;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class utils {
    // out: place ship on the board 0 - have ship, 1 - no ship
    public static int[][] placeShip(Board board) {
        int[][] broad = new int[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                broad[i][j] = board.getGrid()[i][j].isHasShip() ? 1 : 0;
            }
        }
        return broad;
    }

    // out: list of ships that are not sunk
    public static List<Integer> getListShipNotSunk(Board board) {
        List<Integer> listShipDontSunk = new ArrayList<Integer>();
        for (int i = 0; i < board.getShips().size(); i++) {
            Ship ship = board.getShips().get(i);
            if (!ship.isSunk()) {
                listShipDontSunk.add(ship.getSize());
            }
        }
        return listShipDontSunk;
    }

    // out: data gridpane req
    public static int[][] getGridPaneReq(Board board) {
        int[][] gridPaneReq = new int[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                gridPaneReq[i][j] = board.getGrid()[i][j].isHit() ? 1 : 0;
            }
        }
        return gridPaneReq;
    }

    public static String[][] printResult(Board board) {
        String[][] res = new String[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                Cell c = board.getGrid()[i][j];
                res[i][j] =  c.isHasShip() ? String.valueOf(c.getShipInCell().getSize()) : "*";
                System.out.print(res[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        return res;
    }

    // get background color
    public static Background getBackground(Color color) {
        return new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY));
    }

    // animateTitle
    public static void animateTitle(TextField title) {
        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) lastTime = now;
                double timeElapsed = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;

                // Đổi opacity theo thời gian (giảm dần rồi tăng lên)
                double opacity = (Math.sin(timeElapsed * Math.PI) + 1) / 2; // Giá trị opacity dao động từ 0 đến 1

                title.setOpacity(opacity);
            }
        };
        timer.start();
    }


}
