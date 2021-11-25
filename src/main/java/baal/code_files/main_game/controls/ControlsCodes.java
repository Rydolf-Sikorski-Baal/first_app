package baal.code_files.main_game.controls;

import javafx.scene.input.KeyCode;
import lombok.Getter;
import lombok.SneakyThrows;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

@Getter
public enum ControlsCodes {
    Jump(),
    Left(),
    Right;

    private final KeyCode keyCode;
    @SneakyThrows
    ControlsCodes() {
        Yaml yaml = new Yaml();
        InputStream inputStream = new FileInputStream(new File("src/main/resources/baal/code_files/main_game/controls/controls"));
        Map<String, Object> map = yaml.load(inputStream);

        this.keyCode = KeyCode.getKeyCode((String) map.get(this.name()));
    }
}