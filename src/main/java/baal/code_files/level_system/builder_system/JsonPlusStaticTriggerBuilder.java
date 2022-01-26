package baal.code_files.level_system.builder_system;

import baal.code_files.level_system.level.LevelInterface;
import baal.code_files.level_system.load_system.LevelLoaderInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Component
public class JsonPlusStaticTriggerBuilder implements BuilderInterface {
    private LevelInterface level;

    private final LevelLoaderInterface levelLoader;
    private final EventBuilderDirectorInterface eventBuilderDirector;

    public JsonPlusStaticTriggerBuilder(@Qualifier("levelJsonLoader")
                                                LevelLoaderInterface levelLoader,
                                        EventBuilderDirectorInterface eventBuilderDirector) {
        this.levelLoader = levelLoader;
        this.eventBuilderDirector = eventBuilderDirector;
    }

    @Override
    public BuilderInterface loadJson(String levelFileName) throws IOException {
        level = this.levelLoader.loadLevel(levelFileName);
        return this;
    }

    @Override
    @SuppressWarnings("all")
    public BuilderInterface loadTriggers(String levelFileName) {
        Yaml yaml = new Yaml();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("src/main/resources/baal/code_files/level_system/firstEvents");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Map<String, Object> map = yaml.load(inputStream);
        for (Map.Entry<String, Object> entry : map.entrySet()){
            level.getLevelEvents().getLevelEventsVector()
                    .add(eventBuilderDirector.build((Map<String, Object>) map.get(entry.getKey())));
        }

        return this;
    }

    @Override
    public LevelInterface build() {
        return level;
    }
}
