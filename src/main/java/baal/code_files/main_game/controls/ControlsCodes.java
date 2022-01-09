package baal.code_files.main_game.controls;

import javafx.scene.input.KeyCode;
import lombok.Getter;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

@Getter
public enum ControlsCodes {
    Jump(),
    Left(),
    Right;

    private final KeyCode keyCode;
    private final Map<String, Object> map;
    {
        Yaml yaml = new Yaml();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("src/main/resources/baal/code_files/main_game/controls/controls");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        map = yaml.load(inputStream);
    }
    ControlsCodes() {
        this.keyCode = KeyCode.getKeyCode((String) map.get(this.name()));
    }
}