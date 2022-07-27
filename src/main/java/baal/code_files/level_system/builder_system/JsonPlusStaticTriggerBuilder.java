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
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@Component
public class JsonPlusStaticTriggerBuilder implements BuilderInterface {
    private LevelInterface level;

    private final LevelLoaderInterface levelLoader;
    private final EventBuilderDirectorInterface eventBuilderDirector;
    private final EntityBuilderDirectorInterface entityBuilderDirector;

    public JsonPlusStaticTriggerBuilder(@Qualifier("levelJsonLoader")
                                                LevelLoaderInterface levelLoader,
                                        EventBuilderDirectorInterface eventBuilderDirector,
                                        EntityBuilderDirectorInterface entityBuilderDirector) {
        this.levelLoader = levelLoader;
        this.eventBuilderDirector = eventBuilderDirector;
        this.entityBuilderDirector = entityBuilderDirector;
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
                    .add(eventBuilderDirector.build((Map<String, Object>) entry.getValue()));
        }

        return this;
    }

    @Override
    @SuppressWarnings("all")
    public BuilderInterface loadEntities(String levelFileName) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Yaml yaml = new Yaml();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(String.format(levelFileName, "firstEntities"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Map<String, Object> map = yaml.load(inputStream);
        for (Map.Entry<String, Object> entry : map.entrySet()){
            level.getLevelEntities().getEntityVector()
                    .add(entityBuilderDirector.build((Map<String, Object>) entry.getValue()));
        }

        return this;
    }

    @Override
    public LevelInterface build() {
        return level;
    }
}
