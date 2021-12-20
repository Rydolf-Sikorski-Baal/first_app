package baal.code_files;

import baal.ApplicationContextProvider;
import baal.code_files.main_game.Main_game_controller;
import baal.code_files.redactor.Redactor_controller;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("menu.fxml")
public class Menu_controller implements Initializable{
    Menu_controller(ApplicationContextProvider applicationContextProvider){
        this.applicationContextProvider = applicationContextProvider;
    }

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

        this.Start.setOnAction(event -> setScene(Main_game_controller.class));
    }

    private void initExit(){
        Exit.setText("EXIT");
        Exit.setPrefSize(200, 50);

        Exit.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> System.exit(0));

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

        this.RedactorButton.setOnAction(event -> setScene(Redactor_controller.class));
    }

    ApplicationContextProvider applicationContextProvider;
    private void setScene(Class<?> controllerClass){
        FxWeaver fxWeaver
                = (applicationContextProvider.getApplicationContext()).getBean(FxWeaver.class);
        Stage stage = (Stage) Start.getScene().getWindow();

        Parent root = fxWeaver.loadView(controllerClass);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }
}