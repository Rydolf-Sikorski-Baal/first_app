package code_files.blocks;

import code_files.interfaces.ChangingSpeed;
import code_files.interfaces.Visible;
import code_files.logic.DrawCanvas;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public enum Blocks implements ChangingSpeed, Visible {
    Air() {
        public boolean isPassable(){return true;}

        @Override
        public double getNewSpeedX(double current_x) {
            return current_x;
        }

        @Override
        public double getNewSpeedY(double current_y) {
            return current_y;
        }

        @Override
        public void drawYourself(javafx.scene.canvas.Canvas canvas, double top_left_corner_x, double top_left_corner_y,
                                 double cellHeight, double cellWidth) {
            javafx.scene.paint.Color color = Color.WHITE;

            DrawCanvas.drawCell(canvas, top_left_corner_y * cellWidth, top_left_corner_x * cellHeight,
                    color, cellWidth, cellHeight);
        }
    },

    Mud() {
        public boolean isPassable(){return false;}

        @Override
        public double getNewSpeedX(double current_x) {
            return 0.3 * current_x;
        }

        @Override
        public double getNewSpeedY(double current_y) {
            return 0.3 * current_y;
        }

        @Override
        public void drawYourself(javafx.scene.canvas.Canvas canvas, double top_left_corner_x, double top_left_corner_y,
                                 double cellHeight, double cellWidth) {
            javafx.scene.paint.Color color = Color.rgb(130, 80, 45);

            DrawCanvas.drawCell(canvas, top_left_corner_y * cellWidth, top_left_corner_x * cellHeight,
                    color, cellWidth, cellHeight);
        }
    },

    Ice() {
        public boolean isPassable(){return false;}

        @Override
        public double getNewSpeedX(double current_x) {
            return current_x;
        }

        @Override
        public double getNewSpeedY(double current_y) {
            return current_y;
        }

        @Override
        public void drawYourself(javafx.scene.canvas.Canvas canvas, double top_left_corner_x, double top_left_corner_y,
                                 double cellHeight, double cellWidth) {
            Color color = Color.LIGHTBLUE;

            DrawCanvas.drawCell(canvas, top_left_corner_y * cellWidth, top_left_corner_x * cellHeight,
                    color, cellWidth, cellHeight);
        }
    },

    ExternalBlock(){
        @Override
        public void drawYourself(Canvas canvas, double top_left_corner_x, double top_left_y, double cellHeight, double cellWidth) {

        }

        @Override
        public double getNewSpeedX(double current_x) {
            return 0;
        }

        @Override
        public double getNewSpeedY(double current_y) {
            return 0;
        }

        @Override
        public boolean isPassable() {
            return false;
        }
    };
    
    public abstract boolean isPassable();
}