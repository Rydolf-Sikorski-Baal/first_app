package baal.code_files.main_game.threads;

import baal.ApplicationContextProvider;
import baal.code_files.entity_movement.Collision;
import baal.code_files.level_system.event.Event;
import baal.code_files.main_game.GameModel;
import baal.code_files.main_game.LevelChange;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class TriggerCheckThread extends Thread{
    private final ApplicationContextProvider applicationContextProvider;
    private final GameModel gameModel;
    private final Collision collision;
    private final LevelChange levelChange;

    public TriggerCheckThread(ApplicationContextProvider applicationContextProvider, GameModel gameModel, Collision collision, LevelChange levelChange) {
        this.applicationContextProvider = applicationContextProvider;
        this.gameModel = gameModel;
        this.collision = collision;
        this.levelChange = levelChange;
    }

    @Override
    public void run(){
        checkTriggers();
    }

    @SneakyThrows
    @SuppressWarnings("all")
    private void checkTriggers(){
        this.setName("trigger");
        while (this.gameModel.isStarted && !this.isInterrupted()) {
            if (this.gameModel.curr_level.getLevelEvents().getLevelEventsVector() != null) {
                for(Event event : this.gameModel.curr_level.getLevelEvents().getLevelEventsVector()){
                    if (event.check(this.gameModel.curr_level)) {
                        event.ifTrue(this.gameModel.curr_level);
                    }else{
                        event.ifFalse(this.gameModel.curr_level);
                    }
                }
            }
            sleep(1000);
        }
    }
}