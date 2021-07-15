package baal.code_files.level_system.load_system;

import baal.code_files.level_system.level.Level;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@Scope("prototype")
public class LevelLoader implements LevelLoaderInterface{
    public Level loadLevel(String levelFileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(String.format("src/resources/levels/%s.txt", levelFileName));

        return objectMapper.readValue(file, Level.class);
    }
}