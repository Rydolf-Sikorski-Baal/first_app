package code_files.level_system;

import lombok.NonNull;

import java.util.Vector;

public class Chapter {
    @NonNull
    private Vector<Level> levelVector;
    @NonNull
    private LevelsLoadController levelsLoadController;
}
