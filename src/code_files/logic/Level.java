package code_files.logic;

import code_files.entities_collision.entities_tree.Entity;

import java.io.IOException;
import java.util.Scanner;
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
        Scanner in = new Scanner(new FileReader("src/technical_files/Levels/" + level_file_name));

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