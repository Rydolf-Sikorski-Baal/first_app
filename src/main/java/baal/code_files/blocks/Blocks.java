package baal.code_files.blocks;

import baal.code_files.interfaces.BlocksVis;
import baal.code_files.interfaces.ChangingSpeed;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import lombok.SneakyThrows;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

public enum Blocks implements ChangingSpeed, BlocksVis {
    Air(),
    Mud(),
    Ice(),
    ExternalBlock();

    private final Map<String, Object> map;
    {
        Yaml yaml = new Yaml();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File("src/main/resources/baal/code_files/blocks/blocks"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        map = yaml.load(inputStream);
    }

    @SneakyThrows
    Blocks() {
        @SuppressWarnings("unchecked")
        Map<String, Object> block = (Map<String, Object>) map.get(this.name());

        this.isPassable = (boolean) block.get("isPassable");
        this.xSpeedChangeCoefficient = (double) block.get("xSpeedChangeCoefficient");
        this.ySpeedChangeCoefficient = (double) block.get("ySpeedChangeCoefficient");

        @SuppressWarnings("unchecked")
        Map<String, Object> color = (Map<String, Object>) block.get("color");
        this.colorRed = (double) color.get("Red");
        this.colorGreen = (double) color.get("Green");
        this.colorBlue = (double) color.get("Blue");
        this.opacity = (double) color.get("opacity");
    }

    private final boolean isPassable;
    private final double xSpeedChangeCoefficient, ySpeedChangeCoefficient;
    private final double colorRed, colorGreen, colorBlue;
    private final double opacity;

    public boolean isPassable() {return isPassable;}

    @Override
    public double getNewSpeedX(double current_x) {
        return xSpeedChangeCoefficient * current_x;
    }

    @Override
    public double getNewSpeedY(double current_y) {
        return ySpeedChangeCoefficient * current_y;
    }

    private Image image;
    @Override
    public void drawYourself(javafx.scene.canvas.Canvas canvas, double top_left_corner_x, double top_left_corner_y,
                             double cellHeight, double cellWidth) {
        Color color = new Color(this.colorRed, this.colorGreen, this.colorBlue,
                this.opacity);

        if (image == null)
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("2bd433.jpeg")));

        if (!this.equals(Blocks.Air))
            canvas.getGraphicsContext2D().drawImage(image, top_left_corner_y * cellWidth, top_left_corner_x * cellHeight,
                cellWidth, cellHeight);


    }
}