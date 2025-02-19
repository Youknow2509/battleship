package github.com.youknow2509.battleship.model.ship;

public class Carrier extends Ship{
    // Variable
    private String name = "Carrier";

    // Constructor
    public Carrier() {
        super(ShipType.CARRIER);
        setSize(ShipType.CARRIER.getLength());
    }

    public Carrier(boolean isHorizontal) {
        super(ShipType.CARRIER, isHorizontal);
        setSize(ShipType.CARRIER.getLength());
    }

    // Getter and Setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
