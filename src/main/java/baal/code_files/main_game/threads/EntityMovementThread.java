package baal.code_files.main_game.threads;

import baal.ApplicationContextProvider;
import baal.code_files.entities.entities_tree.Entity;
import baal.code_files.entities.movement_tree.AccordingToSpeed;
import baal.code_files.entity_movement.EntityMovement;
import baal.code_files.level_system.level.Level;
import baal.code_files.main_game.Main_game_controller;
import javafx.application.Platform;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
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

    public volatile int tick_number = 0;
    @SneakyThrows
    @Override
    public void run() {
        setMain_game_controller(applicationContextProvider
                .getApplicationContext()
                .getBean(Main_game_controller.class));

        Level level = this.main_game_controller.getCurr_level();

        while (this.main_game_controller.isStarted) {
            tick_number++;

            for (Entity entity : level.getLevelEntities().getEntityVector()) {
                ((AccordingToSpeed)entity.movement).changeSpeedX(0.5);
                entityMovement.moveTick(entity, level);
            }

            Platform.runLater(
                    () -> {
                        this.main_game_controller.getDrawer().drawThisLevel(main_game_controller.getCanvas(), main_game_controller.getCurr_level());
                        this.main_game_controller.label.setText(String.valueOf(tick_number));
                    }
            );

            sleep(tickInMillisecond);
        }
    }
}