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

import java.util.Vector;

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
        /*double positionX = gameModel.curr_level == null ? 0 : gameModel.curr_level
                           .getLevelEntities().getEntityVector().get(0).position
                           .getX(),
               positionY = gameModel.curr_level == null ? 0 : gameModel.curr_level
                           .getLevelEntities().getEntityVector().get(0).position
                           .getY();
        double speedX = gameModel.curr_level == null ? 0 :
                        ((AccordingToSpeed)gameModel.curr_level
                        .getLevelEntities().getEntityVector().get(0).movement)
                        .getSpeed_x(),
               speedY = gameModel.curr_level == null ? 0 :
                        ((AccordingToSpeed)gameModel.curr_level
                        .getLevelEntities().getEntityVector().get(0).movement)
                        .getSpeed_y();

        main_game_controller
                .setLevelLoadThread(applicationContextProvider
                        .getApplicationContext()
                        .getBean(LevelLoadThread.class));
        main_game_controller.getLevelLoadThread().start();
        gameModel.isStarted = true;

        while(main_game_controller.getLevelLoadThread().isAlive()){}
        if (!flags.isPositionRequiresSaving()) {
            positionX = gameModel.curr_level.getLevelSettings().getHeroStartX();
            positionY = gameModel.curr_level.getLevelSettings().getHeroStartY();
        }
        if (!flags.isMovementRequiresSaving()){
            speedX = gameModel.curr_level.getLevelSettings().getDefaultSpeedX();
            speedY = gameModel.curr_level.getLevelSettings().getDefaultSpeedY();
        }
        Rectangle rectangle = new Rectangle();
        rectangle.setX_size(0.5);
        rectangle.setY_size(0.5);
        AccordingToSpeed accordingToSpeed = new AccordingToSpeed();
        accordingToSpeed.setSpeed_x(speedX);
        accordingToSpeed.setSpeed_y(speedY);
        Hero hero = new Hero(rectangle, accordingToSpeed, new HeroControls());

        hero.setPosition(positionX, positionY);
        hero.connect();

        AccordingToSpeed accordingToSpeed2 = new AccordingToSpeed();
        accordingToSpeed2.setSpeed_x(0);
        accordingToSpeed2.setSpeed_y(0.01);
        DeathEntity deathEntity = new DeathEntity(rectangle, accordingToSpeed2, new Uncontrollable());
        deathEntity.setPosition(3.0, 2.0);

        Vector<Entity> entityVector = new Vector<>();
        entityVector.add(hero);
        entityVector.add(deathEntity);
        gameModel.curr_level.getLevelEntities().setEntityVector(entityVector);*/

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