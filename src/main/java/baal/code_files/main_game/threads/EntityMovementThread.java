package baal.code_files.main_game.threads;

import baal.code_files.entities.entities_tree.Entity;
import baal.code_files.entities.movement_tree.AccordingToSpeed;
import baal.code_files.entity_movement.EntityMovement;
import baal.code_files.graphics_system.Drawer;
import baal.code_files.level_system.level.LevelInterface;
import baal.code_files.main_game.GameModel;
import baal.code_files.main_game.LevelChange;
import javafx.application.Platform;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class EntityMovementThread extends Thread {
    private final EntityMovement entityMovement;

    private final GameModel gameModel;
    private final Drawer drawer;

    private final int tickInMillisecond;

    public EntityMovementThread(@Qualifier("entityMovement")
                                        EntityMovement entityMovement,
                                @Qualifier("gameModel")
                                        GameModel gameModel,
                                @Qualifier("drawer")
                                        Drawer drawer,
                                @Value("${tickInMillisecond}")
                                        int tickInMillisecond,
                                @Qualifier("levelChange")
                                        LevelChange levelChange) {
        this.entityMovement = entityMovement;
        this.gameModel = gameModel;
        this.drawer = drawer;
        this.tickInMillisecond = tickInMillisecond;
        this.levelChange = levelChange;
    }

    public volatile int tick_number = 0;
    @SneakyThrows
    @SuppressWarnings("all")
    @Override
    public void run() {
        LevelInterface level = this.gameModel.curr_level;

        this.setName("movement");
        while (this.gameModel.isStarted && !this.isInterrupted()) {
            tick_number++;

            for (Entity entity : level.getLevelEntities().getEntityVector()) {
                ((AccordingToSpeed)entity.movement)
                        .changeSpeedX(this.gameModel.curr_level.getLevelSettings().getDefaultSpeedDeltaX());
                ((AccordingToSpeed)entity.movement)
                        .changeSpeedY(this.gameModel.curr_level.getLevelSettings().getDefaultSpeedDeltaY());
                entityMovement.moveTick(entity, level);
            }

            checkEntitiesCollision(level);

            Platform.runLater(
                () -> {
                    this.drawer.drawThisLevel(gameModel.canvas, gameModel.curr_level);
                    this.gameModel.chapter.tickCount++;
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
                        this.gameModel.chapter.deathCount++;
                        //levelChange.changeLevelByFlags("src/main/resources/baal/code_files/level_system/first",
                          //      false, false);
                    }
                }
            }
        }
    }

    private boolean isIntersect(@NotNull Entity firstEntity,
                                @NotNull Entity secondEntity) {
        double firstX = firstEntity.position.getX();
        double firstY = firstEntity.position.getY();

        double secondX = secondEntity.position.getX();
        double secondY = secondEntity.position.getY();

        double xMin = Math.min(firstX + 0.5, secondX + 0.5);
        double xMax = Math.max(firstX, secondX);
        double xRes = (xMin - xMax > 0 ? xMin - xMax  : 0);

        double yMin = Math.min(firstY + 0.5, secondY + 0.5);
        double yMax = Math.max(firstY, secondY);
        double yRes = (yMin - yMax > 0 ? yMin - yMax : 0);

        double res = 10 * (xRes * yRes);
        return res > 0.5;
    }
}