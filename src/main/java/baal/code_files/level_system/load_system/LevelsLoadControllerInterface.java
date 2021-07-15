package baal.code_files.level_system.load_system;

import baal.code_files.level_system.level.Level;

import java.io.IOException;
import java.util.Vector;

public interface LevelsLoadControllerInterface {
    public void updateFromThisLevel(Level level, Vector<Level> levelVector) throws IOException;
}
