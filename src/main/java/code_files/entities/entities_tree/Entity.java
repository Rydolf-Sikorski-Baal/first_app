package code_files.entities.entities_tree;

import code_files.PointDouble;
import code_files.entities.movement_tree.Movement;
import code_files.entities.shape_tree.Shape;
import code_files.interfaces.Visible;
import lombok.NonNull;

// отвечает за положение и поведение сущности (здесь обрабатывается эффект столкновения, либо создам отдельный класс)

public abstract class Entity implements Visible {
    @NonNull
    public Shape shape;
    @NonNull
    public PointDouble position;
    @NonNull
    public Movement movement;

    public abstract void setPosition(PointDouble new_position);
    public abstract void setPosition(double new_x, double new_y);

    public abstract void moveTick();
    public abstract void moveBack();
}