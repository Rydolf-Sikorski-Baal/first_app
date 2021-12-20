package baal.code_files.entities.entities_tree;

import baal.code_files.entities.controllability_tree.Uncontrollable;
import baal.code_files.entities.movement_tree.AccordingToSpeed;
import baal.code_files.entities.shape_tree.Rectangle;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.util.Objects;

public class DeathEntity extends Entity {
    Image image;

    public DeathEntity(Rectangle rectangle, AccordingToSpeed accordingToSpeed, Uncontrollable uncontrollable){
        this.shape = rectangle;
        this.movement = accordingToSpeed;
        this.controllability = uncontrollable;
    }

    @Override
    public void drawYourself(Canvas canvas, double cellHeight, double cellWidth) {
        if (image == null)
            image = new Image(Objects.requireNonNull(getClass()
                    .getResourceAsStream("123.png")));

        canvas.getGraphicsContext2D().drawImage(image, position.getY() * cellWidth, position.getX() * cellHeight,
                0.5 * cellWidth, 0.5 * cellHeight);
    }
}
