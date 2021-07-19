package baal.code_files.main_game.threads;

import baal.code_files.level_system.Chapter;
import baal.code_files.level_system.load_system.LevelsLoadController;
import baal.code_files.level_system.load_system.LevelsLoadControllerInterface;
import baal.code_files.main_game.Main_game_controller;
import lombok.var;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LevelLoadThread extends Thread {
    //private final Main_game_controller main_game_controller;
    private final LevelsLoadControllerInterface levelsLoadController;

    public LevelLoadThread(//Main_game_controller main_game_controller,
                           LevelsLoadControllerInterface levelsLoadController) {
        //this.main_game_controller = main_game_controller;
        this.levelsLoadController = levelsLoadController;
    }

    @Override
    public void run(){
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
