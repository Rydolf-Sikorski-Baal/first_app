package baal.code_files.level_system.builder_system;

import baal.code_files.entities.entities_tree.Entity;

import java.util.Map;

public interface EntityBuilderInterface {
    Entity build(Map<String, Object> map);
}
