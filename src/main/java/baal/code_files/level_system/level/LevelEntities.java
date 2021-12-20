package baal.code_files.level_system.level;

import baal.code_files.entities.entities_tree.Entity;
import lombok.*;

import java.util.Vector;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class LevelEntities implements LevelEntitiesInterface {
    @Setter(onMethod = @__(@Override))
    @Getter(onMethod = @__(@Override))
    Vector<Entity> entityVector;
}