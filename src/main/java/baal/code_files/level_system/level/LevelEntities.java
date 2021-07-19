package baal.code_files.level_system.level;

import baal.code_files.entities.entities_tree.Entity;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Vector;

@Data
public class LevelEntities implements LevelEntitiesInterface {
    Vector<Entity> entityVector;
}