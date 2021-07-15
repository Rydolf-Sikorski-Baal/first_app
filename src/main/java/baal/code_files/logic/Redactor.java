package baal.code_files.logic;

import java.io.*;
import java.util.Scanner;

// отвечает за редакцию уровней

public class Redactor {
    //класс редактора создаётся после выбора уровня

    public int[][] curr_state_level;
    public int level_number;

    public static String fileNamePattern = "level_%d";
    public String level_file_name;

    public int row, column;
    public int hero_position_x, hero_position_y;

    public Redactor(int number) throws IOException {
        getLevelFileName(number);

        File level_file = new File("src/technical_files/Levels/" + level_file_name);

        if (level_file.createNewFile()){
            row = 0;
            column = 0;

            curr_state_level = new int[row + 2][column + 2];
            curr_state_level[0][0] = 1;
            curr_state_level[0][1] = 1;
            curr_state_level[1][0] = 1;
            curr_state_level[1][1] = 1;

            hero_position_x = 0;
            hero_position_y = 0;
        }else{
            Scanner in = new Scanner(new FileReader("src/technical_files/Levels/" + level_file_name));

            row = in.nextInt();
            column = in.nextInt();

            curr_state_level = new int[row + 2][column + 2];

            hero_position_x = in.nextInt();
            hero_position_y = in.nextInt();

            for (int i = 0; i < row + 2; i++)
                for (int j = 0; j < column + 2; j++)
                    curr_state_level[i][j] = in.nextInt();
        }

    }

    public void getLevelFileName(int number){
        level_file_name = String.format(fileNamePattern, number);
    }

    public void changeCell(int x, int y, int new_type){
        curr_state_level[x][y] = new_type;
    }

    public void setLevel() throws FileNotFoundException {
        try (PrintWriter file = new PrintWriter("src/technical_files/Levels/" + level_file_name)) {
            file.print(row + " " + column + '\n');
            file.print(hero_position_x + " " + hero_position_y + '\n');

            for (int i = 0; i < row + 2; i++) {
                for (int j = 0; j < column + 2; j++)
                    file.print(curr_state_level[i][j] + " ");
                file.println();
            }
        }
    }
}