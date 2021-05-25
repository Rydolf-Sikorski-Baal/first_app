package code_files.interfaces;

import code_files.entities.entities_tree.Entity;

public interface Collision {
    public PositionInfoInterf getPositionInfo(Entity entity, double cellHeight, double cellWeight);
}
