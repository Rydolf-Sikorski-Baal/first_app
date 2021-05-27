package code_files.logic;

import code_files.blocks.Blocks;
import code_files.entities.entities_tree.Entity;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

// отвечает за структуру и свойства уровней

public class Level {
    private int[][] level;
    public int row, column;
    public double hero_start_x, hero_start_y;
    public double cellHeight, cellWidth;

    public int level_number;

    public static String fileNamePattern = "level_%d";
    public String level_file_name;

    public Entity[] list_of_entities;

    public final int number_of_levels = 2;

    public Level(int number) throws IOException {
        level_number = number;

        getLevelFileName(number);

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

    public void buildLevel(double _cellHeight, double _cellWidth) throws IOException {
        cellHeight = _cellHeight;
        cellWidth = _cellWidth;

        hero_start_x = hero_start_x * cellHeight;
        hero_start_y = hero_start_y * cellWidth;
    }

    public Blocks getBlockByCoords(int x, int y){
        if (x < 0 || x > row + 1 || y < 0 || y > column + 1)
            return Blocks.ExternalBlock;
        return Blocks.values()[level[x][y]];
    }
}