package baal.code_files.level_system.builder_system;

import baal.code_files.entities.entities_tree.Entity;
import baal.code_files.level_system.event.Event;

import java.util.Map;

public interface EntityBuilderDirectorInterface {
    Entity build(Map<String, Object> map);
}
