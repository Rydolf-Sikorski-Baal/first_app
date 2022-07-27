package baal.code_files.level_system.builder_system;

import baal.code_files.level_system.level.LevelInterface;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public interface BuilderInterface {
    BuilderInterface loadJson(String levelFileName) throws IOException;
    BuilderInterface loadTriggers(String levelFileName) throws ClassNotFoundException;
    BuilderInterface loadEntities(String levelFileName) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    LevelInterface build();
}