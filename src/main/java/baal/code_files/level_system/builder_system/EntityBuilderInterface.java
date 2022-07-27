package baal.code_files.level_system.builder_system;

import baal.code_files.entities.entities_tree.Entity;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public interface EntityBuilderInterface {
    Entity build(Map<String, Object> map) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
}
