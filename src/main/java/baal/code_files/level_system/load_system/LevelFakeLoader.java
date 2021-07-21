package baal.code_files.level_system.load_system;

import baal.code_files.level_system.level.Level;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LevelFakeLoader implements LevelLoaderInterface{
    @Override
    public Level loadLevel(String levelFileName) throws IOException {
        return null;
    }
}
