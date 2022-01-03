package baal.code_files.level_system.builder_system;

import baal.code_files.level_system.level.LevelInterface;

import java.io.IOException;

public interface BuilderInterface {
    BuilderInterface loadJson(String levelFileName) throws IOException;
    BuilderInterface loadTriggers(String levelFileName) throws ClassNotFoundException;

    LevelInterface build();
}
