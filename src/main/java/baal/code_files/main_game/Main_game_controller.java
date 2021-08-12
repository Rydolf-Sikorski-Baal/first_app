package baal.code_files.main_game;

import baal.ApplicationContextProvider;
import baal.code_files.Menu_controller;
import baal.code_files.graphics_system.Drawer;
import baal.code_files.chapter_system.Chapter;
import baal.code_files.level_system.level.Level;
import baal.code_files.level_system.load_system.LevelLoaderInterface;
import baal.code_files.main_game.threads.EntityMovementThread;
import baal.code_files.main_game.threads.LevelLoadThread;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("main_game.fxml")
public class Main_game_controller implements Initializable {
    public volatile Level curr_level;
    public volatile boolean isStarted;
    public volatile Chapter chapter;
    private final Drawer drawer;

    private final LevelLoaderInterface levelLoader;
    private final String firstLevelFilePath;

    private final EntityMovementThread entityMovementThread;
    private final LevelLoadThread levelLoadThread;

    @FXML
    Button ToMenu;

    @FXML
    Canvas canvas;

    public Main_game_controller(Drawer drawer,
                                EntityMovementThread entityMovementThread,
                                LevelLoadThread levelLoadThread,
                                Chapter chapter,
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

        canvas.setWidth(400);
        canvas.setHeight(400);

        drawer.drawThisLevel(canvas, curr_level);
    }

    private void initMenu() {
        ToMenu.setText("Menu");
        ToMenu.setOnAction(event -> setScene(Menu_controller.class));
    }

    private void startLevel(){
        if (curr_level == null) throw new RuntimeException("не загружен уровень");

        levelLoadThread.start();
        entityMovementThread.start();
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