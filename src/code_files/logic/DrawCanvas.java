package code_files.logic;

import code_files.entities.Entity;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// отвечает за всё изображение полотен
// переписать вместе с классом сущности

public class DrawCanvas {
    static Color COLOR_WALL = Color.rgb(130, 80, 45);
    static Color COLOR_KILLZONE = Color.RED;
    static Color COLOR_END_LEVEL = Color.BLACK;
    static Color COLOR_HERO = Color.BLUE;
    static Color COLOR_EMPTY = Color.WHITE;
    static Color COLOR_ICE = Color.LIGHTBLUE;

    public static DrawCanvas draw = new DrawCanvas();

    public static void drawCell(Canvas canvas, double x, double y, Color color, double width, double height){
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
            for (int j = 0; j < column + 2;j++)
                Blocks.values()[curr_level.level[i][j]].drawYourself(canvas,i, j, cellHeight, cellWidth);

        for(Entity entity : list_of_entity){
            drawEntity(canvas, entity, cellWidth, cellHeight);
        }
    }

    public void drawEntity(Canvas canvas,
                           Entity entity,
                           double cellWidth, double cellHeight){
        double delta_y = cellWidth - entity.width;
        double delta_x = cellHeight - entity.height;

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        graphicsContext.setFill(Color.BLUE);
        graphicsContext.fillRect(entity.position_y,
                                entity.position_x,
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
                Blocks.values()[redactor.curr_state_level[i][j]].drawYourself(canvas,i, j, cellHeight, cellWidth);
            }
    }
}