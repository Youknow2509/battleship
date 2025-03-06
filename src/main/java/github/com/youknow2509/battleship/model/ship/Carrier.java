package github.com.youknow2509.battleship.model.ship;

import github.com.youknow2509.battleship.consts.Consts;
import github.com.youknow2509.battleship.model.Cell;
import github.com.youknow2509.battleship.utils.image.ImageViewUtils;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

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

    // helper get path boat with index of ship
    @Override
    public String getElementPathImageShip(int index) {
        String pathImage = "";
        switch (index) {
            case 0:
                pathImage = Consts.PATH_IMAGE_BOAT_CARRIER_0;
                break;
            case 1:
                pathImage = Consts.PATH_IMAGE_BOAT_CARRIER_1;
                break;
            case 2:
                pathImage = Consts.PATH_IMAGE_BOAT_CARRIER_2;
                break;
            case 3:
                pathImage = Consts.PATH_IMAGE_BOAT_CARRIER_3;
                break;
            case 4:
                pathImage = Consts.PATH_IMAGE_BOAT_CARRIER_4;
                break;
            default:
                break;
        }
        return pathImage;
    }

    // Getter and Setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
