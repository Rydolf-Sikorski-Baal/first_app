package code_files.entities.entities_tree;

import code_files.PointDouble;
import code_files.entities.movement_tree.Movement;
import code_files.entities.shape_tree.Shape;
import javafx.scene.canvas.Canvas;

//@AllArgsConstructor
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

    public Hero(Shape _shape, PointDouble _position, Movement _movement){
        shape = _shape;
        position =_position;
        movement = _movement;
    }

    @Override
    public void drawYourself(Canvas canvas, double top_left_corner_x, double top_left_y, double cellHeight, double cellWidth) {

    }
}