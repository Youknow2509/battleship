package github.com.youknow2509.battleship.model.ship;

import github.com.youknow2509.battleship.consts.Consts;

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

    // helper get path boat with index of ship
    @Override
    public String getElementPathImageShip(int index) {
        String pathImage = "";
        switch (index) {
            case 0:
                pathImage = Consts.PATH_IMAGE_BOAT_CRUISER_0;
                break;
            case 1:
                pathImage = Consts.PATH_IMAGE_BOAT_CRUISER_1;
                break;
            case 2:
                pathImage = Consts.PATH_IMAGE_BOAT_CRUISER_2;
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
