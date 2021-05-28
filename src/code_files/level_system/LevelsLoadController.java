package code_files.level_system;

public class LevelsLoadController {
    LevelLoader levelLoader;
    public LevelsLoadController(){
        levelLoader = LevelLoader.getLevelLoader();
    }
}
