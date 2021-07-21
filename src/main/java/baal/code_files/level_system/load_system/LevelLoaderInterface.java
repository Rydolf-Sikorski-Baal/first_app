package baal.code_files.level_system.load_system;

import baal.code_files.level_system.level.Level;

import java.io.IOException;

public interface LevelLoaderInterface {
    Level loadLevel(String levelFileName) throws IOException;
}
