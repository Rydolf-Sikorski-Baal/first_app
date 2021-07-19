package baal.code_files.main_game.threads;

import baal.code_files.entities.entities_tree.Entity;
import baal.code_files.entity_movement.EntityMovement;
import baal.code_files.level_system.level.Level;
import baal.code_files.main_game.Main_game_controller;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class EntityMovementThread extends Thread {
    //private final Main_game_controller main_game_controller;
    private final EntityMovement entityMovement;

    public EntityMovementThread(/*Main_game_controller main_game_controller,*/
                                EntityMovement entityMovement) {
        //this.main_game_controller = main_game_controller;
        this.entityMovement = entityMovement;
    }

    @SneakyThrows
    @Override
    public void run() {
        /*Level level = this.main_game_controller.curr_level;
        boolean isStarted = this.main_game_controller.isStarted;

        while (isStarted) {
            for (Entity entity : level.getLevelEntities().getEntityVector())
                entityMovement.moveTick(entity, level);

            sleep(10000);

            isStarted = this.main_game_controller.isStarted;
        }*/
    }
}
