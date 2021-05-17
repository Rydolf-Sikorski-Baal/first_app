package code_files.entities_collision;

import code_files.interfaces.PositionInfoInterf;
import lombok.AllArgsConstructor;

import java.awt.*;
import java.util.Vector;

@AllArgsConstructor
public class PositionInfo implements PositionInfoInterf {
    private final Vector<Point> currentInnerCoords;

    private final Vector<Point> currentAdjacentUpCoords;
    private final Vector<Point> currentAdjacentLeftCoords;
    private final Vector<Point> currentAdjacentRightCoords;
    private final Vector<Point> currentAdjacentDownCoords;

    private final Vector<Point> nextTickInnerCoords;

    private final Vector<Point> nextAdjacentUpCoords;
    private final Vector<Point> nextAdjacentLeftCoords;
    private final Vector<Point> nextAdjacentRightCoords;
    private final Vector<Point> nextAdjacentDownCoords;

    @Override
    public Vector<Point> getCurrentInnerCoords() {
        return currentInnerCoords;
    }

    @Override
    public Vector<Point> getCurrentAdjacentUpCoords() {
        return currentAdjacentUpCoords;
    }
    @Override
    public Vector<Point> getCurrentAdjacentLeftCoords() {
        return currentAdjacentLeftCoords;
    }
    @Override
    public Vector<Point> getCurrentAdjacentRightCoords() {
        return currentAdjacentRightCoords;
    }
    @Override
    public Vector<Point> getCurrentAdjacentDownCoords() {
        return currentAdjacentDownCoords;
    }

    @Override
    public Vector<Point> getNextInnerCoords() {
        return nextTickInnerCoords;
    }

    @Override
    public Vector<Point> getNextAdjacentUpCoords() {
        return nextAdjacentUpCoords;
    }
    @Override
    public Vector<Point> getNextAdjacentLeftCoords() {
        return nextAdjacentLeftCoords;
    }
    @Override
    public Vector<Point> getNextAdjacentRightCoords() {
        return nextAdjacentRightCoords;
    }
    @Override
    public Vector<Point> getNextAdjacentDownCoords() {
        return nextAdjacentDownCoords;
    }
}