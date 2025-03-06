package github.com.youknow2509.battleship.model.ship;

import github.com.youknow2509.battleship.consts.Consts;
import github.com.youknow2509.battleship.model.Cell;
import github.com.youknow2509.battleship.model.Position;
import github.com.youknow2509.battleship.utils.image.ImageViewUtils;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

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

    @Override
    public void showShipInGridPane(GridPane gridPane) {
        super.showShipInGridPane(gridPane);
        ImageViewUtils imageViewUtils = new ImageViewUtils();
        for (Cell c: getCells()) {
            int row = c.getPosition().getX();
            int col = c.getPosition().getY();
            int index = c.getPositionShip();
            String pathImage = getElementPathImageShip(index);
            // Get stack pane from grid pane
            StackPane stackPane = getStackPane(gridPane, row, col);
            imageViewUtils.setImageView(
                    stackPane,
                    pathImage,
                    Consts.SIZE_CELL,
                    Consts.SIZE_CELL,
                    isHorizontal()
            );
        }
    }

    // helper get path boat with index of ship
    @Override
    public String getElementPathImageShip(int index) {
        String pathImage = "";
        switch (index) {
            case 0:
                pathImage = Consts.PATH_IMAGE_BOAT_BATTLESHIP_0;
                break;
            case 1:
                pathImage = Consts.PATH_IMAGE_BOAT_BATTLESHIP_1;
                break;
            case 2:
                pathImage = Consts.PATH_IMAGE_BOAT_BATTLESHIP_2;
                break;
            case 3:
                pathImage = Consts.PATH_IMAGE_BOAT_BATTLESHIP_3;
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
