package baal.code_files.entity_movement;

import baal.code_files.PointDouble;
import baal.code_files.blocks.Blocks;
import baal.code_files.entities.entities_tree.Entity;
import baal.code_files.entities.movement_tree.AccordingToSpeed;
import baal.code_files.entities.movement_tree.Movement;
import baal.code_files.entities.shape_tree.Rectangle;
import baal.code_files.level_system.level.Level;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.function.Consumer;

import static java.lang.Math.abs;

@Component
public class EntityMovement {
    private final Collision collision;
    private Map<String, Consumer<Entity>> moveTickFunctions;

    private Level level;

    class ClassPair {
        Class<?> first, second;

        ClassPair(Object firstValue, Object secondValue) {
            first = firstValue.getClass();
            second = secondValue.getClass();
        }
    }

    private EntityMovement(@Qualifier("collision")
                                   Collision collision){
        this.collision = collision;

        moveTickFunctions = new HashMap<>();

        moveTickFunctions.put("class baal.code_files.entities.shape_tree.Rectangle_class baal.code_files.entities.movement_tree.AccordingToSpeed",
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

        EntityTypeInformation(baal.code_files.entities.shape_tree.Shape shape, Movement movement){
            shapeClass = shape.getClass();
            movementClass = movement.getClass();
        }
    }

    //прямоугольник по скорости
    private final int INFINITY = 1000 * 1000 * 1000;
    private final double epsilon = 3;

    private enum Side{
        None, Top, Bottom, Left, Right
    }
    @AllArgsConstructor
    @Getter
    @Setter
    class CollideInformation{
        private double requiredTime;
        private Side side;
    }

    private final double tickTime = 100;
    private double wastedTime;
    private void moveRectangleAccordingToSpeed(Entity entity){
        wastedTime = 0;
        while(abs(tickTime - wastedTime) > epsilon
                && ((((AccordingToSpeed)entity.movement).getSpeed_x() != 0) || (((AccordingToSpeed)entity.movement).getSpeed_y() != 0))) {

            PositionInfo positionInfo = collision.getPositionInfo(entity, level.getLevelCellsSizes().getHeight(),
                    level.getLevelCellsSizes().getWidth());
            Vector<Point> currentPosition = positionInfo.getCurrentInnerCoords();
            Vector<Point> nextPosition = positionInfo.getNextTickInnerCoords();

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
                                blockX, blockY, level.getLevelCellsSizes().getHeight(),
                                level.getLevelCellsSizes().getWidth());

                        if (collideInformation.getRequiredTime() < nextCollide.getRequiredTime()) {
                            nextCollide.setRequiredTime(collideInformation.getRequiredTime());
                            nextCollide.setSide(collideInformation.getSide());
                        }
                    }
                }

            if ((wastedTime + nextCollide.getRequiredTime() * tickTime) - tickTime > epsilon) {
                nextCollide.setSide(Side.None);
                nextCollide.setRequiredTime((tickTime - wastedTime) / tickTime);
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
        double koeff = 0.75;

        double speedX = Math.abs(((AccordingToSpeed) entity.movement).getSpeed_x());
        double speedY = Math.abs(((AccordingToSpeed)entity.movement).getSpeed_y());

        baal.code_files.entities.shape_tree.Rectangle rectangle = (Rectangle) entity.shape;

        PointDouble entityTopLeftCorner = entity.position;
        PointDouble entityBottomRightCorner = new PointDouble(entityTopLeftCorner.getX() + rectangle.x_size,
                entityTopLeftCorner.getY() + rectangle.y_size);

        PointDouble blockTopLeftCorner = new PointDouble(blockX,
                blockY);
        PointDouble blockBottomRightCorner = new PointDouble(blockX + 1,
                blockY + 1);

        Point direction = new Point(0, 0);
        if (blockBottomRightCorner.getX() < entityTopLeftCorner.getX()) direction.x = -1;
        if (blockTopLeftCorner.getX() > entityBottomRightCorner.getX()) direction.x = +1;

        if (blockBottomRightCorner.getY() < entityTopLeftCorner.getY()) direction.y = -1;
        if (blockTopLeftCorner.getY() > entityBottomRightCorner.getY()) direction.y = +1;

        double timeX = tickTime, timeY = tickTime;
        if (direction.x < 0)
            timeX = (entityTopLeftCorner.getX() - blockBottomRightCorner.getX()) / speedX;
        if (direction.x > 0)
            timeX = (blockTopLeftCorner.getX() - entityBottomRightCorner.getX()) / speedX;

        if (direction.y < 0)
            timeY = (entityTopLeftCorner.getY() - blockBottomRightCorner.getY()) / speedY;
        if (direction.y > 0)
            timeY = (blockTopLeftCorner.getY() - entityBottomRightCorner.getY()) / speedY;

        double res = Math.min(timeX, timeY);

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

        PointDouble newEntityTopLeftCorner = new PointDouble(entity.position.getX() + res * ((AccordingToSpeed) entity.movement).getSpeed_x(),
                entity.position.getY() + res * ((AccordingToSpeed) entity.movement).getSpeed_y());
        PointDouble newEntityBottomRightCorner = new PointDouble(newEntityTopLeftCorner.getX() + rectangle.x_size,
                newEntityTopLeftCorner.getY() + rectangle.y_size);

        PointDouble interceptionTopLeft = new PointDouble(Math.max(newEntityTopLeftCorner.getX(), blockTopLeftCorner.getX()),
                Math.max(newEntityTopLeftCorner.getY(), blockTopLeftCorner.getY()));
        PointDouble interceptionBottomRight = new PointDouble(Math.min(newEntityBottomRightCorner.getX(), blockBottomRightCorner.getX()),
                Math.min(newEntityBottomRightCorner.getY(), blockBottomRightCorner.getY()));

        if ((interceptionBottomRight.getX() - interceptionTopLeft.getX() < -0.01) ||
            (interceptionBottomRight.getY() - interceptionTopLeft.getY() < -0.01)) {
            res = 1;
            side = Side.None;
        }

        return new CollideInformation((100 * res + epsilon < 100 ? res * koeff : 1), side);
    }

    private void moveCountingCollision(Entity entity, CollideInformation collideInformation){
        double newX = entity.position.getX() + ((AccordingToSpeed)entity.movement).getSpeed_x() * (collideInformation.getRequiredTime());
        double newY = entity.position.getY() + ((AccordingToSpeed)entity.movement).getSpeed_y() * (collideInformation.getRequiredTime());
        entity.position.setLocation(newX, newY);

        if (collideInformation.getSide().equals(Side.Top)){
            ((AccordingToSpeed) entity.movement).setSpeed_x(0);
        }

        if (collideInformation.getSide().equals(Side.Bottom)){
            ((AccordingToSpeed) entity.movement).setSpeed_x(0);
        }

        if (collideInformation.getSide().equals(Side.Left)){
            ((AccordingToSpeed) entity.movement).setSpeed_y(0);
        }

        if (collideInformation.getSide().equals(Side.Right)){
            ((AccordingToSpeed) entity.movement).setSpeed_y(0);
        }
    }
}