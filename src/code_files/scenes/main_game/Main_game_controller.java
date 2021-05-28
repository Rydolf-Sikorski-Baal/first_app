package code_files.scenes.main_game;

import code_files.PointDouble;
import code_files.entities.entities_tree.Entity;
import code_files.entities.entities_tree.Hero;
import code_files.entities.movement_tree.AccordingToSpeed;
import code_files.entities.shape_tree.Rectangle;
import code_files.entity_movement.Collision;
import code_files.entity_movement.EntityMovement;
import code_files.level_system.Level;
import code_files.logic.DrawCanvas;
import code_files.logic.SceneSwitcher;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Main_game_controller implements Initializable {
    private int curr_level_number;
    private volatile Level curr_level;
    private volatile boolean isStarted;
    private DrawCanvas draw = new DrawCanvas();
    private Hero hero;

    private SceneSwitcher sceneSwitcher = new SceneSwitcher();
    private ObjectsThread objectsThread = new ObjectsThread();

    @FXML
    Button ToMenu;

    @FXML
    Canvas Map;

    @FXML
    Label checkThread;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            startGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startGame() throws IOException {
        checkThread.setText("0");

        isStarted = true;
        curr_level_number = 1;

        initToMenu();

        startLevel(curr_level_number);
        collision = Collision.getCollision(cellHeight, cellWidth);
        entityMovement = EntityMovement.getEntityMovement(cellHeight, cellWidth);

        objectsThread.start();
    }

    private void startLevel(int number) throws IOException {
        curr_level = new Level(number);

        row = curr_level.row;
        column = curr_level.column;
        initCanvas();

        curr_level.buildLevel(cellHeight, cellWidth);

        initHero();

        draw.drawLevel(Map, cellWidth, cellHeight, curr_level, curr_level.list_of_entities);
    }

    private void initHero() {
        hero = new Hero(new Rectangle(),
                new PointDouble(curr_level.hero_start_x, curr_level.hero_start_y),
                new AccordingToSpeed());

        curr_level.list_of_entities = new Entity[1];
        curr_level.list_of_entities[0] = hero;
    }

    private void initCanvas() {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        Map.setWidth();
        Map.setHeight();

        this.cellHeight = Map.getHeight() / ();
        this.cellWidth = Map.getWidth() / ();
    }

    private void initToMenu() {
        ToMenu.setText("ToMenu");
        ToMenu.setPrefSize();

        EventHandler<KeyEvent> heroMoveEventHandler = event -> {
            KeyCode code = event.getCode();

            if (    code == KeyCode.ENTER){
                isStarted = false;
                sceneSwitcher.setMenuScene(event);
            }

            if (    code == KeyCode.SPACE ||
                    code == KeyCode.A || code == KeyCode.D) {

                if (isStarted) {

                    checkThread.setText("0");
                    AccordingToSpeed accordingToSpeed = (AccordingToSpeed) hero.movement;

                    if (code == KeyCode.A) {
                        accordingToSpeed.changeSpeedY();
                    }
                    if (code == KeyCode.D) {
                        accordingToSpeed.changeSpeedY();
                    }

                    if (code == KeyCode.SPACE){
                        accordingToSpeed.changeSpeedX();
                    }
                }
            }
        };

        ToMenu.setOnKeyPressed(heroMoveEventHandler);
    }

    EntityMovement entityMovement;
    class ObjectsThread extends Thread{
        @Override
        public void run(){
            while(isStarted) {
                ((AccordingToSpeed)hero.movement).changeSpeedX(7);

                draw.drawCell(Map, hero.position.getY(), hero.position.getX(),
                        Color.WHITE, ((Rectangle)hero.shape).y_size, ((Rectangle)hero.shape).x_size);
                entityMovement.moveTick(hero, curr_level);
                draw.drawEntity(Map, hero, cellWidth, cellHeight);


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