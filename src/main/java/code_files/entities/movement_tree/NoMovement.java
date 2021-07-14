package code_files.entities.movement_tree;

import code_files.entities.entities_tree.Entity;

public class NoMovement extends Movement{
    @Override
    public void moveTick(Entity entity) {
        //ничего
    }

    @Override
    public void moveBack(Entity entity) {
        //ничего
    }
}
