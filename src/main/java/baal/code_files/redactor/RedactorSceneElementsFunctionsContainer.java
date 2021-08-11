package baal.code_files.redactor;

import baal.ApplicationContextProvider;
import baal.code_files.Menu_controller;
import baal.code_files.graphics_system.DrawerInterface;
import baal.code_files.level_system.level.LevelInterface;
import baal.code_files.level_system.load_system.LevelLoaderInterface;
import baal.code_files.level_system.save_system.LevelSaverInterface;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Component
public class RedactorSceneElementsFunctionsContainer implements RedactorSceneElementsFunctionsContainerInterface{
    private final ApplicationContextProvider applicationContextProvider;

    public RedactorSceneElementsFunctionsContainer(ApplicationContextProvider applicationContextProvider) {
        this.applicationContextProvider = applicationContextProvider;
    }

    private void setScene(Stage stage){
        FxWeaver fxWeaver
                = (applicationContextProvider.getApplicationContext()).getBean(FxWeaver.class);

        Parent root = fxWeaver.loadView(Menu_controller.class);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private LevelLoaderInterface levelLoader;
    private DrawerInterface drawer;
    private javafx.scene.canvas.Canvas canvas;
    private ComboBox<String> levelComboBox;
    private void setLevel(LevelInterface level,
                          String levelFilePathPattern) throws IOException {
        String levelFilePath = String.format(levelFilePathPattern, levelComboBox.getValue());

        level = levelLoader.loadLevel(levelFilePath);
        drawer.drawThisLevel(canvas, level);
    }

    private LevelSaverInterface levelSaver;
    private void saveLevel(LevelInterface level,
                           String levelFilePathPattern){
        String levelFilePath = String.format(levelFilePathPattern, "first");
        levelSaver.saveLevel(level, levelFilePath);
    }

    public Consumer<Stage> getToMenuButtonFunction(){
        return this::setScene;
    }

    @Override
    public BiConsumer<LevelInterface, String> getSetLevelFunction(LevelLoaderInterface levelLoader,
                                                        DrawerInterface drawer,
                                                        javafx.scene.canvas.Canvas canvas,
                                                        ComboBox<String> levelComboBox) {
        this.levelLoader = levelLoader;
        this.drawer = drawer;
        this.canvas = canvas;
        this.levelComboBox = levelComboBox;
        return (level, levelFilePathPattern) -> {
            try {
                setLevel(level, levelFilePathPattern);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    @Override
    public BiConsumer<LevelInterface, String> getSaveLevelFunction(LevelSaverInterface levelSaver,
                                                                   ComboBox<String> levelComboBox) {
        this.levelSaver = levelSaver;
        this.levelComboBox = levelComboBox;
        return this::saveLevel;
    }
}