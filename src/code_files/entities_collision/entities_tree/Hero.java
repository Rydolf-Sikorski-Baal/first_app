package code_files.entities_collision.entities_tree;

import code_files.entities_collision.PointDouble;
import code_files.entities_collision.movement_tree.Movement;
import code_files.entities_collision.shape_tree.Shape;
import lombok.AllArgsConstructor;
import lombok.NonNull;

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

    public Hero(Shape _shape, PointDouble _position, Movement _movement){
        shape = _shape;
        position =_position;
        movement = _movement;
    }
}