package baal.code_files.main_game.threads;

import baal.ApplicationContextProvider;
import baal.code_files.entities.entities_tree.Entity;
import baal.code_files.entity_movement.EntityMovement;
import baal.code_files.level_system.level.Level;
import baal.code_files.main_game.Main_game_controller;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EntityMovementThread extends Thread {
    private final ApplicationContextProvider applicationContextProvider;
    @Setter private Main_game_controller main_game_controller;
    private final EntityMovement entityMovement;

    private final int tickInMillisecond;

    public EntityMovementThread(@Qualifier("applicationContextProvider")
                                        ApplicationContextProvider applicationContextProvider,
                                @Qualifier("entityMovement")
                                        EntityMovement entityMovement,
                                @Value("${tickInMillisecond}")
                                        int tickInMillisecond) {
        this.applicationContextProvider = applicationContextProvider;
        this.entityMovement = entityMovement;
        this.tickInMillisecond = tickInMillisecond;
    }

    @SneakyThrows
    @Override
    public void run() {
        setMain_game_controller(applicationContextProvider
                .getApplicationContext()
                .getBean(Main_game_controller.class));

        Level level = this.main_game_controller.curr_level;

        while (this.main_game_controller.isStarted) {
            for (Entity entity : level.getLevelEntities().getEntityVector())
                entityMovement.moveTick(entity, level);

            sleep(tickInMillisecond);
        }
    }
}
