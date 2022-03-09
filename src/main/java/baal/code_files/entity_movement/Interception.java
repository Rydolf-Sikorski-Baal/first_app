package baal.code_files.entity_movement;

import baal.code_files.entities.entities_tree.Entity;
import baal.code_files.entities.shape_tree.Rectangle;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.function.Function;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Component
class Interception {
    private final double epsilon = 0.005;

    static class CordsFunction {
        Function<Entity, Coords> function;

        CordsFunction(Function<Entity, Coords> _function) {
            function = _function;
        }
    }

    private final Map<Class<?>, CordsFunction> positionFunctions;

    private Interception() {
        positionFunctions = new HashMap<>();

        //прямоугольник
        CordsFunction rectangleFunctions = new CordsFunction(this::makeCords_Rectangle);
        positionFunctions.put(baal.code_files.entities.shape_tree.Rectangle.class, rectangleFunctions);
    }

    public Coords getCords(Entity entity) {
        return positionFunctions.get(entity.shape.getClass()).function.apply(entity);
    }

    //прямоугольник
    static class InnerCordsEdges {
        public Point left_top, left_down, right_down, right_top;

        InnerCordsEdges(Point _left_top,
                        Point _left_down,
                        Point _right_down,
                        Point _right_top) {
            left_top = _left_top;
            left_down = _left_down;
            right_down = _right_down;
            right_top = _right_top;
        }
    }

    private Coords makeCords_Rectangle(Entity _entity) {
        baal.code_files.entities.shape_tree.Rectangle rectangle
                = (baal.code_files.entities.shape_tree.Rectangle) _entity.shape;

        Vector<Point> InnerCords = makeInnerCords_Rectangle(_entity, rectangle);

        int INFINITY = 1000 * 1000 * 1000;
        int x_min = INFINITY, x_max = -INFINITY;
        int y_min = INFINITY, y_max = -INFINITY;
        for (Point point : InnerCords) {
            x_min = min(x_min, point.x);
            x_max = max(x_max, point.x);

            y_min = min(y_min, point.y);
            y_max = max(y_max, point.y);
        }
        Point left_top = new Point(x_min, y_min),
                left_down = new Point(x_max, y_min),
                right_down = new Point(x_max, y_max),
                right_top = new Point(x_min, y_max);
        InnerCordsEdges innerCordsEdges = new InnerCordsEdges(left_top, left_down, right_down, right_top);

        Vector<Point> AdjacentUpCords = makeAdjacentUpDownCords(-1,
                _entity.position.getX(),
                innerCordsEdges.left_top.y, innerCordsEdges.right_top.y,
                innerCordsEdges.left_top.x);
        Vector<Point> AdjacentLeftCords = makeAdjacentSideCords(-1,
                _entity.position.getY(),
                innerCordsEdges.left_top.x, innerCordsEdges.left_down.x,
                innerCordsEdges.left_top.y);
        Vector<Point> AdjacentRightCords = makeAdjacentSideCords(+1,
                _entity.position.getY() + rectangle.y_size,
                innerCordsEdges.right_top.x, innerCordsEdges.right_down.x,
                innerCordsEdges.right_down.y);
        Vector<Point> AdjacentDownCords = makeAdjacentUpDownCords(+1,
                _entity.position.getX() + rectangle.x_size,
                innerCordsEdges.left_down.y, innerCordsEdges.right_down.y,
                innerCordsEdges.right_down.x);

        return new Coords(InnerCords, AdjacentUpCords, AdjacentLeftCords, AdjacentRightCords, AdjacentDownCords);
    }

    private Vector<Point> makeAdjacentSideCords(int delta, double position,
                                                int start, int end, int y) {
        Vector<Point> adjacentCords = new Vector<>(0);

        if ((position % 1) < epsilon)
            for (int curr = start; curr <= end; curr++)
                adjacentCords.add(new Point(curr, y + delta));

        return adjacentCords;
    }

    private Vector<Point> makeAdjacentUpDownCords(int delta, double position,
                                                  int start, int end, int x) {
        Vector<Point> adjacentCords = new Vector<>(0);

        if (position < 0) {
            position = 228;
        }
        if ((position % 1) < epsilon)
            for (int curr = start; curr <= end; curr++)
                adjacentCords.add(new Point(x + delta, curr));

        return adjacentCords;
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

    private Vector<Point> makeInnerCords_Rectangle(Entity _entity, Rectangle rectangle) {
        double x_size = rectangle.x_size;
        double y_size = rectangle.y_size;

        double x = _entity.position.getX();
        double y = _entity.position.getY();

        Vector<Point> listOfPositions = new Vector<>(0);

        int stepSize = max((int) x_size, (int) y_size) + 1;
        int[] step = new int[2 * stepSize + 1];
        for (int curr = -stepSize, i = 0; curr <= stepSize; curr++, i++)
            step[i] = curr;

        int approximate_position_x = (int) (x) + (x % 1 > 0 ? 1 : 0);
        int approximate_position_y = (int) (y) + (y % 1 > 0 ? 1 : 0);

        for (int k : step)
            for (int i : step) {
                int pretend_position_x = approximate_position_x + k;
                int pretend_position_y = approximate_position_y + i;

                double areaInterceptionOfPretended = getAreaIntersection_Rectangle(x, y,
                        x + x_size,
                        y + y_size,
                        pretend_position_x, pretend_position_y,
                        (double) pretend_position_x + 1,
                        (double) pretend_position_y + 1);

                if (areaInterceptionOfPretended > epsilon) {
                    Point curr_cord = new Point(pretend_position_x, pretend_position_y);
                    listOfPositions.add(curr_cord);
                }
            }

        return listOfPositions;
    }
}