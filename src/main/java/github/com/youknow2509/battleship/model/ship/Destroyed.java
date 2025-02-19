package github.com.youknow2509.battleship.model.ship;

public class Destroyed extends Ship{
    // variables
    private String name = "Destroyed";

    // constructor
    public Destroyed() {
        super(ShipType.DESTROYER);
        setSize(ShipType.DESTROYER.getLength());
    }

    public Destroyed(boolean isHorizontal) {
        super(ShipType.DESTROYER, isHorizontal);
        setSize(ShipType.DESTROYER.getLength());
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
