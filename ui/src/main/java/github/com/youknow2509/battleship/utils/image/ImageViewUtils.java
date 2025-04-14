package github.com.youknow2509.battleship.utils.image;

import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

public class ImageViewUtils {
    // Set image view in grid pane
    public void setImageView(StackPane stackPane, String pathImage, double width, double height) {
        Image image = new Image(getClass().getResource(pathImage).toExternalForm(), width, height, true, true);
        javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
        stackPane.getChildren().add(imageView);
    }

    public void setImageView(StackPane stackPane, String pathImage, double width, double height, boolean isHorizontal) {
        Image image = new Image(getClass().getResource(pathImage).toExternalForm(), width, height, true, true);
        javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
        if (isHorizontal) {
            imageView.setRotate(-90);
        }
        stackPane.getChildren().add(imageView);
    }
}
