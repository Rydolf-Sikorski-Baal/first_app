package baal.code_files.level_system.builder_system;

import baal.code_files.entities.entities_tree.Entity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EntityBuilderDirector implements EntityBuilderDirectorInterface{
    private final EntityBuilderInterface entityBuilder;

    @Override
    public Entity build(Map<String, Object> map) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return entityBuilder.build(map);
    }
}
