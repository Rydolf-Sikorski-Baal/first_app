package code_files.level_system;

import code_files.level_system.level.Level;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class LevelLoader {
    private static LevelLoader levelLoader;
    public static LevelLoader getLevelLoader(){
        if (levelLoader == null){
            levelLoader = new LevelLoader();
        }
        return levelLoader;
    }
    private LevelLoader(){}

    public Level loadLevel(String levelFileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(String.format("src/resources/levels/%s.txt", levelFileName));

        return objectMapper.readValue(file, Level.class);
    }
}