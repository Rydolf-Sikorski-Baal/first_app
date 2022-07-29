package baal.code_files.chapter_selection;

import baal.ApplicationContextProvider;
import baal.code_files.Menu_controller;
import baal.code_files.main_game.GameModel;
import baal.code_files.main_game.Main_game_controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
@FxmlView("chapterSelection.fxml")
public class ChapterSelectionController implements Initializable {
    @FXML
    public ComboBox<String> comboBox;
    @FXML
    public Button toMenu;
    @FXML
    public Button select;

    private final GameModel gameModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File folder = new File("src/main/resources/baal/code_files/chapter");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < Objects.requireNonNull(listOfFiles).length; i++) {
            if (listOfFiles[i].isFile()) {
                comboBox.getItems().add(listOfFiles[i].getName());
            }
        }

        this.toMenu.setText("To Menu");
        this.select.setText("select");

        this.toMenu.setOnAction(event -> setScene(Menu_controller.class));
        this.comboBox.setOnAction(event -> gameModel
                .chapter
                .setChapterName(this.comboBox.getValue()));
        this.select.setOnAction(event -> setScene(Main_game_controller.class));
    }

    final ApplicationContextProvider applicationContextProvider;
    private void setScene(Class<?> controllerClass){
        FxWeaver fxWeaver
                = (applicationContextProvider.getApplicationContext()).getBean(FxWeaver.class);
        Stage stage = (Stage) comboBox.getScene().getWindow();

        Parent root = fxWeaver.loadView(controllerClass);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }
}
