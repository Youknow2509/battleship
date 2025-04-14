package github.com.youknow2509.battleship.model.repo;

import com.google.gson.annotations.Expose;

import java.util.List;

public class BodyReq {
    @Expose
    public List<List<Integer>> board;
    @Expose
    public List<Integer> listShips;

    // Constructor
    public BodyReq(List<List<Integer>> board, List<Integer> listShips) {
        this.board = board;
        this.listShips = listShips;
    }

    // Getter và Setter
    public List<List<Integer>> getBoard() {
        return board;
    }

    public void setBoard(List<List<Integer>> board) {
        this.board = board;
    }

    public List<Integer> getListShips() {
        return listShips;
    }

    public void setListShips(List<Integer> listShips) {
        this.listShips = listShips;
    }
}
