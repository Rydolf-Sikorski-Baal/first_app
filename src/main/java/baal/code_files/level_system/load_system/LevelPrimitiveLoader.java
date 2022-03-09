package baal.code_files.level_system.load_system;

import baal.code_files.blocks.Blocks;
import baal.code_files.graphics_system.LevelCellsSizes;
import baal.code_files.level_system.level.Level;
import baal.code_files.level_system.level.LevelConnections;
import baal.code_files.level_system.level.LevelEntities;
import baal.code_files.level_system.level.LevelSettings;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

@Component
public class LevelPrimitiveLoader implements LevelLoaderInterface{
    @Override
    public Level loadLevel(String levelFileName) throws IOException {
        Scanner in = new Scanner(new FileReader(levelFileName));

        int row = in.nextInt();
        int column = in.nextInt();

        int hero_start_x = in.nextInt();
        int hero_start_y = in.nextInt();

        Blocks[][] levelMap = new Blocks[row + 2][column + 2];

        for (int i = 0; i < row + 2; i++) {
            for (int j = 0; j < column + 2; j++) {
                levelMap[i][j] = Blocks.values()[in.nextInt()];
            }
        }

        LevelSettings levelSettings = new LevelSettings(row, column,
                                                        (double)hero_start_x, (double)hero_start_y,
                                                        0.0, 0.0,
                                                        0.0, 0.0,
                                                        false);
        LevelEntities levelEntities = new LevelEntities();
        LevelConnections levelConnections = new LevelConnections();
        LevelCellsSizes levelCellsSizes = new LevelCellsSizes(50, 50);

        return new Level(levelSettings, levelEntities, levelConnections, levelMap, levelCellsSizes);
    }
}