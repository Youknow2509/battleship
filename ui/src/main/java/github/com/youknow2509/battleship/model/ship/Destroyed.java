package github.com.youknow2509.battleship.model.ship;

import github.com.youknow2509.battleship.consts.Consts;

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

    // helper get path boat with index of ship
    @Override
    public String getElementPathImageShip(int index) {
        String pathImage = "";
        switch (index) {
            case 0:
                pathImage = Consts.PATH_IMAGE_BOAT_DESTROYER_0;
                break;
            case 1:
                pathImage = Consts.PATH_IMAGE_BOAT_DESTROYER_1;
                break;
            default:
                break;
        }
        return pathImage;
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
