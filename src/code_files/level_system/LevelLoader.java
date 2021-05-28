package code_files.level_system;

public class LevelLoader {
    private static LevelLoader levelLoader;
    public static LevelLoader getLevelLoader(){
        if (levelLoader == null){
            levelLoader = new LevelLoader();
        }
        return levelLoader;
    }
    private LevelLoader(){}

    public static void loadLevel(Level level){

    }
}