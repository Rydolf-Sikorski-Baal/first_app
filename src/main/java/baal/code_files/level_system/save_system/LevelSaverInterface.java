package baal.code_files.level_system.save_system;

import baal.code_files.level_system.level.LevelInterface;

public interface LevelSaverInterface {
    void saveLevel(LevelInterface level, String FilePath);
}
