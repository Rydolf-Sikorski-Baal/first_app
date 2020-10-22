package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.*;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.EventObject;
import java.util.ResourceBundle;
import java.util.Vector;

import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;

public class Main_game_controller implements Initializable {
    private int curr_level_number;
    private Level curr_level;
    private boolean isStarted;
    private DrawCanvas draw = new DrawCanvas();
    private Entity hero;

    private static int row, column;
    private double cellHeight, cellWidth;

    private SceneSwitcher sceneSwitcher = new SceneSwitcher();

    @FXML
    Button ToMenu;

    @FXML
    Canvas Map;

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
        isStarted = true;
        curr_level_number = 1;

        initCanvas();
        initToMenu();

        startLevel(curr_level_number);

        draw.drawLevel(Map, cellWidth, cellHeight, curr_level, curr_level.list_of_entities);
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
                sceneSwitcher.setMenuScene(event);
            }

            if (    code == KeyCode.SPACE ||
                    code == KeyCode.A || code == KeyCode.D) {

                if (isStarted) {

                    draw.drawCell(Map, hero.position_y, hero.position_x, Color.WHITE,
                                  hero.width, hero.height);

                    if (code == KeyCode.A) {
                        if (!Collision.IsBehindLeftWall(cellWidth, cellHeight, hero, curr_level)) {
                            hero.moveEntity(0, -10);
                        }
                    }
                    if (code == KeyCode.D) {
                        if (!Collision.IsBehindRightWall(cellWidth, cellHeight, hero, curr_level)) {
                            hero.moveEntity(0, 10);
                        }
                    }

                    if (code == KeyCode.SPACE){
                        if (!Collision.IsUnderRoof(cellWidth, cellHeight, hero, curr_level))
                            hero.moveEntity(-1, 0);
                    }

                    if (code == KeyCode.ENTER) {
                        try {
                            startGame();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    /*if (code != KeyCode.SPACE) {
                        if (!Collision.IsOnSurface(hero.width, hero.height,
                                                    hero, curr_level))
                            hero.moveEntity(10, 0);
                    }*/

                    Vector<int[]> curr_positions = Collision.getListOfPositions(hero.position_x, hero.position_y,
                                                                              cellWidth, cellHeight, hero);
                    for (int[] now : curr_positions) {
                        if (CheckPosition.Check(curr_level, now[0], now[1]) == 2)
                            initHero();

                        if (CheckPosition.Check(curr_level, now[0], now[1]) == 3) {
                            try {
                                curr_level_number++;
                                startLevel(curr_level_number);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    draw.drawEntity(Map, curr_level, hero, cellWidth, cellHeight);

                }
            }
        };

        ToMenu.setOnKeyPressed(heroMoveEventHandler);
    }
}