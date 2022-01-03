package baal.code_files.main_game.threads;

import baal.ApplicationContextProvider;
import baal.code_files.level_system.builder_system.LevelBuilderDirectorInterface;
import baal.code_files.level_system.load_system.LevelsLoadControllerInterface;
import baal.code_files.main_game.Main_game_controller;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Scope("prototype")
public class LevelLoadThread extends Thread {
    private final ApplicationContextProvider applicationContextProvider;
    @Setter private Main_game_controller main_game_controller;
    private final LevelBuilderDirectorInterface levelBuilderDirector;

    public LevelLoadThread(ApplicationContextProvider applicationContextProvider,
                           LevelsLoadControllerInterface levelsLoadController, LevelBuilderDirectorInterface levelBuilderDirector) {
        this.applicationContextProvider = applicationContextProvider;
        this.levelBuilderDirector = levelBuilderDirector;
    }
    
    @SneakyThrows
    @Override
    public void run(){
        setMain_game_controller(this.applicationContextProvider
                .getApplicationContext()
                .getBean(Main_game_controller.class));

        loadFromThisLevel();
        this.setName("load");
    }

    private void loadFromThisLevel() throws IOException, ClassNotFoundException {
        this.main_game_controller.curr_level =
                levelBuilderDirector.build(this.main_game_controller.currLevelFilePath);
    }
}