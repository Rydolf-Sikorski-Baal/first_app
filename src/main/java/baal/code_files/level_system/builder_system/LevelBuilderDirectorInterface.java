package baal.code_files.level_system.builder_system;

import baal.code_files.level_system.level.LevelInterface;

public interface LevelBuilderDirectorInterface {
    LevelInterface build(String levelFileName);
}
