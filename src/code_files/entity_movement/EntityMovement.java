package code_files.entity_movement;

import code_files.PointDouble;
import code_files.blocks.Blocks;
import code_files.entities.entities_tree.Entity;
import code_files.entities.movement_tree.AccordingToSpeed;
import code_files.entities.movement_tree.Movement;
import code_files.entities.shape_tree.Rectangle;
import code_files.entities.shape_tree.Shape;
import code_files.logic.Level;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.naming.OperationNotSupportedException;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.function.Consumer;

public class EntityMovement {
    Collision collision;
    double cellHeight, cellWeight;
    Map<String, Consumer<Entity>> moveTickFunctions;

    Level level;

    public EntityMovement(double _cellHeight, double _cellWidth){
        cellHeight = _cellHeight;
        cellWeight = _cellWidth;
        collision = new Collision(cellHeight, cellWeight);

        moveTickFunctions = new HashMap<>();

        moveTickFunctions.put("class code_files.entities.shape_tree.Rectangle_class code_files.entities.movement_tree.AccordingToSpeed",
                this::moveRectangleAccordingToSpeed);
    }

    public void moveTick(Entity entity, Level _level){
        level = _level;

        String entityTypeInformation = (entity.shape.getClass()).toString() + "_" +
                (entity.movement.getClass()).toString();

        moveTickFunctions.get(entityTypeInformation).accept(entity);
    }

    public static class EntityTypeInformation {
        private Class<?> shapeClass;
        private Class<?> movementClass;

        EntityTypeInformation(Entity entity){
            shapeClass = entity.shape.getClass();
            movementClass = entity.movement.getClass();
        }

        EntityTypeInformation(Shape shape, Movement movement){
            shapeClass = shape.getClass();
            movementClass = movement.getClass();
        }
    }

    //прямоугольник по скорости
    final int INFINITY = 1000 * 1000 * 1000;
    final double epsilon = 5;

    enum Side{
        None, Top, Bottom, Left, Right
    }
    @AllArgsConstructor
    @Getter
    @Setter
    class CollideInformation{
        private double requiredTime;
        private Side side;
    }

    final double tickTime = 100;
    double wastedTime;
    private void moveRectangleAccordingToSpeed(Entity entity){
        wastedTime = 0;
        while(Math.abs(tickTime - wastedTime) > epsilon) {

            PositionInfo positionInfo = collision.getPositionInfo(entity, level.cellHeight, level.cellWidth);
            Vector<Point> currentPosition = positionInfo.getCurrentInnerCoords();
            Vector<Point> nextPosition = positionInfo.getNextInnerCoords();

            int minimumX = INFINITY;
            int maximumX = -INFINITY;

            int minimumY = INFINITY;
            int maximumY = -INFINITY;

            for (Point point : currentPosition) {
                minimumX = Math.min(point.x, minimumX);
                maximumX = Math.max(point.x, maximumX);

                minimumY = Math.min(point.y, minimumY);
                maximumY = Math.max(point.y, maximumY);
            }
            for (Point point : nextPosition) {
                minimumX = Math.min(point.x, minimumX);
                maximumX = Math.max(point.x, maximumX);

                minimumY = Math.min(point.y, minimumY);
                maximumY = Math.max(point.y, maximumY);
            }

            CollideInformation nextCollide = new CollideInformation(INFINITY, Side.None);
            for (int blockX = minimumX; blockX <= maximumX; blockX++)
                for (int blockY = minimumY; blockY <= maximumY; blockY++) {
                    Blocks block = level.getBlockByCoords(blockX, blockY);

                    if (!block.isPassable()) {
                        CollideInformation collideInformation = getTimeToBlock_RectangleAccordingToSpeed(entity,
                                blockX, blockY, level.cellHeight, level.cellWidth);

                        if (collideInformation.getRequiredTime() < nextCollide.getRequiredTime()) {
                            nextCollide.setRequiredTime(collideInformation.getRequiredTime());
                            nextCollide.setSide(collideInformation.getSide());
                        }
                    }
                }

            if ((wastedTime + nextCollide.getRequiredTime() * tickTime) - tickTime > epsilon) {
                nextCollide.setSide(Side.None);
                nextCollide.setRequiredTime((tickTime - wastedTime)/tickTime);
                wastedTime = tickTime;
            }else {
                wastedTime += nextCollide.getRequiredTime() * tickTime;
            }

            moveCountingCollision(entity, nextCollide);
        }
    }

