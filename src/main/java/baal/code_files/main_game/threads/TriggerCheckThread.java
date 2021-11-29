package baal.code_files.main_game.threads;

import baal.ApplicationContextProvider;
import baal.code_files.entity_movement.Collision;
import baal.code_files.level_system.event.PositionTrigger;
import baal.code_files.level_system.event.Trigger;
import baal.code_files.main_game.LevelChange;
import baal.code_files.main_game.Main_game_controller;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Scope("prototype")
public class TriggerCheckThread extends Thread{
    private final ApplicationContextProvider applicationContextProvider;
    @Setter private Main_game_controller main_game_controller;
    private final Collision collision;
    private final LevelChange levelChange;

    public TriggerCheckThread(ApplicationContextProvider applicationContextProvider, Collision collision, LevelChange levelChange) {
        this.applicationContextProvider = applicationContextProvider;
        this.collision = collision;
        this.levelChange = levelChange;
    }

    @Override
    public void run(){
        this.setMain_game_controller(this.applicationContextProvider
                .getApplicationContext()
                .getBean(Main_game_controller.class));

        checkTriggers();
    }

    @SneakyThrows
    private void checkTriggers(){
        while (this.main_game_controller.isStarted) {
            if (this.main_game_controller.curr_level.getLevelTriggers().getLevelTriggersVector() != null) {
                for (Trigger trigger : this.main_game_controller.curr_level.getLevelTriggers().getLevelTriggersVector()) {
                    if (trigger.check(collision.getPositionInfo(this.main_game_controller.curr_level.getLevelEntities().getEntityVector().get(0), 50, 50))) {
                        trigger.ifTrue(this.main_game_controller.curr_level);
                    } else {
                        trigger.ifFalse(this.main_game_controller.curr_level);
                    }
                }
            }

            //убрать
            PositionTrigger positionTrigger = new PositionTrigger();
            if (positionTrigger.check(collision.getPositionInfo(this.main_game_controller.curr_level.getLevelEntities().getEntityVector().get(0), 50 ,50))){
                positionTrigger.ifTrue(this.main_game_controller.curr_level);

                if (!Objects.equals(this.main_game_controller.currLevelFilePath, "src/main/resources/baal/code_files/level_system/second"))
                    this.levelChange.changeLevelByFlags("src/main/resources/baal/code_files/level_system/second",
                            true,true);
            }else{
                positionTrigger.ifFalse(this.main_game_controller.curr_level);

                if (!Objects.equals(this.main_game_controller.currLevelFilePath, "src/main/resources/baal/code_files/level_system/first"))
                    this.levelChange.changeLevelByFlags("src/main/resources/baal/code_files/level_system/first",
                            true, true);
            }

            sleep(100);
        }
    }
}