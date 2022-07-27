package baal.code_files.entities.entities_tree;

import baal.code_files.entities.controllability_tree.HeroControls;
import baal.code_files.entities.movement_tree.AccordingToSpeed;
import baal.code_files.entities.shape_tree.Rectangle;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import lombok.Setter;

import java.util.Objects;

public class Hero extends Entity {
    @Setter public Color myColor = Color.RED;
    Image image;

    public Hero(Rectangle rectangle, AccordingToSpeed movement, HeroControls heroControls){
        this.shape = rectangle;
        this.movement = movement;
        this.controllability = heroControls;
    }

    @Override
    public void drawYourself(Canvas canvas, double cellHeight, double cellWidth) {
        if (image == null)
            image = new Image(Objects.requireNonNull(getClass()
                    .getResourceAsStream("123.png")));

        canvas.getGraphicsContext2D().drawImage(
                image,
                position.getY() * cellWidth, position.getX() * cellHeight,
                0.5 * cellWidth, 0.5 * cellHeight);
    }
}