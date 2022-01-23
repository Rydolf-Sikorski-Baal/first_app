package baal.code_files.main_game.threads;

import baal.ApplicationContextProvider;
import baal.code_files.entity_movement.Collision;
import baal.code_files.level_system.event.Event;
import baal.code_files.main_game.LevelChange;
import baal.code_files.main_game.Main_game_controller;
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
    @SuppressWarnings("all")
    private void checkTriggers(){
        this.setName("trigger");
        while (this.main_game_controller.isStarted && !this.isInterrupted()) {
            if (this.main_game_controller.curr_level.getLevelEvents().getLevelEventsVector() != null) {
                for(Event event : this.main_game_controller.curr_level.getLevelEvents().getLevelEventsVector()){
                    if (event.check(this.main_game_controller.curr_level)) {
                        event.ifTrue(this.main_game_controller.curr_level);
                    }else{
                        event.ifFalse(this.main_game_controller.curr_level);
                    }
                }
            }
            sleep(1000);
        }
    }
}