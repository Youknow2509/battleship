package github.com.youknow2509.battleship.model.ship;

import github.com.youknow2509.battleship.consts.Consts;

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

    // helper get path boat with index of ship
    @Override
    public String getElementPathImageShip(int index) {
        String pathImage = "";
        switch (index) {
            case 0:
                pathImage = Consts.PATH_IMAGE_BOAT_SUBMARINE_0;
                break;
            case 1:
                pathImage = Consts.PATH_IMAGE_BOAT_SUBMARINE_1;
                break;
            case 2:
                pathImage = Consts.PATH_IMAGE_BOAT_SUBMARINE_2;
                break;
            default:
                break;
        }
        return pathImage;
    }

    // getter and setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
