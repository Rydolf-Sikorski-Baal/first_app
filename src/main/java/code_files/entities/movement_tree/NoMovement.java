package code_files.entities.movement_tree;

import code_files.entities.entities_tree.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
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
