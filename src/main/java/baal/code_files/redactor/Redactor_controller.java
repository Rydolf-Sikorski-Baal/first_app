package baal.code_files.redactor;

import baal.code_files.blocks.Blocks;
import baal.code_files.graphics_system.Drawer;
import baal.code_files.graphics_system.DrawerInterface;
import baal.code_files.level_system.level.Level;
import baal.code_files.level_system.load_system.LevelLoaderInterface;
import baal.code_files.level_system.save_system.LevelSaverInterface;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("redactor_scene.fxml")
public class Redactor_controller implements Initializable{
    private final LevelLoaderInterface levelLoader;
    private final LevelSaverInterface levelSaver;
    private final RedactorLevelContainerInterface redactorContainer;
    private final RedactorSceneElementsFunctionsContainerInterface redactorSceneElementsFunctionsContainer;
    private final DrawerInterface drawer;
    private final String levelFilePathPattern;
    private Level level;

    @FXML
    Button toMenu;
    @FXML
    Button setLevel;
    @FXML
    Button saveLevel;
    @FXML
    ComboBox<Blocks> redactorCellComboBox;
    @FXML
    ComboBox<String> redactorLevelComboBox;
    @FXML
    Canvas redactorMap;


    public Redactor_controller(@Qualifier("levelJsonLoader")
                                       LevelLoaderInterface levelLoader,
                               @Qualifier("levelJsonSaver")
                                       LevelSaverInterface levelSaver,
                               @Qualifier("redactorLevelContainer")
                                       RedactorLevelContainerInterface redactorContainer,
                               @Qualifier("redactorSceneElementsFunctionsContainer")
                                       RedactorSceneElementsFunctionsContainerInterface redactorSceneElementsFunctionsContainer,
                               @Qualifier("drawer")
                                       Drawer drawer,
                               @Value("${levelFilePathPattern}")
                                       String levelFilePathPattern) {
        this.levelLoader = levelLoader;
        this.levelSaver = levelSaver;
        this.redactorContainer = redactorContainer;
        this.redactorSceneElementsFunctionsContainer = redactorSceneElementsFunctionsContainer;
        this.drawer = drawer;
        this.levelFilePathPattern = levelFilePathPattern;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initMenu();
        initCanvas();
    }

    private void initCanvas() {
        redactorMap.setHeight(400);
        redactorMap.setWidth(400);

        redactorMap.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @SneakyThrows
            @Override
            public void handle(MouseEvent mouseEvent) {
                double curr_mouse_x = mouseEvent.getY();
                double curr_mouse_y = mouseEvent.getX();

                if (level == null)
                    level = levelLoader.loadLevel("src/main/resources/baal/code_files/level_system/first");
                if (level.getLevelCellsSizes() == null)
                    level.setLevelCellsSizes(drawer.getLevelCellsSizesForThisLevel(level));
                int curr_cell_x = (int) (curr_mouse_x / level.getLevelCellsSizes().getHeight());
                int curr_cell_y = (int) (curr_mouse_y / level.getLevelCellsSizes().getWidth());

                level.changeThisCellTo(curr_cell_x, curr_cell_y, redactorCellComboBox.getValue());
                drawer.drawThisLevel(redactorMap, level);
            }
        });
    }

    @SneakyThrows
    private void initMenu(){
        toMenu.setText("Main menu");
        this.toMenu.setOnAction(event ->
                redactorSceneElementsFunctionsContainer.getToMenuButtonFunction().accept(
                        (Stage) toMenu.getScene().getWindow()
                ));

        this.redactorCellComboBox.getItems().addAll(Blocks.values());

        this.redactorLevelComboBox.getItems().add("first");
        this.redactorLevelComboBox.getItems().add("second");
        this.redactorLevelComboBox.setValue("first");

        this.setLevel.setText("choose level");
        this.setLevel.setOnAction(event ->
                redactorSceneElementsFunctionsContainer.getSetLevelFunction(
                        levelLoader, drawer, redactorMap, redactorLevelComboBox)
                            .accept(level, levelFilePathPattern));

        this.saveLevel.setText("save level");
        this.saveLevel.setOnAction(event ->
                redactorSceneElementsFunctionsContainer.getSaveLevelFunction(levelSaver, redactorLevelComboBox)
                        .accept(level, levelFilePathPattern));
    }
}