package code_files.scenes.main_game;

import code_files.entities_collision.Collision;
import code_files.entities_collision.PointDouble;
import code_files.entities_collision.PositionInfo;
import code_files.entities_collision.entities_tree.Entity;
import code_files.entities_collision.entities_tree.Hero;
import code_files.entities_collision.movement_tree.AccordingToSpeed;
import code_files.entities_collision.shape_tree.Rectangle;
import code_files.logic.*;
import code_files.main.SceneSwitcher;
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
import java.util.Vector;

public class Main_game_controller implements Initializable {
    private int curr_level_number;
    private volatile Level curr_level;
    private volatile boolean isStarted;
    private DrawCanvas draw = new DrawCanvas();
    private Hero hero;

    private Collision collision = new Collision();

    private static int row, column;
    private double cellHeight, cellWidth;

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

        //сюда вставляется тик. ну либо мне придётся переписывать контроллер

        /*Timeline tick = TimelineBuilder.create().keyFrames(

        )*/
    }

    private void startGame() throws IOException {
        checkThread.setText("0");


        isStarted = true;
        curr_level_number = 1;

        initCanvas();
        initToMenu();

        startLevel(curr_level_number);

        draw.drawLevel(Map, cellWidth, cellHeight, curr_level, curr_level.list_of_entities);

        objectsThread.start();
    }

    private void startLevel(int number) throws IOException {
        curr_level = new Level(number);

        row = curr_level.row;
        column = curr_level.column;

        initCanvas();

        initHero();

        draw.drawLevel(Map, cellWidth, cellHeight, curr_level, curr_level.list_of_entities);


    }

    private void initHero() {
        hero = new Hero(new Rectangle(30, 30),
                new PointDouble(curr_level.hero_start_x, curr_level.hero_start_y),
                new AccordingToSpeed());
        hero.setPosition(100, 200);

        curr_level.list_of_entities = new Entity[1];
        curr_level.list_of_entities[0] = hero;
    }

    private void initCanvas() {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        Map.setWidth(600);
        Map.setHeight(400);

        this.cellHeight = Map.getHeight() / (row + 2);
        this.cellWidth = Map.getWidth() / (column + 2);
    }

    private void initToMenu() {
        ToMenu.setText("ToMenu");
        ToMenu.setPrefSize(200, 50);

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
                        accordingToSpeed.changeSpeedY(-10);
                    }
                    if (code == KeyCode.D) {
                        accordingToSpeed.changeSpeedY(10);
                    }

                    if (code == KeyCode.SPACE){
                        accordingToSpeed.changeSpeedX(-400);
                    }
                }
            }
        };

        ToMenu.setOnKeyPressed(heroMoveEventHandler);
    }

    class ObjectsThread extends Thread{
        @Override
        public void run(){
            while(isStarted) {
                AccordingToSpeed accordingToSpeed = (AccordingToSpeed) hero.movement;
                accordingToSpeed.changeSpeedX(10);

                Collision collision = new Collision();
                PositionInfo positionInfo = collision.getPositionInfo(hero, cellHeight, cellWidth);

                Vector<Point> bottom_coords = positionInfo.getCurrentAdjacentDownCoords();
                for (Point curr_coord : bottom_coords){
                    if (Blocks.values()[curr_level.level[curr_coord.x][curr_coord.y]] != Blocks.Air){
                        ((AccordingToSpeed) hero.movement).nullifyMinusX();
                        //((AccordingToSpeed) hero.movement).nullifyPlusX();
                    }
                }

                draw.drawCell(Map, hero.position.getY(),
                        hero.position.getX(),
                        Color.WHITE,
                        30, 30);
                hero.moveTick();
                draw.drawEntity(Map, hero, cellWidth, cellHeight);

                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    isStarted = false;
                }
            }
        }
    }
}