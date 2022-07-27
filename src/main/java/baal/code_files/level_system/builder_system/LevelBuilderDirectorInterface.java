package baal.code_files.level_system.builder_system;

import baal.code_files.level_system.level.LevelInterface;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public interface LevelBuilderDirectorInterface {
    LevelInterface build(String levelFileName) throws ClassNotFoundException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
}
