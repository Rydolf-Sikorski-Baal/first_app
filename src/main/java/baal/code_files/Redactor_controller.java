package baal.code_files;

import baal.code_files.level_system.level.Level;
import baal.code_files.level_system.load_system.LevelLoaderInterface;
import baal.code_files.level_system.save_system.LevelSaverInterface;
import javafx.fxml.Initializable;
import lombok.SneakyThrows;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("redactor_scene.fxml")
public class Redactor_controller implements Initializable{
    private final LevelLoaderInterface levelLoader;
    private final LevelSaverInterface levelSaver;
    private Level level;



    public Redactor_controller(@Qualifier("levelPrimitiveLoader")
                                       LevelLoaderInterface levelLoader,
                               @Qualifier("levelJsonSaver")
                                       LevelSaverInterface levelSaver) {
        this.levelLoader = levelLoader;
        this.levelSaver = levelSaver;
    }

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initMenu();
        initCanvas();

        loadLevel("src/main/java/technical_files/Levels/level_1");
        saveLevel();
    }

    private void initCanvas() {

    }

    private void initMenu(){}

    private void loadLevel(String levelFilePath) throws IOException {
        level = levelLoader.loadLevel(levelFilePath);
    }
    private void saveLevel(){
        levelSaver.saveLevel(level, levelSaver.getPathForThisLevel(level));
    }
}