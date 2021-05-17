package code_files.entities_collision.entities_tree;

import code_files.entities_collision.PointDouble;
import code_files.entities_collision.shape_tree.Shape;
import code_files.entities_collision.movement_tree.Movement;
import lombok.NonNull;

import java.awt.geom.Point2D;

// отвечает за положение и поведение сущности (здесь обрабатывается эффект столкновения, либо создам отдельный класс)

public abstract class Entity{
    @NonNull
    public Shape shape;
    @NonNull
    public PointDouble position;
    @NonNull
    public Movement movement;

    public abstract void setPosition(PointDouble new_position);
    public abstract void setPosition(double new_x, double new_y);

    public abstract void moveTick();
}