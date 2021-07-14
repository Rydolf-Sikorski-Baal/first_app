package code_files.entities.entities_tree;

import code_files.PointDouble;
import code_files.entities.movement_tree.AccordingToSpeed;
import code_files.entities.movement_tree.Movement;
import code_files.entities.shape_tree.Rectangle;
import code_files.entities.shape_tree.Shape;
import javafx.scene.canvas.Canvas;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Hero extends Entity {
    @Override
    public void setPosition(PointDouble new_position) {
        position = new_position;
    }
    @Override
    public void setPosition(double new_x, double new_y) {
        position.setLocation(new_x, new_y);
    }

    @Override
    public void moveTick() {
        movement.moveTick(this);
    }

    @Override
    public void moveBack() {
        movement.moveBack(this);
    }

    public Hero(Rectangle rectangle, AccordingToSpeed movement){
        this.shape = rectangle;
        this.movement = movement;
    }

    @Override
    public void drawYourself(Canvas canvas, double top_left_corner_x, double top_left_y, double cellHeight, double cellWidth) {

    }
}