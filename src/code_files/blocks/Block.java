package code_files.blocks;

import code_files.entities.Entity;
import code_files.interfaces.ChangingSpeed;
import code_files.interfaces.Named;
import code_files.interfaces.Visible;
import javafx.scene.paint.Color;

import java.util.Vector;

public abstract class Block implements ChangingSpeed, Named, Visible {
    public int number;

    abstract public void effect(Entity entity);
}
