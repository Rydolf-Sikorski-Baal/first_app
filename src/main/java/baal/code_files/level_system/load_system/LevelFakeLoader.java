package baal.code_files.level_system.load_system;

import baal.code_files.level_system.level.Level;
import baal.code_files.level_system.level.LevelConnections;
import baal.code_files.level_system.level.LevelEntities;
import baal.code_files.level_system.level.LevelSettings;
import lombok.var;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LevelFakeLoader implements LevelLoaderInterface{
    @Override
    public Level loadLevel(String levelFileName) throws IOException {
        var levelConnections = new LevelConnections();
        var levelEntities = new LevelEntities();
        var levelSettings = new LevelSettings(6, 6,
                                              1.0,1.0,
                                              0.0, 0.0,
                                              0.0, 0.0,
                                              false);

        return null;
    }
}
