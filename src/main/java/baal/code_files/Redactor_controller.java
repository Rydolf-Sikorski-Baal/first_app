package baal.code_files;

import baal.code_files.level_system.level.Level;
import baal.code_files.logic.Redactor;
import baal.code_files.logic.SceneSwitcher;
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
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("redactor.fxml")
public class Redactor_controller implements Initializable{

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
