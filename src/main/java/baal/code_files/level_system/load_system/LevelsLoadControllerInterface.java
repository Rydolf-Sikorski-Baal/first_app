package baal.code_files.level_system.load_system;

import baal.code_files.level_system.level.Level;

import java.io.IOException;
import java.util.Vector;

public interface LevelsLoadControllerInterface {
    LevelLoaderInterface getLevelLoader();

    void updateFromThisLevel(Level level, Vector<Level> levelVector) throws IOException;
    void loadThisLevel(String levelFileName, Vector<Level> levelVector) throws IOException;
}