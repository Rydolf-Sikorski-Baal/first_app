package baal.code_files;

import baal.code_files.entities.entities_tree.Entity;
import baal.code_files.entities.entities_tree.Hero;
import baal.code_files.entities.movement_tree.AccordingToSpeed;
import baal.code_files.entities.shape_tree.Rectangle;
import baal.code_files.entity_movement.Collision;
import baal.code_files.entity_movement.EntityMovement;
import baal.code_files.graphics_system.Drawer;
import baal.code_files.level_system.level.Level;
import baal.code_files.logic.SceneSwitcher;
import baal.config.SpringConfiguration;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("main_game.fxml")
public class Main_game_controller implements Initializable {
    private volatile Level curr_level;
    private volatile boolean isStarted;
    private Drawer drawer;
    private Hero hero;

    private final SceneSwitcher sceneSwitcher = new SceneSwitcher();
    private final ObjectsThread objectsThread = new ObjectsThread();

    @FXML
    Button ToMenu;

    @FXML
    Canvas canvas;

    @FXML
    Label checkThread;

    public Main_game_controller(Drawer drawer) {
        this.drawer = drawer;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            startGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
    private final EntityMovement entityMovement = context.getBean(EntityMovement.class);
    class ObjectsThread extends Thread{
        @Override
        public void run(){
            while(isStarted) {
                ((AccordingToSpeed)hero.movement).changeSpeedX(7);

                draw.drawCell(canvas, hero.position.getY(), hero.position.getX(),
                        Color.WHITE, ((baal.code_files.entities.shape_tree.Rectangle)hero.shape).y_size, ((Rectangle)hero.shape).x_size);
                entityMovement.moveTick(hero, curr_level);
                draw.drawEntity(canvas, hero, cellWidth, cellHeight);


                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    isStarted = false;
                }
            }
        }
    }
}