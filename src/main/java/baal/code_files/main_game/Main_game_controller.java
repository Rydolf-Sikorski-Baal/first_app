package baal.code_files.main_game;

import baal.ApplicationContextProvider;
import baal.code_files.Menu_controller;
import baal.code_files.chapter_system.ChapterInterface;
import baal.code_files.entities.controllability_tree.HeroControls;
import baal.code_files.entities.entities_tree.Entity;
import baal.code_files.entities.entities_tree.Hero;
import baal.code_files.entities.movement_tree.AccordingToSpeed;
import baal.code_files.entities.shape_tree.Rectangle;
import baal.code_files.graphics_system.DrawerInterface;
import baal.code_files.level_system.level.Level;
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
import javafx.stage.Stage;
import lombok.Getter;
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
public class Main_game_controller implements Initializable {
    public volatile Level curr_level;
    public volatile boolean isStarted;
    public volatile ChapterInterface chapter;
    private final DrawerInterface drawer;

    private final LevelLoaderInterface levelLoader;
    private final String firstLevelFilePath;

    private final EntityMovementThread entityMovementThread;
    private final LevelLoadThread levelLoadThread;
    private final TriggerCheckThread triggerCheckThread;

    @FXML
    Button ToMenu;

    @FXML
    Canvas canvas;

    @FXML
    public volatile Label label;
    public volatile int tik_number = 0;

    public Main_game_controller(@Qualifier("drawer")
                                        DrawerInterface drawer,
                                @Qualifier("entityMovementThread")
                                        EntityMovementThread entityMovementThread,
                                @Qualifier("levelLoadThread")
                                        LevelLoadThread levelLoadThread,
                                @Qualifier("triggerCheckThread")
                                        TriggerCheckThread triggerCheckThread,
                                @Qualifier("chapter")
                                        ChapterInterface chapter,
                                @Qualifier("levelJsonLoader")
                                        LevelLoaderInterface levelLoader,
                                @Value("${firstLevelFilePath}")
                                        String firstLevelFilePath,
                                ApplicationContextProvider applicationContextProvider) {
        this.drawer = drawer;
        this.entityMovementThread = entityMovementThread;
        this.levelLoadThread = levelLoadThread;
        this.chapter = chapter;
        this.levelLoader = levelLoader;
        this.firstLevelFilePath = firstLevelFilePath;
        this.triggerCheckThread = triggerCheckThread;
        this.applicationContextProvider = applicationContextProvider;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initMenu();
        initCanvas();

        startLevel();
    }

    @SneakyThrows
    private void initCanvas() {
        curr_level = levelLoader.loadLevel(firstLevelFilePath);

        Rectangle rectangle = new Rectangle();
        rectangle.setX_size(0.5);
        rectangle.setY_size(0.5);
        AccordingToSpeed accordingToSpeed = new AccordingToSpeed();
        accordingToSpeed.setSpeed_x(curr_level.getLevelSettings().getDefaultSpeedX());
        accordingToSpeed.setSpeed_y(curr_level.getLevelSettings().getDefaultSpeedY());
        Hero hero = new Hero(rectangle, accordingToSpeed, new HeroControls());
        hero.setPosition(curr_level.getLevelSettings().getHeroStartX(),
                         curr_level.getLevelSettings().getHeroStartY());
        hero.connect();

        Vector<Entity> entityVector = new Vector<>();
        entityVector.add(hero);
        curr_level.getLevelEntities().setEntityVector(entityVector);

        canvas.setWidth(400);
        canvas.setHeight(400);
    }

    private void initMenu() {
        ToMenu.setText("Menu");
        ToMenu.setOnMousePressed(event -> setScene(Menu_controller.class));

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
        };
        ToMenu.setOnKeyPressed(controlsEventHandler);

        label.setText(String.valueOf(tik_number));
    }

    private void startLevel(){
        if (curr_level == null) throw new RuntimeException("не загружен уровень");

        this.isStarted = true;

        levelLoadThread.start();
        entityMovementThread.start();
        triggerCheckThread.start();
    }

    ApplicationContextProvider applicationContextProvider;
    @SuppressWarnings("SameParameterValue")
    private void setScene(Class<?> controllerClass){
        FxWeaver fxWeaver
                = (applicationContextProvider.getApplicationContext()).getBean(FxWeaver.class);
        Stage stage = (Stage) canvas.getScene().getWindow();

        Parent root = fxWeaver.loadView(controllerClass);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}