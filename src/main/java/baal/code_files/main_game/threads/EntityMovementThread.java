package baal.code_files.main_game.threads;

import baal.ApplicationContextProvider;
import baal.code_files.entities.entities_tree.Entity;
import baal.code_files.entities.movement_tree.AccordingToSpeed;
import baal.code_files.entity_movement.EntityMovement;
import baal.code_files.level_system.level.LevelInterface;
import baal.code_files.main_game.LevelChange;
import baal.code_files.main_game.Main_game_controller;
import javafx.application.Platform;
import lombok.Setter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
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
                                        int tickInMillisecond,
                                @Qualifier("levelChange")
                                        LevelChange levelChange) {
        this.applicationContextProvider = applicationContextProvider;
        this.entityMovement = entityMovement;
        this.tickInMillisecond = tickInMillisecond;
        this.levelChange = levelChange;
    }

    public volatile int tick_number = 0;
    @SneakyThrows
    @SuppressWarnings("all")
    @Override
    public void run() {
        setMain_game_controller(applicationContextProvider
                .getApplicationContext()
                .getBean(Main_game_controller.class));

        LevelInterface level = this.main_game_controller.getCurr_level();

        this.setName("movement");
        while (this.main_game_controller.isStarted && !this.isInterrupted()) {
            tick_number++;

            for (Entity entity : level.getLevelEntities().getEntityVector()) {
                ((AccordingToSpeed)entity.movement)
                        .changeSpeedX(this.main_game_controller.curr_level.getLevelSettings().getDefaultSpeedDeltaX());
                ((AccordingToSpeed)entity.movement)
                        .changeSpeedY(this.main_game_controller.curr_level.getLevelSettings().getDefaultSpeedDeltaY());
                entityMovement.moveTick(entity, level);
            }

            //checkEntitiesCollision(level);

            Platform.runLater(
                () -> {
                    this.main_game_controller.getDrawer().drawThisLevel(main_game_controller.getCanvas(), main_game_controller.getCurr_level());
                    this.main_game_controller.label.setText(String.valueOf(tick_number));
                }
            );


            sleep(tickInMillisecond);
        }
    }

    private final LevelChange levelChange;
    private void checkEntitiesCollision(@NotNull LevelInterface level) {
        for (Entity firstEntity : level.getLevelEntities().getEntityVector()){
            for (Entity secondEntity : level.getLevelEntities().getEntityVector()){
                if (firstEntity != secondEntity){
                    if (isIntersect(firstEntity, secondEntity)){
                        levelChange.changeLevelByFlags("src/main/resources/baal/code_files/level_system/first",
                                false, false);
                    }
                }
            }
        }
    }

    private boolean isIntersect(Entity firstEntity, Entity secondEntity) {
        double firstX = firstEntity.position.getX();
        double firstY = firstEntity.position.getY();

        double secondX = secondEntity.position.getX();
        double secondY = secondEntity.position.getY();

        double xMin = Math.min(firstX + 0.5, secondX + 0.5);
        double xMax = Math.max(firstX, secondY);
        double xRes = (xMax - xMin > 0 ? xMax - xMin : 0);

        double yMin = Math.min(firstY + 0.5, secondY + 0.5);
        double yMax = Math.max(firstY, secondY);
        double yRes = (yMax - yMin > 0 ? yMax - yMin : 0);

        double res = 10 * (xRes * yRes);
        return res > 1;
    }
}