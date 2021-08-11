package baal.code_files.level_system.level;

import baal.code_files.entities.entities_tree.Entity;
import lombok.Data;

import java.util.Vector;

@Data
public class LevelEntities implements LevelEntitiesInterface {
    Vector<Entity> entityVector;
}