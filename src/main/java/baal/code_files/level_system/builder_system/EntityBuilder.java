package baal.code_files.level_system.builder_system;

import baal.code_files.entities.controllability_tree.Controllability;
import baal.code_files.entities.entities_tree.Entity;
import baal.code_files.entities.movement_tree.Movement;
import baal.code_files.entities.shape_tree.Shape;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EntityBuilder implements EntityBuilderInterface {
    private Entity entity;

    @Override
    @SuppressWarnings("all")
    public Entity build(Map<String, Object> map) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Map.Entry<String, Object> entry = (Map.Entry<String, Object>) map.entrySet().toArray()[0];

        Map<String, String> args = (Map<String, String>) entry.getValue();

        Controllability controllability = null;
        controllability = (Controllability) Class.forName(args.get("controllability")).getConstructor().newInstance();

        Movement movement = null;
        movement = (Movement) Class.forName(args.get("movement")).getConstructor().newInstance();

        Shape shape = null;
        shape = (Shape) Class.forName(args.get("shape")).getConstructor().newInstance();

        entity = (Entity) Class.forName(entry.getKey())
                    .getConstructor()
                    .newInstance(shape, movement, controllability);

        return entity;
    }
}
