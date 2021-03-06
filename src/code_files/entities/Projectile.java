package code_files.entities;

import code_files.entities.Entity;
import code_files.interfaces.Movable;

public class Projectile extends Entity implements Movable {
    public double start_x, start_y;

    public Projectile(double _cellHeight, double _cellWidth,
                      double _start_x, double _start_y) {
        super(_cellHeight, _cellWidth);

        start_x = _start_x;
        start_y = _start_y;
    }
}
