package baal.code_files.blocks;

import baal.code_files.entities.entities_tree.Entity;
import baal.code_files.entity_movement.PositionInfoInterf;

public interface Collision {
    public PositionInfoInterf getPositionInfo(Entity entity, double cellHeight, double cellWeight);
}
