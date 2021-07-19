package baal.code_files.level_system.load_system;

import baal.code_files.level_system.level.Level;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Vector;

@Component
@Getter
public class LevelsLoadController implements LevelsLoadControllerInterface {
    private final LevelLoaderInterface levelLoader;

    public LevelsLoadController(LevelLoader levelLoader) {
        this.levelLoader = levelLoader;
    }

    public void updateFromThisLevel(Level level, Vector<Level> levelVector) throws IOException {
        for (String fileName : level.getLevelConnections().getLevelFileNamesVector()){
            levelVector.add(levelLoader.loadLevel(fileName));
        }
    }

    @Override
    public void loadThisLevel(String levelFileName, Vector<Level> levelVector) throws IOException {
        levelVector.add(levelLoader.loadLevel(levelFileName));
    }
}