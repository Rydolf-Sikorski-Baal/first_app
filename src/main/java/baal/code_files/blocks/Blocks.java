package baal.code_files.blocks;

import baal.code_files.interfaces.BlocksVis;
import baal.code_files.interfaces.ChangingSpeed;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public enum Blocks implements ChangingSpeed, BlocksVis {
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
            GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

            graphicsContext.setFill(Color.WHITE);
            graphicsContext.fillRect(top_left_corner_y * cellWidth, top_left_corner_x * cellHeight,
                    cellWidth, cellHeight);
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

            GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

            graphicsContext.setFill(color);
            graphicsContext.fillRect(top_left_corner_y * cellWidth, top_left_corner_x * cellHeight,
                    cellWidth, cellHeight);
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

            GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

            graphicsContext.setFill(color);
            graphicsContext.fillRect(top_left_corner_y * cellWidth, top_left_corner_x * cellHeight,
                    cellWidth, cellHeight);
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