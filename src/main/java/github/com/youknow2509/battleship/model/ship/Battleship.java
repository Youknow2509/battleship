package github.com.youknow2509.battleship.model.ship;

import github.com.youknow2509.battleship.model.Cell;
import github.com.youknow2509.battleship.model.Position;

public class Battleship extends Ship {
    // variables
    private String name = "Battleship";
    
    // constructor
    public Battleship() {
        super(ShipType.BATTLESHIP);
        setSize(ShipType.BATTLESHIP.getLength());
    }
    
    public Battleship(boolean isHorizontal) {
        super(ShipType.BATTLESHIP, isHorizontal);
        setSize(ShipType.BATTLESHIP.getLength());
    }

    // getter and setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
