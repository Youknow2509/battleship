package github.com.youknow2509.battleship.utils.create;

import github.com.youknow2509.battleship.model.ship.*;

public class FactoryCreateShip {
    public static Ship create(ShipType shipType) {
        return switch (shipType) {
            case CARRIER -> new Carrier();
            case BATTLESHIP -> new Battleship();
            case CRUISER -> new Cruiser();
            case SUBMARINE -> new Submarine();
            case DESTROYER -> new Destroyed();
            default -> null;
        };
    }
}
