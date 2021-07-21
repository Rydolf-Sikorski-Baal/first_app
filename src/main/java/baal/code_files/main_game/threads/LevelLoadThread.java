package baal.code_files.main_game.threads;

import baal.ApplicationContextProvider;
import baal.code_files.level_system.load_system.LevelsLoadControllerInterface;
import baal.code_files.main_game.Main_game_controller;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LevelLoadThread extends Thread {
    private final ApplicationContextProvider applicationContextProvider;
    private Main_game_controller main_game_controller;
    private final LevelsLoadControllerInterface levelsLoadController;

    public LevelLoadThread(ApplicationContextProvider applicationContextProvider,
                           LevelsLoadControllerInterface levelsLoadController) {
        this.applicationContextProvider = applicationContextProvider;
        this.levelsLoadController = levelsLoadController;
    }
    
    void setMain_game_controller(){
        this.main_game_controller = this.applicationContextProvider
                .getApplicationContext()
                .getBean(Main_game_controller.class);
    }

    @Override
    public void run(){
        setMain_game_controller();
        /*try {
            wait();
        } catch (InterruptedException e) {
            var level = main_game_controller.curr_level;
            var chapter = main_game_controller.chapter;
            var levelsLoadController =
                    (LevelsLoadController) chapter.getLevelsLoadController();

            try {
                levelsLoadController.updateFromThisLevel(level, chapter.getLevelVector());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }*/
    }
}
