package github.com.youknow2509.battleship.model;

public class Position {
    // variables
    private int x; // row
    private int y; // col

    // constructor
    public Position() {
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // getters and setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
