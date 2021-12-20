package baal.code_files.main_game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("game_menu.fxml")
public class GameMenuController implements Initializable {
    @FXML
    public Button ToMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToMenu.setOnAction(event -> System.exit(0));
    }
}
