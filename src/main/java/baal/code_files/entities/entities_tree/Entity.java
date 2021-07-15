package baal.code_files.entities.entities_tree;

import baal.code_files.PointDouble;
import baal.code_files.entities.movement_tree.Movement;
import baal.code_files.entities.shape_tree.Shape;
import baal.code_files.interfaces.Visible;
import lombok.NonNull;

// отвечает за положение и поведение сущности (здесь обрабатывается эффект столкновения, либо создам отдельный класс)

public abstract class Entity implements Visible {
    @NonNull
    public Shape shape;
    public PointDouble position;
    @NonNull
    public Movement movement;

    public abstract void setPosition(PointDouble new_position);
    public abstract void setPosition(double new_x, double new_y);

    public abstract void moveTick();
    public abstract void moveBack();
}