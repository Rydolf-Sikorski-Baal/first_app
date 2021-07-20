package baal.code_files.main_game.threads;

import baal.ApplicationContextProvider;
import baal.code_files.entities.entities_tree.Entity;
import baal.code_files.entity_movement.EntityMovement;
import baal.code_files.level_system.level.Level;
import baal.code_files.main_game.Main_game_controller;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EntityMovementThread extends Thread {
    private final ApplicationContextProvider applicationContextProvider;
    private Main_game_controller main_game_controller;
    private final EntityMovement entityMovement;

    public EntityMovementThread(ApplicationContextProvider applicationContextProvider,
                                EntityMovement entityMovement) {
        this.applicationContextProvider = applicationContextProvider;
        this.entityMovement = entityMovement;
    }

    @PostConstruct
    void setMain_game_controller(){
        this.main_game_controller = this.applicationContextProvider
                .getApplicationContext()
                .getBean(Main_game_controller.class);
    }

    @SneakyThrows
    @Override
    public void run() {
        Level level = this.main_game_controller.curr_level;
        boolean isStarted = this.main_game_controller.isStarted;

        while (isStarted) {
            for (Entity entity : level.getLevelEntities().getEntityVector())
                entityMovement.moveTick(entity, level);

            sleep(10000);

            isStarted = this.main_game_controller.isStarted;
        }
    }
}
