package code_files.entities_collision;

import lombok.AllArgsConstructor;

import javax.naming.OperationNotSupportedException;
import java.awt.geom.Point2D;

@AllArgsConstructor
public class PointDouble extends Point2D {
    double x, y;

    @Override
    public double getX() {
        return x;
    }
    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setLocation(double _x, double _y) {
        x = _x;
        y = _y;
    }
}
