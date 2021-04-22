package code_files.scenes.main_game;

import code_files.entities.Entity;
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
    private Entity hero;

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
        hero = new Entity(cellHeight, cellWidth);
        hero.setPosition(curr_level.hero_start_x, curr_level.hero_start_y);

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

                    draw.drawCell(Map, hero.position_y, hero.position_x, Color.WHITE,
                                  hero.width, hero.height);

                    if (code == KeyCode.A) {
                        if (!Collision.IsBehindLeftWall(cellWidth, cellHeight, hero, curr_level)) {
                            hero.pulse.changePulseY(-10);
                        }
                    }
                    if (code == KeyCode.D) {
                        if (!Collision.IsBehindRightWall(cellWidth, cellHeight, hero, curr_level)) {
                            hero.pulse.changePulseY(10);
                        }
                    }

                    if (code == KeyCode.SPACE){
                        if (!Collision.IsUnderRoof(cellWidth, cellHeight, hero, curr_level)
                        && Collision.IsOnSurface(cellWidth,cellHeight,hero,curr_level))
                            hero.pulse.changePulseX(-400);
                    }
                }
            }
        };

        ToMenu.setOnKeyPressed(heroMoveEventHandler);
    }

    class ObjectsThread extends Thread{
        public void checkPulse(Entity entity){

        }

        @Override
        public void run(){
            while(isStarted) {
                if (!Collision.IsOnSurface(cellWidth, cellHeight, hero, curr_level))
                    hero.pulse.changePulseX(0.1);

                //сюда вставить трение

                for (Entity entity : curr_level.list_of_entities) {
                    checkPulse(entity);

                    double delta_y = cellWidth - entity.width;
                    double delta_x = cellHeight - entity.height;

                    draw.drawCell(Map, entity.position_y,
                            entity.position_x,
                            Color.WHITE,
                            hero.width, hero.height);
                    entity.move();
                    draw.drawEntity(Map, entity, cellWidth, cellHeight);

                    //почему оно не работает?

                    Vector<Integer> bottom_blocks = Collision.getBottomBlocksIndexes(cellWidth, cellHeight,
                            entity, curr_level);

                    for (Integer block_ind : bottom_blocks) {
                        entity.pulse.setPulseX(Blocks.values()[block_ind].getNewSpeedX(entity.pulse.pulse_x));
                        entity.pulse.setPulseY(Blocks.values()[block_ind].getNewSpeedY(entity.pulse.pulse_y));
                    }
                }
            }
            try {
                sleep(10*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                isStarted = false;
            }
        }
    }
}