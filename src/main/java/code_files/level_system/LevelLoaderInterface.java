package code_files.level_system;

import code_files.level_system.level.Level;

import java.io.IOException;

public interface LevelLoaderInterface {
    public Level loadLevel(String levelFileName) throws IOException;
}
