package code_files.interfaces;

import java.awt.*;
import java.util.Vector;

public interface PositionInfoInterf {
    public Vector<Point> getCurrentInnerCoords();

    public Vector<Point> getCurrentAdjacentUpCoords();
    public Vector<Point> getCurrentAdjacentLeftCoords();
    public Vector<Point> getCurrentAdjacentRightCoords();
    public Vector<Point> getCurrentAdjacentDownCoords();

    public Vector<Point> getNextInnerCoords();

    public Vector<Point> getNextAdjacentUpCoords();
    public Vector<Point> getNextAdjacentLeftCoords();
    public Vector<Point> getNextAdjacentRightCoords();
    public Vector<Point> getNextAdjacentDownCoords();
}
