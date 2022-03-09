package baal.code_files.main_game.threads;

import baal.ApplicationContextProvider;
import baal.code_files.level_system.builder_system.LevelBuilderDirectorInterface;
import baal.code_files.level_system.load_system.LevelsLoadControllerInterface;
import baal.code_files.main_game.GameModel;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Scope("prototype")
public class LevelLoadThread extends Thread {
    private final ApplicationContextProvider applicationContextProvider;
    private final GameModel gameModel;
    private final LevelBuilderDirectorInterface levelBuilderDirector;

    public LevelLoadThread(ApplicationContextProvider applicationContextProvider,
                           LevelsLoadControllerInterface levelsLoadController, GameModel gameModel, LevelBuilderDirectorInterface levelBuilderDirector) {
        this.applicationContextProvider = applicationContextProvider;
        this.gameModel = gameModel;
        this.levelBuilderDirector = levelBuilderDirector;
    }
    
    @SneakyThrows
    @Override
    public void run(){
        loadFromThisLevel();
        this.setName("load");
    }

    private void loadFromThisLevel() throws IOException, ClassNotFoundException {
        this.gameModel.curr_level =
                levelBuilderDirector.build(this.gameModel.currLevelFilePath);
    }
}