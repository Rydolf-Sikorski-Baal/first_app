package baal.code_files.entity_movement;

import baal.ApplicationContextProvider;
import baal.code_files.entities.entities_tree.Entity;
import baal.code_files.entities.entities_tree.Hero;
import baal.code_files.entities.shape_tree.Rectangle;
import baal.code_files.graphics_system.LevelCellsSizes;
import baal.code_files.level_system.level.Level;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Component
class Interception {
    private final double epsilon = 0.005;
    private final int INFINITY = 1000 * 1000 * 1000;
    private double cellHeight, cellWeight;

    class CoordsFunction {
        Function<Entity, Coords> function;

        CoordsFunction(Function<Entity, Coords> _function) {
            function = _function;
        }
    }

    class ClassPair {
        public Class<?> first, second;

        ClassPair(Entity firstEntity, Entity secondEntity) {
            first = firstEntity.shape.getClass();
            second = secondEntity.shape.getClass();
        }
    }

    private Map<ClassPair, BiFunction<Entity, Entity, Boolean>> interceptors;
    private Map<Class<?>, CoordsFunction> positionFunctions;

    private Interception() {
        positionFunctions = new HashMap<>();

        //прямоугольник
        CoordsFunction rectangleFunctions = new CoordsFunction(this::makeCoords_Rectangle);
        positionFunctions.put(baal.code_files.entities.shape_tree.Rectangle.class, rectangleFunctions);
    }

    public Coords getCoords(Entity entity, double _cellHeight, double _cellWeight) {
        cellHeight = _cellHeight;
        cellWeight = _cellWeight;

        return positionFunctions.get(entity.shape.getClass()).function.apply(entity);
    }

    //прямоугольник
    class InnerCoordsEdges {
        public Point left_top, left_down, right_down, right_top;

        InnerCoordsEdges(Point _left_top,
                         Point _left_down,
                         Point _right_down,
                         Point _right_top) {
            left_top = _left_top;
            left_down = _left_down;
            right_down = _right_down;
            right_top = _right_top;
        }
    }

    private Coords makeCoords_Rectangle(Entity _entity) {
        Hero currEntity = (Hero) _entity;
        baal.code_files.entities.shape_tree.Rectangle rectangle = (baal.code_files.entities.shape_tree.Rectangle) _entity.shape;

        Vector<Point> InnerCoords = makeInnerCoords_Rectangle(currEntity, rectangle);

        int x_min = INFINITY, x_max = -INFINITY;
        int y_min = INFINITY, y_max = -INFINITY;
        for (Point point : InnerCoords) {
            x_min = min(x_min, point.x);
            x_max = max(x_max, point.x);

            y_min = min(y_min, point.y);
            y_max = max(y_max, point.y);
        }
        Point left_top = new Point(x_min, y_min),
                left_down = new Point(x_max, y_min),
                right_down = new Point(x_max, y_max),
                right_top = new Point(x_min, y_max);
        InnerCoordsEdges innerCoordsEdges = new InnerCoordsEdges(left_top, left_down, right_down, right_top);

        Vector<Point> AdjacentUpCoords = makeAdjacentUpDownCoords(-1,
                currEntity.position.getX(), cellHeight,
                innerCoordsEdges.left_top.y, innerCoordsEdges.right_top.y,
                innerCoordsEdges.left_top.x);
        Vector<Point> AdjacentLeftCoords = makeAdjacentSideCoords(-1,
                currEntity.position.getY(), cellWeight,
                innerCoordsEdges.left_top.x, innerCoordsEdges.left_down.x,
                innerCoordsEdges.left_top.y);
        Vector<Point> AdjacentRightCoords = makeAdjacentSideCoords(+1,
                currEntity.position.getY() + rectangle.y_size, cellWeight,
                innerCoordsEdges.right_top.x, innerCoordsEdges.right_down.x,
                innerCoordsEdges.right_down.y);
        Vector<Point> AdjacentDownCoords = makeAdjacentUpDownCoords(+1,
                currEntity.position.getX() + rectangle.x_size, cellHeight,
                innerCoordsEdges.left_down.y, innerCoordsEdges.right_down.y,
                innerCoordsEdges.right_down.x);

        Coords currCoords = new Coords(InnerCoords, AdjacentUpCoords, AdjacentLeftCoords, AdjacentRightCoords, AdjacentDownCoords);
        return currCoords;
    }

    private Vector<Point> makeAdjacentSideCoords(int delta, double position, double cellSize,
                                         int start, int end, int y) {
        Vector<Point> adjacentCoords = new Vector<Point>(0);

        if ((position % 1) < epsilon)
            for (int curr = start; curr <= end; curr++)
                adjacentCoords.add(new Point(curr, y + delta));

        return adjacentCoords;
    }

    private Vector<Point> makeAdjacentUpDownCoords(int delta, double position, double cellSize,
                                           int start, int end, int x) {
        Vector<Point> adjacentCoords = new Vector<Point>(0);

        if (position < 0) {
            position = 228;
        }
        if ((position % 1) < epsilon)
            for (int curr = start; curr <= end; curr++)
                adjacentCoords.add(new Point(x + delta, curr));

        return adjacentCoords;
    }

    private double getAreaIntersection_Rectangle(double x1_up, double y1_left,
                                                 double x1_down, double y1_right,
                                                 double x2_up, double y2_left,
                                                 double x2_down, double y2_right) {
        double y_leftPoint = max(y1_left, y2_left);
        double x_leftPoint = max(x1_up, x2_up);

        double y_rightPoint = min(y1_right, y2_right);
        double x_rightPoint = min(x1_down, x2_down);

        double y_length = max(y_rightPoint - y_leftPoint, 0);
        double x_length = max(x_rightPoint - x_leftPoint, 0);

        return y_length * x_length;
    }

    private Vector<Point> makeInnerCoords_Rectangle(Hero _entity, Rectangle rectangle) {
        double x_size = rectangle.x_size;
        double y_size = rectangle.y_size;

        double x = _entity.position.getX();
        double y = _entity.position.getY();

        Vector<Point> listOfPositions = new Vector<Point>(0);

        int stepSize = max((int) x_size, (int) y_size) + 1;
        int[] step = new int[2 * stepSize + 1];
        for (int curr = -stepSize, i = 0; curr <= stepSize; curr++, i++)
            step[i] = curr;

        int approximate_position_x = (int) (x) + (x % 1 > 0 ? 1 : 0);
        int approximate_position_y = (int) (y) + (y % 1 > 0 ? 1 : 0);

        for (int i = 0; i < step.length; i++)
            for (int j = 0; j < step.length; j++) {
                int pretend_position_x = approximate_position_x + step[i];
                int pretend_position_y = approximate_position_y + step[j];

                double pretend_x_double = pretend_position_x;
                double pretend_y_double = pretend_position_y;

                double areaInterseptionOfPretendent = getAreaIntersection_Rectangle(x, y,
                        x + x_size,
                        y + y_size,
                        pretend_x_double, pretend_y_double,
                        pretend_x_double + 1,
                        pretend_y_double + 1);

                if (areaInterseptionOfPretendent > epsilon) {
                    Point curr_cord = new Point(pretend_position_x, pretend_position_y);
                    listOfPositions.add(curr_cord);
                }
            }

        return listOfPositions;
    }
}