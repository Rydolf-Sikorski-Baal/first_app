package baal.code_files.main_game;

import baal.ApplicationContextProvider;
import baal.code_files.entities.controllability_tree.HeroControls;
import baal.code_files.entities.controllability_tree.Uncontrollable;
import baal.code_files.entities.entities_tree.DeathEntity;
import baal.code_files.entities.entities_tree.Entity;
import baal.code_files.entities.entities_tree.Hero;
import baal.code_files.entities.movement_tree.AccordingToSpeed;
import baal.code_files.entities.shape_tree.Rectangle;
import baal.code_files.main_game.threads.EntityMovementThread;
import baal.code_files.main_game.threads.LevelLoadThread;
import baal.code_files.main_game.threads.TriggerCheckThread;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("levelChange")
public class LevelChange {
    @Autowired ApplicationContextProvider applicationContextProvider;
    private final GameModel gameModel;

    @Data
    private static class Flags{
        private final boolean isPositionRequiresSaving;
        private final boolean isMovementRequiresSaving;
    }

    private Flags flags;

    public void changeLevelByFlags(String levelFilePath,
                                   boolean isPositionRequiresSaving,
                                   boolean isMovementRequiresSaving){
        flags = new Flags(isPositionRequiresSaving,
                isMovementRequiresSaving);

        changeLevel(levelFilePath);
    }

    Main_game_controller main_game_controller;
    private void changeLevel(String levelFilePath){
        main_game_controller = this.applicationContextProvider
                .getApplicationContext()
                .getBean(Main_game_controller.class);

        closeLevel();
        gameModel.currLevelFilePath = levelFilePath;
        startLevel();
    }

    private void closeLevelThreads(){
        if (this.main_game_controller.getEntityMovementThread() != null)
            this.main_game_controller.getEntityMovementThread().interrupt();
        if (this.main_game_controller.getTriggerCheckThread() != null)
            this.main_game_controller.getTriggerCheckThread().interrupt();
    }

    private void closeLevel(){
        closeLevelThreads();
    }

    @SneakyThrows
    private void startLevel() {
        LevelLoadThread levelLoadThread =
                (LevelLoadThread) applicationContextProvider.getApplicationContext().getBean("levelLoadThread");
        levelLoadThread.start();

        while (levelLoadThread.isAlive()){}

        gameModel.isStarted = true;

        assignThreads();
        startThreads();
    }

    private void assignThreads(){
        main_game_controller
                .setEntityMovementThread(applicationContextProvider
                        .getApplicationContext()
                        .getBean(EntityMovementThread.class));
        main_game_controller
                .setTriggerCheckThread(applicationContextProvider
                        .getApplicationContext()
                        .getBean(TriggerCheckThread.class));
    }

    private void startThreads(){
        main_game_controller.getEntityMovementThread().start();
        main_game_controller.getTriggerCheckThread().start();
    }
}