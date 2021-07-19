package baal.code_files.logic;

import baal.code_files.main_game.Main_game_controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.EventObject;

// отвечает за смену сцен

public class SceneSwitcher {
    public static final String menu_scene_name = "menu";
    public static final String game_scene_name = "main_game";
    public static final String redactor_scene_name = "redactor_scene";

    public static final double menu_scene_width = 400, menu_scene_height = 300;
    public static final double game_scene_width = 800, game_scene_height = 800;
    public static final double redactor_scene_width = 200, redactor_scene_height = 100;

    private static class ResultInfo{
        FXMLLoader loader;
        Stage stage;
        Scene scene;

        ResultInfo(FXMLLoader loader) {
            this.loader = loader;
        }

        <T extends Main_game_controller> T getController() {
            return loader.getController();
        }

        Parent getParent() {
            try {
                return loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private ResultInfo findParentBy(String sceneName) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                String.format("/code_files/scenes/%s/", sceneName) + sceneName + ".fxml"
        ));
        return new ResultInfo(loader);
    }

    private ResultInfo createScene(String sceneName, double width, double height) {
        ResultInfo resultInfo = findParentBy(sceneName);

        resultInfo.scene = new Scene(
                resultInfo.getParent(),
                width, height
        );

        return resultInfo;
    }

    private Stage findStage(EventObject event){
        return (Stage) ((Node)event.getSource()).getScene().getWindow();
    }

    public void setScene(EventObject event, Scene scene) {
        Stage stage = findStage(event);

        stage.setScene(scene);
        stage.setResizable(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        stage.setX(screenSize.width / 2.0 - scene.getWidth() / 2);
        stage.setY(screenSize.height / 2.0 - scene.getHeight() / 2);
    }

    public void setScene(EventObject event, String scene_name, double scene_width, double scene_height){
        ResultInfo info = createScene(scene_name, scene_width, scene_height);

        Scene scene = info.scene;

        setScene(event, scene);
    }

    public void setGameScene(EventObject event) {
        setScene(event, game_scene_name, game_scene_width, game_scene_height);
    }

    public void setMenuScene(EventObject event) {
        setScene(event, menu_scene_name, menu_scene_width, menu_scene_height);
    }

    public void setRedactorScene(EventObject event) {
        setScene(event, redactor_scene_name, redactor_scene_width, redactor_scene_height);
    }
}
