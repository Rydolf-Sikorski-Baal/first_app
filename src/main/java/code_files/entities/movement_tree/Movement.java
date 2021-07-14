package code_files.entities.movement_tree;

import code_files.entities.entities_tree.Entity;

public abstract class Movement {
    public abstract void moveTick(Entity entity);
    public abstract void moveBack(Entity entity);
}