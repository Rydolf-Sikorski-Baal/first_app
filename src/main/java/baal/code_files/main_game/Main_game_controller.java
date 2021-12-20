package baal.code_files.main_game;

import baal.ApplicationContextProvider;
import baal.code_files.Menu_controller;
import baal.code_files.chapter_system.ChapterInterface;
import baal.code_files.entities.entities_tree.Entity;
import baal.code_files.graphics_system.DrawerInterface;
import baal.code_files.level_system.level.LevelInterface;
import baal.code_files.level_system.load_system.LevelLoaderInterface;
import baal.code_files.main_game.controls.ControlsCodes;
import baal.code_files.main_game.threads.EntityMovementThread;
import baal.code_files.main_game.threads.LevelLoadThread;
import baal.code_files.main_game.threads.TriggerCheckThread;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

@Component
@FxmlView("main_game.fxml")
@Getter
@Setter
public class Main_game_controller implements Initializable {
    public volatile LevelInterface curr_level;
    public volatile boolean isStarted;
    public volatile ChapterInterface chapter;
    private final DrawerInterface drawer;

    private final LevelChange levelChange;

    private final LevelLoaderInterface levelLoader;
    private final String firstLevelFilePath;
    private final String secondLevelFilePath;

    private EntityMovementThread entityMovementThread;
    private LevelLoadThread levelLoadThread;
    private TriggerCheckThread triggerCheckThread;

    public String currLevelFilePath;

    @FXML
    Button ToMenu;

    @FXML
    Canvas canvas;

    @FXML
    public volatile Label label;
    public volatile int tik_number = 0;

    public Main_game_controller(@Qualifier("drawer")
                                        DrawerInterface drawer,
                                @Qualifier("chapter")
                                        ChapterInterface chapter,
                                @Qualifier("levelChange")
                                        LevelChange levelChange,
                                @Qualifier("levelJsonLoader")
                                        LevelLoaderInterface levelLoader,
                                @Value("${firstLevelFilePath}")
                                        String firstLevelFilePath,
                                @Value("${secondLevelFilePath}")
                                        String secondLevelFilePath,
                                ApplicationContextProvider applicationContextProvider) {
        this.drawer = drawer;
        this.chapter = chapter;
        this.levelChange = levelChange;
        this.levelLoader = levelLoader;
        this.firstLevelFilePath = firstLevelFilePath;
        this.secondLevelFilePath = secondLevelFilePath;
        this.applicationContextProvider = applicationContextProvider;

        currLevelFilePath = firstLevelFilePath;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initMenu();
        initCanvas();

        levelChange.changeLevelByFlags(firstLevelFilePath, false, false);
    }

    @SneakyThrows
    private void initCanvas() {
        canvas.setWidth(800);
        canvas.setHeight(800);
    }

    private void initMenu() {
        EventHandler<KeyEvent> controlsEventHandler = event -> {
            KeyCode keyCode = event.getCode();
            Vector<Entity> entityVector = curr_level.getLevelEntities().getEntityVector();

            if (keyCode == ControlsCodes.Jump.getKeyCode()){
                for (Entity entity : entityVector)
                    entity.controllability.doThisOperation(ControlsCodes.Jump);
            }
            if (keyCode == ControlsCodes.Left.getKeyCode()){
                for (Entity entity : entityVector)
                    entity.controllability.doThisOperation(ControlsCodes.Left);
            }
            if (keyCode == ControlsCodes.Right.getKeyCode()){
                for (Entity entity : entityVector)
                    entity.controllability.doThisOperation(ControlsCodes.Right);
            }
            if (keyCode.equals(KeyCode.E)){
                this.setStage(GameMenuController.class);
            }
        };
        ToMenu.setOnKeyPressed(controlsEventHandler);
    }

    ApplicationContextProvider applicationContextProvider;

    @SuppressWarnings("SameParameterValue")
    @SneakyThrows
    private void setStage(Class<?> controllerClass){
        FxWeaver fxWeaver
                = (applicationContextProvider.getApplicationContext()).getBean(FxWeaver.class);
        Stage stage = new Stage();

        Parent root = fxWeaver.loadView(controllerClass);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    @SuppressWarnings("SameParameterValue")
    @SneakyThrows
    private void setScene(Class<?> controllerClass){
        this.isStarted = false;

        FxWeaver fxWeaver
                = (applicationContextProvider.getApplicationContext()).getBean(FxWeaver.class);
        Stage stage = (Stage) canvas.getScene().getWindow();

        Parent root = fxWeaver.loadView(controllerClass);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}