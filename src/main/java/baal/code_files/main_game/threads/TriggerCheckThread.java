package baal.code_files.main_game.threads;

import baal.ApplicationContextProvider;
import baal.code_files.entities.entities_tree.Hero;
import baal.code_files.entity_movement.Collision;
import baal.code_files.level_system.event.PositionTrigger;
import baal.code_files.level_system.event.Trigger;
import baal.code_files.main_game.Main_game_controller;
import javafx.scene.paint.Color;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class TriggerCheckThread extends Thread{
    private final ApplicationContextProvider applicationContextProvider;
    @Setter private Main_game_controller main_game_controller;
    private final Collision collision;

    public TriggerCheckThread(ApplicationContextProvider applicationContextProvider, Collision collision) {
        this.applicationContextProvider = applicationContextProvider;
        this.collision = collision;
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
            }else{
                positionTrigger.ifFalse(this.main_game_controller.curr_level);
            }

            sleep(100);
        }
    }
}