package code_files.entities;

import code_files.interfaces.Movable;

import java.awt.*;

// отвечает за положение и поведение сущности (здесь обрабатывается эффект столкновения, либо создам отдельный класс)

public class Entity implements Movable {
    public Point position;
    public double position_x;
    public double position_y;

    public double height, width;
    double cellHeight, cellWidth;

    public Pulse pulse;

    public static class Pulse {
        private final double MAX_PULSE = 20;

        public double pulse_x, pulse_y;

        Pulse(){
            pulse_x = 0;
            pulse_y = 0;
        }

        public void changePulseY(double deltaPulse){
            if (pulse_y + deltaPulse > MAX_PULSE){
                pulse_y = MAX_PULSE;
            }else if (pulse_y + deltaPulse < -MAX_PULSE){
                pulse_y = -MAX_PULSE;
            }else{
                pulse_y += deltaPulse;
            }
        }

        public void changePulseX(double deltaPulse){
            if (pulse_x + deltaPulse > MAX_PULSE){
                pulse_x = MAX_PULSE;
            }else if (pulse_x + deltaPulse < -MAX_PULSE){
                pulse_x = -MAX_PULSE;
            }else{
                pulse_x += deltaPulse;
            }
        }

        public void setPulseX(double newPulseX){
            pulse_x = newPulseX;
        }

        public void setPulseY(double newPulseY){
            pulse_y = newPulseY;
        }

        public double getPulseX(){
            return pulse_x;
        }

        public double getPulseY(){
            return pulse_y;
        }

        public void nullifyMinusX(){
            if (pulse_x < 0) pulse_x = 0;
        }

        public void nullifyPlusX(){
            if (pulse_x > 0) pulse_x = 0;
        }

        public void nullifyMinusY(){
            if (pulse_y < 0) pulse_y = 0;
        }

        public void nullifyPlusY(){
            if (pulse_y > 0) pulse_y = 0;
        }
    }

    public Entity(double _cellHeight, double _cellWidth){
        position_x = -1;
        position_y = -1;

        height = 0.5 * _cellHeight;
        width = 0.5 * _cellWidth;

        cellHeight = _cellHeight;
        cellWidth = _cellWidth;

        pulse = new Pulse();
    }

    public void moveEntity(double x, double y){
        position_x += x;
        position_y += y;
    }

    public void move(){
        position_x += pulse.getPulseX();
        position_y += pulse.getPulseY();
    }

    public void setPosition(double x, double y){
        position_x = x * cellHeight;
        position_y = y * cellWidth;
    }
}