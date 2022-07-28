package baal.code_files.main_game.threads;

import baal.code_files.level_system.builder_system.LevelBuilderDirectorInterface;
import baal.code_files.main_game.GameModel;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@Component
@Scope("prototype")
public class LevelLoadThread extends Thread {
    private final GameModel gameModel;
    private final LevelBuilderDirectorInterface levelBuilderDirector;

    public LevelLoadThread(GameModel gameModel,
                           @Qualifier("levelBuilderDirector")
                                   LevelBuilderDirectorInterface levelBuilderDirector) {
        this.gameModel = gameModel;
        this.levelBuilderDirector = levelBuilderDirector;
    }
    
    @SneakyThrows
    @Override
    public void run(){
        loadFromThisLevel();
        this.setName("load");
    }

    private void loadFromThisLevel() throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        this.gameModel.curr_level =
                levelBuilderDirector.build(this.gameModel.currLevelFilePath);
    }
}