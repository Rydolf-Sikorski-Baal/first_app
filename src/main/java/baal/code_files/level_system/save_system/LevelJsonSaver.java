package baal.code_files.level_system.save_system;

import baal.code_files.level_system.level.Level;
import baal.code_files.level_system.level.LevelInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class LevelJsonSaver implements LevelSaverInterface {
    @SneakyThrows
    @Override
    public void saveLevel(LevelInterface level, String filePath) {
        File file = new File(filePath);
        file.createNewFile();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(file, level);
    }

    @Override
    public String getPathForThisLevel(Level level) {
        return String.format("src/main/resources/baal/code_files/level_system/%s", "first");
    }
}