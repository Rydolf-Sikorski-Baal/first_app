package code_files;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.geom.Point2D;

@AllArgsConstructor
@Getter
public class PointDouble extends Point2D {
    double x, y;

    @Override
    public void setLocation(double _x, double _y) {
        x = _x;
        y = _y;
    }
}
