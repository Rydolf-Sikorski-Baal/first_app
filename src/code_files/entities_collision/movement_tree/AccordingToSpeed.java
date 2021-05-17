package code_files.entities_collision.movement_tree;

import code_files.entities_collision.PointDouble;
import code_files.entities_collision.entities_tree.Entity;

import java.awt.geom.Point2D;

public class AccordingToSpeed extends Movement{
    private final double MAX_PULSE = 4;

    public double speed_x, speed_y;

    public void changeSpeedY(double deltaSpeed){
        speed_y += deltaSpeed;

        if (speed_y > MAX_PULSE)
            speed_y = MAX_PULSE;
        if (speed_y < -MAX_PULSE)
            speed_y = -MAX_PULSE;
    }
    public void changeSpeedX(double deltaSpeed){
        speed_x += deltaSpeed;

        if (speed_x > MAX_PULSE)
            speed_x = MAX_PULSE;
        if (speed_x < -MAX_PULSE)
            speed_x = -MAX_PULSE;
    }

    public void setSpeedX(double newSpeedX){
        speed_x = newSpeedX;
    }
    public void setSpeedY(double newSpeedY){
        speed_y = newSpeedY;
    }

    public double getSpeedX(){
        return speed_x;
    }
    public double getSpeedY(){
        return speed_y;
    }

    public void nullifyMinusX(){
        if (speed_x < 0) speed_x = 0;
    }
    public void nullifyPlusX(){
        if (speed_y > 0) speed_y = 0;
    }
    public void nullifyMinusY(){
        if (speed_y < 0) speed_y = 0;
    }
    public void nullifyPlusY(){
        if (speed_y > 0) speed_y = 0;
    }

    @Override
    public void moveTick(Entity entity) {
        double currX = entity.position.getX();
        double currY = entity.position.getY();

        double newX = currX + speed_x;
        double newY = currY + speed_y;

        entity.position.setLocation(newX, newY);
    }
}