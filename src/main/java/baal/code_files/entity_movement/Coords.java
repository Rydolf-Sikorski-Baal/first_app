package baal.code_files.entity_movement;

import java.awt.*;
import java.util.Vector;

public class Coords {
    public final Vector<Point> InnerCoords;

    public final Vector<Point> AdjacentUpCoords;
    public final Vector<Point> AdjacentLeftCoords;
    public final Vector<Point> AdjacentRightCoords;
    public final Vector<Point> AdjacentDownCoords;

    Coords(Vector<Point> _InnerCoords,
           Vector<Point> _AdjacentUpCoords,
           Vector<Point> _AdjacentLeftCoords,
           Vector<Point> _AdjacentRightCoords,
           Vector<Point> _AdjacentDownCoords) {
        InnerCoords = _InnerCoords;

        AdjacentUpCoords = _AdjacentUpCoords;
        AdjacentLeftCoords = _AdjacentLeftCoords;
        AdjacentRightCoords = _AdjacentRightCoords;
        AdjacentDownCoords = _AdjacentDownCoords;
    }
}
