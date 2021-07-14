package code_files.level_system;

import code_files.level_system.level.Level;
import lombok.NonNull;

import java.util.Vector;

public class Chapter {
    @NonNull
    private Vector<Level> levelVector;
    @NonNull
    private LevelsLoadController levelsLoadController;

    private Level currentLevel;
}
