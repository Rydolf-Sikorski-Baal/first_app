package baal.code_files.main_game.threads;

import baal.ApplicationContextProvider;
import baal.code_files.entities.entities_tree.Hero;
import baal.code_files.entity_movement.Collision;
import baal.code_files.level_system.event.PositionTrigger;
import baal.code_files.main_game.Main_game_controller;
import javafx.scene.paint.Color;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
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
            PositionTrigger positionTrigger = new PositionTrigger();
            if (positionTrigger.check(
                    collision.getPositionInfo
                            (this.main_game_controller.curr_level
                                            .getLevelEntities()
                                            .getEntityVector()
                                            .get(0),
                                    50,
                                    50))) {
                ((Hero)this.main_game_controller.curr_level
                        .getLevelEntities()
                        .getEntityVector()
                        .get(0))
                        .setMyColor(Color.GREEN);
            }else{
                ((Hero)this.main_game_controller.curr_level
                        .getLevelEntities()
                        .getEntityVector()
                        .get(0))
                        .setMyColor(Color.RED);
            }

            sleep(100);
        }
    }
}