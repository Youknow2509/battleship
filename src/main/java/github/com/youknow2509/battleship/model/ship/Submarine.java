package github.com.youknow2509.battleship.model.ship;

public class Submarine extends Ship{
    // variable
    private String name = "Submarine";

    // constructor
    public Submarine() {
        super(ShipType.SUBMARINE);
        setSize(ShipType.SUBMARINE.getLength());
    }

    public Submarine(boolean isHorizontal) {
        super(ShipType.SUBMARINE, isHorizontal);
        setSize(ShipType.SUBMARINE.getLength());
    }

    // getter and setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
