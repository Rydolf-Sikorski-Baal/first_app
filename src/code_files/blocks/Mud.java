package code_files.blocks;

import code_files.entities.Entity;
import code_files.logic.DrawCanvas;
import javafx.scene.paint.Color;

import java.awt.*;

public class Mud extends Block{
    static double modifier = 0.3;

    @Override
    public void effect(Entity entity) {

    }

    @Override
    public double getNewSpeedX(double current_x) {
        return modifier * current_x;
    }

    @Override
    public double getNewSpeedY(double current_y) {
        return modifier * current_y;
    }


    @Override
    public String getName() {
        return "Mud";
    }

    @Override
    public void drawYourself(javafx.scene.canvas.Canvas canvas, double top_left_corner_x, double top_left_corner_y,
                             double cellHeight, double cellWidth) {
        javafx.scene.paint.Color color = Color.rgb(130, 80, 45);

        DrawCanvas.drawCell(canvas, top_left_corner_y * cellWidth, top_left_corner_x * cellHeight,
                color, cellWidth, cellHeight);
    }
}
