package baal.code_files.level_system.builder_system;

import baal.code_files.entities.entities_tree.Entity;

import java.util.Map;

public class EntityBuilderDirector implements EntityBuilderDirectorInterface{
    private final EntityBuilderInterface entityBuilder;
    @Override
    public Entity build(Map<String, Object> map) {
        return entityBuilder.build(map);
    }
}
