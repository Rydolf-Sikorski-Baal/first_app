package code_files.level_system;

import code_files.level_system.level.Level;
import lombok.Getter;

import java.io.IOException;
import java.util.Vector;

@Getter
public class LevelsLoadController implements LevelsLoadContrInterface {
    private final LevelLoader levelLoader;

    private static LevelsLoadController levelsLoadController;
    private LevelsLoadController() {
        levelLoader = LevelLoader.getLevelLoader();
    }
    public static LevelsLoadController getLevelsLoadController(){
        if (levelsLoadController == null){
            levelsLoadController = new LevelsLoadController();
        }
        return levelsLoadController;
    }


    public void updateFromThisLevel(Level level, Vector<Level> levelVector) throws IOException {
        for (String fileName : level.getLevelConnections().getLevelFileNamesVector()){
            levelVector.add(levelLoader.loadLevel(fileName));
        }
    }
}
