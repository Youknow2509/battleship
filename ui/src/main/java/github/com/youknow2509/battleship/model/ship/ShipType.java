package github.com.youknow2509.battleship.model.ship;

public enum ShipType {
    CARRIER(5),
    BATTLESHIP(4),
    CRUISER(3),
    SUBMARINE(3),
    DESTROYER(2);

    private int length;

    ShipType(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }
    public String getName() {
        return name();
    }
}
