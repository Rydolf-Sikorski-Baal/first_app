package code_files.logic;

import code_files.blocks.Blocks;
import code_files.entities.entities_tree.Entity;
import code_files.entities.shape_tree.Rectangle;
import code_files.level_system.level.Level;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// отвечает за всё изображение полотен
// переписать вместе с классом сущности

public class DrawCanvas {
    public static void drawCell(Canvas canvas, double x, double y, Color color, double width, double height){
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        graphicsContext.setFill(color);
        graphicsContext.fillRect();
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
                (curr_level.getBlockByCoords(i, j)).drawYourself();

        for(Entity entity : list_of_entity){
            drawEntity();
        }
    }

    public void drawEntity(Canvas canvas,
                           Entity entity,
                           double cellWidth, double cellHeight){
        Rectangle rectangle = (Rectangle) entity.shape;

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        graphicsContext.setFill(Color.BLUE);
        graphicsContext.fillRect();
    }

    public void clearCanvas(Canvas canvas,
                            double cellWidth, double cellHeight){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect();
    }

    public void drawRedactor(Canvas canvas,
                             double cellWidth, double cellHeight,
                             Redactor redactor){
        clearCanvas(canvas, cellWidth, cclearCanvellHeight);

        int row = redactor.row;
        int column = redactor.column;

        for (int i = 0; i < row + 2; i++)
            for (int j = 0; j < column + 2;j++) {
                Blocks.values()[redactor.curr_state_level[i][j]].drawYourself(canvas,i, j, cellHeight, cellWidth);
            }
    }
}