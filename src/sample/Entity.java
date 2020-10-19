package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

import java.awt.*;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

// отвечает за положение и поведение сущности (здесь обрабатывается эффект столкновения, либо создам отдельный класс)

public class Entity {
    public Point position;
    public double position_x;
    public double position_y;

    public double height, width;

    public Entity(double cellHeight, double cellWidth){
        position_x = -1;
        position_y = -1;

        height = 0.5 * cellHeight;
        width = 0.5 * cellWidth;
    }

    Entity(/*double*/ int x, /*double*/ int y){
        position_x = x;
        position_y = y;
    }

    public void moveEntity(double x, double y){
        position_x += x;
        position_y += y;
    }

    public void setPosition(/*double*/ int x, /*double*/ int y){
        position_x = x;
        position_y = y;
    }
}