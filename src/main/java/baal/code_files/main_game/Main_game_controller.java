package baal.code_files.main_game;

import baal.code_files.graphics_system.Drawer;
import baal.code_files.level_system.Chapter;
import baal.code_files.level_system.level.Level;
import baal.code_files.main_game.threads.EntityMovementThread;
import baal.code_files.main_game.threads.LevelLoadThread;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxmlView;
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

    private final EntityMovementThread entityMovementThread;
    private final LevelLoadThread levelLoadThread;

    @FXML
    Button ToMenu;

    @FXML
    Canvas canvas;

    @FXML
    Label checkThread;

    public Main_game_controller(Drawer drawer,
                                EntityMovementThread entityMovementThread,
                                LevelLoadThread levelLoadThread,
                                Chapter chapter) {
        this.drawer = drawer;
        this.entityMovementThread = entityMovementThread;
        this.levelLoadThread = levelLoadThread;
        this.chapter = chapter;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startLevel();
    }

    public void setLevel(Level level){
        this.curr_level = level;
    }

    private void startLevel(){
        //if (curr_level == null) throw new RuntimeException("не загружен уровень");

        levelLoadThread.start();
        entityMovementThread.start();
    }
}