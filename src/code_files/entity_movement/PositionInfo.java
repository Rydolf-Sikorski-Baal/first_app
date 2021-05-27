package code_files.entity_movement;

import code_files.interfaces.PositionInfoInterf;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;
import java.util.Vector;

@AllArgsConstructor
@Getter
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
}