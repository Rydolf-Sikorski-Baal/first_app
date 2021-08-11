package baal.code_files.redactor;

import baal.code_files.graphics_system.DrawerInterface;
import baal.code_files.level_system.level.LevelInterface;
import baal.code_files.level_system.load_system.LevelLoaderInterface;
import baal.code_files.level_system.save_system.LevelSaverInterface;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface RedactorSceneElementsFunctionsContainerInterface {
    Consumer<Stage> getToMenuButtonFunction();
    BiConsumer<LevelInterface, String> getSetLevelFunction(LevelLoaderInterface levelLoader,
                                                 DrawerInterface drawer,
                                                 javafx.scene.canvas.Canvas canvas,
                                                 ComboBox<String> levelComboBox);
    BiConsumer<LevelInterface, String> getSaveLevelFunction(LevelSaverInterface levelSaver,
                                                    ComboBox<String> levelComboBox);
}