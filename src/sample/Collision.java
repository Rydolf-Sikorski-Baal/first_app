package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import java.math.*;
import java.util.Vector;

import static java.lang.Math.*;

// описывет столкновение объектов, но не его эффект

public class Collision {
    private static final double epsilon = 1e-6;

    public static double getAreaIntersection(double x1_up, double y1_left,
                                       double x1_down, double y1_right,
                                       double x2_up, double y2_left,
                                       double x2_down, double y2_right){
        double y_leftPoint = max(y1_left, y2_left);
        double x_leftPoint = max(x1_up, x2_up);

        double y_rightPoint = min(y1_right, y2_right);
        double x_rightPoint = min(x1_down, x2_down);

        double y_length = max(y_rightPoint - y_leftPoint, 0);
        double x_length = max(x_rightPoint - x_leftPoint, 0);

        return y_length * x_length;
    }

    public static Vector<int[]> getListOfPositions(double x, double y,
                                      double cellWeight, double cellHeight,
                                      Entity entity){
        Vector<int[]> listOfPositions = new Vector<int[]>(0);

        int[] step = {-1, 0, 1};

        int approximate_position_x = (int)(x / cellHeight);
        int approximate_position_y = (int)(y / cellWeight);

        for (int i = 0; i < step.length; i++)
            for (int j = 0; j < step.length; j++){
                int pretend_position_x = approximate_position_x + step[i];
                int pretend_position_y = approximate_position_y + step[j];

                double pretend_x_double = pretend_position_x * cellHeight;
                double pretend_y_double = pretend_position_y * cellWeight;

                double areaInterseptionOfPretendent = getAreaIntersection(x, y,
                                                x + entity.height,
                                                y + entity.width,
                                                        pretend_x_double, pretend_y_double,
                                                pretend_x_double + cellHeight,
                                                pretend_y_double + cellWeight);

                if (areaInterseptionOfPretendent > epsilon) {
                    int[] curr_coord = {pretend_position_x, pretend_position_y};
                    listOfPositions.add(curr_coord);
                }
            }

        return listOfPositions;
    }

    public static boolean check(double cellWeight, double cellHeight,
                                Entity entity, Level level,
                                int delta_x, int delta_y){
        double entity_x = entity.position_x;
        double entity_y = entity.position_y;

        Vector<int[]> listOfPosisions = getListOfPositions(entity_x, entity_y, cellWeight, cellHeight, entity);

        for (int i = 0; i < listOfPosisions.size(); i++){
            int curr_x = listOfPosisions.get(i)[0];
            int curr_y = listOfPosisions.get(i)[1];

            if (level.level[curr_x + delta_x][curr_y + delta_y] == 1) return true;
        }

        return false;
    }
    
    public static boolean IsOnSurface(double cellWeight, double cellHeight,
                                      Entity entity, Level level){
        return check(cellWeight, cellHeight,
                entity, level, 1, 0)
                && (entity.position_x + entity.height) % cellHeight < epsilon;
    }

    public static boolean IsBehindLeftWall(double cellWeight, double cellHeight,
                                   Entity entity, Level level){
        return check(cellWeight, cellHeight,
                entity, level, 0, -1)
                && (entity.position_y % cellWeight) < epsilon;
    }

    public static boolean IsBehindRightWall(double cellWeight, double cellHeight,
                                            Entity entity, Level level){
        return check(cellWeight, cellHeight,
                entity, level, 0, 1)
                && abs((entity.position_y + entity.width) % cellWeight) < epsilon;
    }

    public static boolean IsUnderRoof(double cellWeight, double cellHeight,
                                      Entity entity, Level level){
        return check(cellWeight, cellHeight,
                entity, level, -1, 0)
                && (entity.position_x % cellHeight) < epsilon;
    }
}