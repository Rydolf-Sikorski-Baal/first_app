package baal.code_files.entities.entities_tree;

import baal.code_files.PointDouble;
import baal.code_files.entities.controllability_tree.HeroControls;
import baal.code_files.entities.movement_tree.AccordingToSpeed;
import baal.code_files.entities.shape_tree.Rectangle;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

public class Hero extends Entity {
    @Override
    public void setPosition(PointDouble new_position) {
        position = new_position;
    }
    @Override
    public void setPosition(double new_x, double new_y) {
        PointDouble newPosition = new PointDouble(new_x, new_y);
        setPosition(newPosition);
    }

    @Override
    public void moveTick() {
        movement.moveTick(this);
    }

    @Override
    public void moveBack() {
        movement.moveBack(this);
    }

    public Hero(Rectangle rectangle, AccordingToSpeed movement, HeroControls heroControls){
        this.shape = rectangle;
        this.movement = movement;
        this.controllability = heroControls;
    }

    public void connect(){
        this.controllability.setEntity(this);
    }

    @Override
    public void drawYourself(Canvas canvas, double cellHeight, double cellWidth) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        double left_top_x = this.position.getX();
        double left_top_y = this.position.getY();
        
        graphicsContext.setFill(Color.RED);
        
        graphicsContext.fillRect(left_top_y, left_top_x,
                ((Rectangle)shape).getX_size(), ((Rectangle)shape).getY_size());
    }
}