    private CollideInformation getTimeToBlock_RectangleAccordingToSpeed(Entity entity,
                                                            int blockX, int blockY,
                                                            double cellHeight, double cellWidth){
        double speedX = ((AccordingToSpeed)entity.movement).speed_x;
        double speedY = ((AccordingToSpeed)entity.movement).speed_y;

        Rectangle rectangle = (Rectangle) entity.shape;

        PointDouble entityTopLeftCorner = entity.position;
        PointDouble entityBottomRightCorner = new PointDouble(entityTopLeftCorner.getX() + rectangle.x_size,
                entityTopLeftCorner.getY() + rectangle.y_size);

        PointDouble blockTopLeftCorner = new PointDouble(blockX * cellHeight,
                blockY * cellWidth);
        PointDouble blockBottomRightCorner = new PointDouble(blockX * cellHeight + cellHeight,
                blockY * cellWidth + cellWidth);

        Point direction = new Point(0, 0);
        if (blockBottomRightCorner.getX() <= entityTopLeftCorner.getX()) direction.x = -1;
        if (blockTopLeftCorner.getX() >= entityBottomRightCorner.getX()) direction.x = +1;

        if (blockBottomRightCorner.getY() <= entityTopLeftCorner.getY()) direction.y = -1;
        if (blockTopLeftCorner.getY() >= entityBottomRightCorner.getY()) direction.y = +1;

        double timeX = tickTime, timeY = tickTime;
        if (direction.x < 0)
            timeX = (blockBottomRightCorner.getX() - entityTopLeftCorner.getX()) / speedX;
        if (direction.x > 0)
            timeX = (entityBottomRightCorner.getX() - blockTopLeftCorner.getX()) / speedX;

        if (direction.y < 0)
            timeY = (entityTopLeftCorner.getY() - blockBottomRightCorner.getY()) /speedY;
        if (direction.y > 0)
            timeY = (blockTopLeftCorner.getY() - entityBottomRightCorner.getY()) / speedY;

        double res = Math.min(timeX, timeY);

        PointDouble newEntityTopLeftCorner = new PointDouble(entity.position.getX() + res * ((AccordingToSpeed) entity.movement).speed_x,
                entity.position.getY() + res*((AccordingToSpeed) entity.movement).speed_y);
        PointDouble newEntityBottomRightCorner = new PointDouble(newEntityTopLeftCorner.getX() + rectangle.x_size,
                newEntityTopLeftCorner.getY() + rectangle.y_size);

        PointDouble interceptionTopLeft = new PointDouble(Math.max(newEntityTopLeftCorner.getX(), blockTopLeftCorner.getX()) - epsilon,
                Math.max(newEntityTopLeftCorner.getY(), blockTopLeftCorner.getY()) - epsilon);
        PointDouble interceptionBottomRight = new PointDouble(Math.min(newEntityBottomRightCorner.getX(), blockBottomRightCorner.getX()) + epsilon,
                Math.min(newEntityBottomRightCorner.getY(), blockBottomRightCorner.getY()) + epsilon);

        if ((interceptionTopLeft.getX() > interceptionBottomRight.getX()) ||
                (interceptionTopLeft.getY() > interceptionBottomRight.getY()))
                    res = tickTime;

        Side side = Side.None;
        if (timeX < timeY){
            if (direction.x < 0) side = Side.Top;
            if (direction.x > 0) side = Side.Bottom;
        }else{
            if (direction.y < 0) side = Side.Left;
            if (direction.y > 0) side = Side.Right;
        }
        if (direction.x == 0 && direction.y == 0){
            side = Side.Bottom;
            res = 0;
        }
        return new CollideInformation(res, side);
    }

    private void moveCountingCollision(Entity entity, CollideInformation collideInformation){
        double newX = entity.position.getX() + ((AccordingToSpeed)entity.movement).speed_x * collideInformation.getRequiredTime();
        double newY = entity.position.getY() + ((AccordingToSpeed)entity.movement).speed_y * collideInformation.getRequiredTime();
        entity.position.setLocation(newX, newY);

        if (collideInformation.getSide().equals(Side.Top)){
            ((AccordingToSpeed) entity.movement).speed_x = 0;
        }

        if (collideInformation.getSide().equals(Side.Bottom)){
            ((AccordingToSpeed) entity.movement).speed_x = 0;
        }

        if (collideInformation.getSide().equals(Side.Left)){
            ((AccordingToSpeed) entity.movement).speed_y = 0;
        }

        if (collideInformation.getSide().equals(Side.Right)){
            ((AccordingToSpeed) entity.movement).speed_y = 0;
        }
    }
}