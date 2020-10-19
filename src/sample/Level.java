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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.FileReader;

// отвечает за структуру и свойства уровней

public class Level {
    public int[][] level;
    public int row, column;
    public /*double*/ int hero_start_x, hero_start_y;

    public int level_number;

    public static String fileNamePattern = "level_%d";
    public String level_file_name;

    public Entity[] list_of_entities;

    public final int number_of_levels = 2;

    //0 - пустое пространство
    //1 - обычный блок
    //2 - киллзона
    //3 - выход с уровня

    public Level(int number) throws IOException {
        level_number = number;

        getLevelFileName(number);

        buildLevel();
    }

    public void getLevelFileName(int number){
        level_file_name = String.format(fileNamePattern, number);
    }

    public boolean IsPassable(int x, int y){
        int curr_block = level[x][y];

        if (curr_block == 0) return true;
        if (curr_block == 2) return true;
        if (curr_block == 3) return true;

        if (curr_block == 1) return false;

        return false;
    }

    public void buildLevel() throws IOException {
        Scanner in = new Scanner(new FileReader("src/Levels_structure/" + level_file_name));

        row = in.nextInt();
        column = in.nextInt();

        hero_start_x = in.nextInt();
        hero_start_y = in.nextInt();

        level = new int[row + 2][column + 2];

        for (int i = 0; i < row + 2; i++)
            for (int j = 0; j < column + 2; j++)
                level[i][j] = in.nextInt();
    }
}