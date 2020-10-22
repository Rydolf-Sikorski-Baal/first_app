package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

// отвечает за всё изображение полотен
// переписать вместе с классом сущности

public class DrawCanvas {
    static Color COLOR_WALL = Color.rgb(130, 80, 45);
    static Color COLOR_KILLZONE = Color.RED;
    static Color COLOR_END_LEVEL = Color.BLACK;
    static Color COLOR_HERO = Color.BLUE;
    static Color COLOR_EMPTY = Color.WHITE;

    public static DrawCanvas draw = new DrawCanvas();

    public void drawCell(Canvas canvas, double x, double y, Color color, double width, double height){
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        graphicsContext.setFill(color);
        graphicsContext.fillRect(x, y, width, height);
    }

    public void drawLevel(
            Canvas canvas,
            double cellWidth, double cellHeight,
            Level curr_level,
            Entity[] list_of_entity){

        clearCanvas(canvas, cellWidth, cellHeight);

        int row = curr_level.row;
        int column = curr_level.column;

        for (int i = 0; i < row + 2; i++)
            for (int j = 0; j < column + 2;j++) {
                if (curr_level.level[i][j] == 1)
                    drawCell(canvas, j * cellWidth, i * cellHeight, COLOR_WALL, cellWidth, cellHeight);

                if (curr_level.level[i][j] == 2)
                    drawCell(canvas, j * cellWidth, i * cellHeight, COLOR_KILLZONE, cellWidth, cellHeight);

                if (curr_level.level[i][j] == 3)
                    drawCell(canvas, j * cellWidth, i * cellHeight, COLOR_END_LEVEL, cellWidth, cellHeight);
            }

        for(Entity entity : list_of_entity){
            drawEntity(canvas, curr_level, entity, cellWidth, cellHeight);
        }
    }

    public void drawEntity(Canvas canvas,
                           Level curr_level,
                           Entity entity,
                           double cellWidth, double cellHeight){
        double delta_y = cellWidth - entity.width;
        double delta_x = cellHeight - entity.height;

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        graphicsContext.setFill(Color.BLUE);
        graphicsContext.fillRect(entity.position_y,entity.position_x,
                                 entity.width, entity.height);
    }

    public void clearCanvas(Canvas canvas,
                            double cellWidth, double cellHeight){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void drawRedactor(Canvas canvas,
                             double cellWidth, double cellHeight,
                             Redactor redactor){
        clearCanvas(canvas, cellWidth, cellHeight);

        int row = redactor.row;
        int column = redactor.column;

        for (int i = 0; i < row + 2; i++)
            for (int j = 0; j < column + 2;j++) {
                if (redactor.curr_state_level[i][j] == 1)
                    drawCell(canvas, j * cellWidth, i * cellHeight, COLOR_WALL, cellWidth, cellHeight);

                if (redactor.curr_state_level[i][j] == 2)
                    drawCell(canvas, j * cellWidth, i * cellHeight, COLOR_KILLZONE, cellWidth, cellHeight);

                if (redactor.curr_state_level[i][j] == 3)
                    drawCell(canvas, j * cellWidth, i * cellHeight, COLOR_END_LEVEL, cellWidth, cellHeight);
            }
    }
}