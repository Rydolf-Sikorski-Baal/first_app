package baal.code_files.level_system.builder_system;

import baal.code_files.entities.entities_tree.Hero;
import baal.code_files.level_system.event.Event;
import baal.code_files.level_system.event.Term;
import baal.code_files.level_system.event.position.HeroPositionXOver;
import baal.code_files.level_system.level.LevelInterface;
import baal.code_files.level_system.load_system.LevelLoaderInterface;
import baal.code_files.main_game.LevelChange;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

@Component
public class JsonPlusStaticTriggerBuilder implements BuilderInterface {

    private final LevelLoaderInterface levelLoader;

    public JsonPlusStaticTriggerBuilder(@Qualifier("levelJsonLoader")
                                                LevelLoaderInterface levelLoader) {
        this.levelLoader = levelLoader;
    }

    private LevelInterface level;
    @Override
    @SneakyThrows
    public BuilderInterface loadJson(String levelFileName) {
        level = this.levelLoader.loadLevel(levelFileName);
        return this;
    }

    private void doSmt(LevelInterface levelInterface) {}

    @Override
    @SuppressWarnings("all")
    public BuilderInterface loadTriggers(String levelFileName) {
        Yaml yaml = new Yaml();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("src/main/resources/baal/code_files/blocks/blocks");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Map<String, Object> map = yaml.load(inputStream);

        ArrayList<Term<?>> termList = new ArrayList<>();
        HeroPositionXOver<Hero> term = new HeroPositionXOver<>(5);
        termList.add(term);
        Event event = new Event(termList,
                this::doSmt,
                this::doSmt);
        /*for (Map.Entry<String, Object> entry : map.entrySet()){
            final Map<String, Object> entry1 = (Map<String, Object>) entry;
            String type = (String) entry1.get("type");
            HeroPositionXOver<Hero> term = new HeroPositionXOver<>(5);

            termList.add(term);
        }*/
        level
                .getLevelEvents()
                .getLevelEventsVector()
                .add(event);
        return this;
    }

    @Override
    public LevelInterface build() {
        return level;
    }
}
