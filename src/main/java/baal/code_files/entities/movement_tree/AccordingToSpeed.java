package baal.code_files.entities.movement_tree;

import baal.code_files.entities.entities_tree.Entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Getter
@Setter
public class AccordingToSpeed extends Movement{
    private final double MAX_PULSE = 30;

    private double speed_x, speed_y;

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

    @Override
    public void moveBack(Entity entity) {
        double currX = entity.position.getX();
        double currY = entity.position.getY();

        double newX = currX - speed_x;
        double newY = currY - speed_y;

        entity.position.setLocation(newX, newY);
    }
}