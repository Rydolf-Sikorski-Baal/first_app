package code_files.level_system;

import code_files.level_system.level.Level;

import java.io.IOException;
import java.util.Vector;

public interface LevelsLoadContrInterface {
    public void updateFromThisLevel(Level level, Vector<Level> levelVector) throws IOException;
}
