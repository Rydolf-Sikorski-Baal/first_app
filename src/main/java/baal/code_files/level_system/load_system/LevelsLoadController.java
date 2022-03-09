package baal.code_files.level_system.load_system;

import baal.code_files.level_system.level.Level;
import baal.code_files.level_system.level.LevelInterface;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Vector;

@Component
@Getter
public class LevelsLoadController implements LevelsLoadControllerInterface {
    private final LevelLoaderInterface levelLoader;

    public LevelsLoadController(@Qualifier("levelJsonLoader")
                                        LevelLoaderInterface levelLoader) {
        this.levelLoader = levelLoader;
    }

    public void updateFromThisLevel(LevelInterface level, Vector<LevelInterface> levelVector) throws IOException {
        if (levelVector == null) levelVector = new Vector<>();
        if (level.getLevelConnections().getLevelFileNamesVector() != null){
            for (String fileName : level.getLevelConnections().getLevelFileNamesVector()) {
                levelVector.add(levelLoader.loadLevel(fileName));
            }
        }
    }

    @Override
    public void loadThisLevel(String levelFileName, Vector<Level> levelVector) throws IOException {
        levelVector.add(levelLoader.loadLevel(levelFileName));
    }
}