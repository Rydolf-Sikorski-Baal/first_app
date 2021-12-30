package baal.code_files.level_system.builder_system;

import baal.code_files.level_system.level.LevelInterface;

public interface BuilderInterface {
    BuilderInterface loadJson(String levelFileName);
    BuilderInterface loadTriggers(String levelFileName);

    LevelInterface build();
}
