package baal.code_files.level_system.load_system;

import baal.code_files.level_system.level.Level;
import baal.code_files.level_system.level.LevelInterface;

import java.io.IOException;
import java.util.Vector;

public interface LevelsLoadControllerInterface {
    LevelLoaderInterface getLevelLoader();

    void updateFromThisLevel(LevelInterface level, Vector<LevelInterface> levelVector) throws IOException;
    void loadThisLevel(String levelFileName, Vector<Level> levelVector) throws IOException;
}