package sample;

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

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.EventObject;
import java.util.ResourceBundle;

// старый контроллер - уничтожить при возможности, вероятно, при обновлении редактора

public class Controller implements Initializable{
    private static int row, column;
    private double cellHeight, cellWidth;

    private boolean isStarted = false;

    private Entity hero;
    private Level curr_level;

    private DrawCanvas draw = new DrawCanvas();

    private static int number_of_levels = 2;
    private int curr_level_number;

    private SceneSwitcher sceneSwitcher = new SceneSwitcher();

    Redactor redactor;

    @FXML
    Button Start;

    @FXML
    Button Exit;

    @FXML
    Button RedactorButton;

    @FXML
    Canvas RedactorMap;

    @FXML
    Canvas Map;

    @FXML
    ComboBox<Integer> RedactorComboBox;

    @FXML
    ComboBox<Integer> RedactorCellComboBox;

    @FXML
    Button SetLevel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initMenu();
    }

    private void startGame() throws IOException {
        isStarted = true;
        curr_level_number = 1;

        initCanvas();

        startLevel(curr_level_number);

        draw.drawLevel(Map, cellWidth, cellHeight, curr_level, curr_level.list_of_entities);
    }

    private void startLevel(int number) throws IOException {
        initCanvas();

        curr_level = new Level(number);

        row = curr_level.row;
        column = curr_level.column;

        initHero();

        draw.drawLevel(Map, cellWidth, cellHeight, curr_level, curr_level.list_of_entities);
    }

    private void initHero(){
        hero = new Entity(cellHeight, cellWidth);
        hero.setPosition(curr_level.hero_start_x, curr_level.hero_start_y);

        curr_level.list_of_entities = new Entity[1];
        curr_level.list_of_entities[0] = hero;
    }

    private void initCanvas() {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        Map.setWidth(size.width * 0.5);
        Map.setHeight(size.height * 0.5);

        this.cellHeight = Map.getHeight() / (row + 2);
        this.cellWidth = Map.getWidth() / (column + 2);
    }

    private void initMenu(){
        initStart();
        initExit();
        initRedactorButton();
        initRedactorComboBox();
    }

    private void initRedactorComboBox(){
        ObservableList<Integer> chose_level = FXCollections.observableArrayList();

        for (int i = 1; i <= number_of_levels; i++)
            chose_level.add(i);

        RedactorComboBox.getItems().addAll(chose_level);
        RedactorComboBox.setValue(1);
    }

    private void initRedactorCellComboBox(){
        ObservableList<Integer> chose_level = FXCollections.observableArrayList();

        for (int i = 0; i <= 3; i++)
            chose_level.add(i);

        RedactorCellComboBox.getItems().addAll(chose_level);
        RedactorCellComboBox.setValue(1);
    }

    private void initRedactorButton() {
        RedactorButton.setText("Redactor");
        RedactorButton.setPrefSize(200, 50);

        this.RedactorButton.setOnAction(actionEvent ->{
            sceneSwitcher.setMenuScene(actionEvent);
        });
    }

    private void initRedactor(int number) throws IOException {
        redactor = new Redactor(number);

        initRedactorMap();
        initSetLevel();
    }

    private void initSetLevel(){
        SetLevel.setText("Set Level");

        SetLevel.setPrefSize(200, 50);

        SetLevel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    redactor.setLevel();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initRedactorMap(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        RedactorMap.setHeight(size.height * 0.5);
        RedactorMap.setWidth(size.width * 0.5);

        draw.drawRedactor(RedactorMap, cellWidth, cellHeight, redactor);

        RedactorMap.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouseEvent) {
                double curr_mouse_x = mouseEvent.getY();
                double curr_mouse_y = mouseEvent.getX();

                int curr_cell_x = (int) (curr_mouse_x / cellHeight);
                int curr_cell_y = (int) (curr_mouse_y / cellWidth);

                int new_type = (int)RedactorCellComboBox.getValue();

                redactor.changeCell(curr_cell_x, curr_cell_y, new_type);
                draw.drawRedactor(RedactorMap, cellWidth, cellHeight, redactor);
            }
        });
    }

    private void initStart(){
        Start.setText("START");
        Start.setPrefSize(200, 50);

        Start.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    startGame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        EventHandler<KeyEvent> heroMoveEventHandler = event ->{
            KeyCode code = event.getCode();

            if (code == KeyCode.ENTER || code == KeyCode.SPACE ||
            code == KeyCode.A || code == KeyCode.D) {

                if (code == KeyCode.ENTER && !isStarted) {
                    try {
                        startGame();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (isStarted) {
                    int curr_x = 0;
                    int curr_y = 0;

                    draw.drawCell(Map, hero.position_y, hero.position_x, Color.WHITE, cellWidth, cellHeight);

                    if (code == KeyCode.A) {
                        int to_x = curr_x;
                        int to_y = curr_y - 1;
                        if (curr_level.IsPassable(to_x, to_y)) {
                            hero.moveEntity(0, -1);
                        }
                    }
                    if (code == KeyCode.D) {
                        int to_x = curr_x;
                        int to_y = curr_y + 1;
                        if (curr_level.IsPassable(to_x, to_y)) {
                            hero.moveEntity(0, 1);
                        }
                    }
                    if (code == KeyCode.SPACE && curr_level.level[curr_x + 1][curr_y] == 1) {
                        int to_x = curr_x - 2;
                        int to_y = curr_y;

                        if (to_x > 0) {
                            if (curr_level.level[to_x][to_y] == 1 || curr_level.level[to_x + 1][to_y] == 1) to_x++;
                            if (curr_level.level[to_x][to_y] == 1) to_x++;
                        } else {
                            to_x = curr_x;
                        }

                        hero.moveEntity(to_x - curr_x, 0);
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

                    if (code != KeyCode.SPACE) {
                        int to_x = curr_x + 1;
                        int to_y = curr_y;

                        if (curr_level.IsPassable(to_x, to_y))
                            hero.moveEntity(1, 0);
                    }

                    if (curr_level.level[0][0] == 2)
                        initHero();

                    if (curr_level.level[0][0] == 3) {
                        try {
                            curr_level_number++;
                            startLevel(curr_level_number);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    draw.drawEntity(Map, curr_level, hero, cellWidth, cellHeight);

                }
            }
        };

        Start.setOnKeyPressed(heroMoveEventHandler);
    }

    private void initExit(){
        Exit.setText("EXIT");
        Exit.setPrefSize(200, 50);

        Exit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.exit(0);
            }
        });

        EventHandler<KeyEvent> closeEventHandler = event ->{
            KeyCode code = event.getCode();

            if (code == KeyCode.ENTER){
                System.exit(0);
            }
        };

        Exit.setOnKeyPressed(closeEventHandler);
    }
}