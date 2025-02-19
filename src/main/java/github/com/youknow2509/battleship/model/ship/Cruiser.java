package github.com.youknow2509.battleship.model.ship;

public class Cruiser extends Ship {
    // variables
    private String name = "Cruiser";

    // constructor
    public Cruiser() {
        super(ShipType.CRUISER);
        setSize(ShipType.CRUISER.getLength());

    }

    public Cruiser(boolean isHorizontal) {
        super(ShipType.CRUISER, isHorizontal);
        setSize(ShipType.CRUISER.getLength());

    }

    // getter and setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
