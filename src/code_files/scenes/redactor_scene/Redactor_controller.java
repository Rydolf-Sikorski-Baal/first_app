package code_files.scenes.redactor_scene;

import code_files.logic.Redactor;
import code_files.logic.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import code_files.logic.DrawCanvas;
import code_files.logic.Level;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Redactor_controller implements Initializable{
    private Redactor redactor;
    private Level level;
    private DrawCanvas draw;
    private SceneSwitcher sceneSwitcher;

    private static int row, column;
    private double cellHeight, cellWidth;

    @FXML
    Button ToMenu;

    @FXML
    Button RedactorButton;

    @FXML
    Canvas RedactorMap;

    @FXML
    ComboBox<Integer> RedactorComboBox;

    @FXML
    ComboBox<Integer> RedactorCellComboBox;

    @FXML
    Button SetLevel;

    public Redactor_controller() throws IOException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initRedactorButton();
        try {
            initRedactor(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initRedactor(int number) throws IOException {
        redactor = new Redactor(number);
        level = new Level(number);
        draw = new DrawCanvas();
        sceneSwitcher = new SceneSwitcher();

        initRedactorMap();
        initSetLevel();
        initRedactorComboBox();
        initRedactorCellComboBox();
        initToMenu();
    }

    private void initToMenu() {
        ToMenu.setText("ToMenu");
        ToMenu.setPrefSize(200, 50);

        EventHandler<KeyEvent> SetMenuEventHandler = event -> {
            KeyCode code = event.getCode();

            if (    code == KeyCode.ENTER){
                sceneSwitcher.setMenuScene(event);
            }
        };

        ToMenu.setOnKeyPressed(SetMenuEventHandler);
    }

    private void initRedactorCellComboBox(){
        ObservableList<Integer> chose_level = FXCollections.observableArrayList();

        for (int i = 0; i <= 3; i++)
            chose_level.add(i);

        RedactorCellComboBox.getItems().addAll(chose_level);
        RedactorCellComboBox.setValue(1);
    }

    private void initRedactorComboBox(){
        ObservableList<Integer> chose_level = FXCollections.observableArrayList();

        for (int i = 1; i <= level.number_of_levels; i++)
            chose_level.add(i);

        RedactorComboBox.getItems().addAll(chose_level);
        RedactorComboBox.setValue(1);
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
        row = level.row;
        column = level.column;

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        RedactorMap.setHeight(size.height * 0.5);
        RedactorMap.setWidth(size.width * 0.5);

        this.cellHeight = RedactorMap.getHeight() / (row + 2);
        this.cellWidth = RedactorMap.getWidth() / (column + 2);

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

    private void initRedactorButton(){
        RedactorButton.setText("Chose level");

        RedactorButton.setPrefSize(200, 50);

        RedactorButton.setOnAction(actionEvent ->{
            try {
                redactor = new Redactor(RedactorComboBox.getValue());
                level = new Level(RedactorComboBox.getValue());

                initRedactorMap();
                initSetLevel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
