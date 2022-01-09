package baal.code_files.entities.entities_tree;

import baal.code_files.PointDouble;
import baal.code_files.entities.Visible;
import baal.code_files.entities.controllability_tree.Controllability;
import baal.code_files.entities.movement_tree.Movement;
import baal.code_files.entities.shape_tree.Shape;
import lombok.NonNull;

public abstract class Entity implements Visible {
    @NonNull
    public Shape shape;
    public PointDouble position;
    @NonNull
    public Movement movement;
    @NonNull
    public Controllability controllability;

    public void setPosition(PointDouble new_position) {
        position = new_position;
    }

    public void setPosition(double new_x, double new_y) {
        PointDouble newPosition = new PointDouble(new_x, new_y);
        setPosition(newPosition);
    }

    public void moveTick() {
        movement.moveTick(this);
    }
    public void moveBack() {
        movement.moveBack(this);
    }

    public void connect(){
        this.controllability.setEntity(this);
    }
}