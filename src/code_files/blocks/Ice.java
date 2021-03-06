package code_files.blocks;

import code_files.entities.Entity;
import code_files.logic.DrawCanvas;
import javafx.scene.paint.Color;
import javafx.scene.Scene;

import java.awt.*;

public class Ice extends Block {
    @Override
    public void effect(Entity entity) {

    }

    @Override
    public double getNewSpeedX(double current_x) {
        return current_x;
    }

    @Override
    public double getNewSpeedY(double current_y) {
        return current_y;
    }

    @Override
    public String getName() {
        return "Ice";
    }

    @Override
    public void drawYourself(javafx.scene.canvas.Canvas canvas, double top_left_corner_x, double top_left_corner_y,
                             double cellHeight, double cellWidth) {
        Color color = Color.LIGHTBLUE;

        DrawCanvas.drawCell(canvas, top_left_corner_y * cellWidth, top_left_corner_x * cellHeight,
                            color, cellWidth, cellHeight);
    }
}
