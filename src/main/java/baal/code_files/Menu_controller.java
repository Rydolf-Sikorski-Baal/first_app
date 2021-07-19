package baal.code_files;

import baal.code_files.logic.SceneSwitcher;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("menu")
public class Menu_controller implements Initializable{
    private final SceneSwitcher sceneSwitcher = new SceneSwitcher();

    @FXML
    Button Start;

    @FXML
    Button Exit;

    @FXML
    Button RedactorButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) { initMenu();}

    private void initMenu(){
        initStart();
        initExit();
        initRedactorButton();
    }

    private void initStart(){
        Start.setText("START");
        Start.setPrefSize(200, 50);

        this.Start.setOnAction(sceneSwitcher::setGameScene);
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

    private void initRedactorButton() {
        RedactorButton.setText("Redactor");
        RedactorButton.setPrefSize(200, 50);

        this.RedactorButton.setOnAction(sceneSwitcher::setRedactorScene);
    }
}