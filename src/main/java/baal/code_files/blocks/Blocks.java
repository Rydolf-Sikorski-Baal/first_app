package baal.code_files.blocks;

import baal.code_files.interfaces.BlocksVis;
import baal.code_files.interfaces.ChangingSpeed;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.SneakyThrows;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public enum Blocks implements ChangingSpeed, BlocksVis {
    Air(),
    Mud(),
    Ice(),
    ExternalBlock();

    @SneakyThrows
    Blocks() {
        Yaml yaml = new Yaml();
        InputStream inputStream = new FileInputStream(new File("src/main/resources/baal/code_files/blocks/blocks"));
        Map<String, Object> map = yaml.load(inputStream);

        @SuppressWarnings("unchecked")
        Map<String, Object> block = (Map<String, Object>) map.get(this.name());

        this.isPassable = (boolean) block.get("isPassable");
        this.xSpeedChangeCoefficient = (double) block.get("xSpeedChangeCoefficient");
        this.ySpeedChangeCoefficient = (double) block.get("ySpeedChangeCoefficient");

        @SuppressWarnings("unchecked")
        Map<String, Object> color = (Map<String, Object>) block.get("color");
        this.colorRed = (int) color.get("Red");
        this.colorGreen = (int) color.get("Green");
        this.colorBlue = (int) color.get("Blue");
        this.opacity = (int) color.get("opacity");
    }

    private final boolean isPassable;
    private final double xSpeedChangeCoefficient, ySpeedChangeCoefficient;
    private final int colorRed, colorGreen, colorBlue;
    private final int opacity;

    public boolean isPassable() {return isPassable;}

    @Override
    public double getNewSpeedX(double current_x) {
        return xSpeedChangeCoefficient * current_x;
    }

    @Override
    public double getNewSpeedY(double current_y) {
        return ySpeedChangeCoefficient * current_y;
    }

    @Override
    public void drawYourself(javafx.scene.canvas.Canvas canvas, double top_left_corner_x, double top_left_corner_y,
                             double cellHeight, double cellWidth) {
        Color color = new Color(this.colorRed, this.colorGreen, this.colorBlue,
                this.opacity);

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        graphicsContext.setFill(color);
        graphicsContext.fillRect(top_left_corner_y * cellWidth, top_left_corner_x * cellHeight,
                cellWidth, cellHeight);
    }
}