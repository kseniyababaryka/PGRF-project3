package objectdata;

import transforms.Mat3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Polygon {
    private List<Point2D> points;

    // constructor
    public Polygon() {
        points = new ArrayList<>(); //implemented by array
    }

    public void addPoint(Point2D point) {
        points.add(point);
    }

    public Point2D getPoint(int idx) {
        return points.get(idx);
    }

    public int size() {
        return points.size();
    }

    public List<Line> toLines() {
        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            Point2D nextPoint = points.get((i + 1) % points.size());
            lines.add(new Line(points.get(i), nextPoint));
        }
        return lines;
    }

    public Polygon translate(double dx, double dy) {
        Polygon result = new Polygon();
        for (int i = 0; i < points.size(); i++) {
            result.addPoint(points.get(i).translate(dx, dy));
        }
        return result;
    }

    public Polygon scale(double k) {
        Polygon result = new Polygon();
        for (int i = 0; i < points.size(); i++) {
            result.addPoint(points.get(i).scale(k));
        }
        return result;
    }

    public Polygon rotate(double alpha) {
        Polygon result = new Polygon();
        for (int i = 0; i < points.size(); i++) {
            result.addPoint(points.get(i).rotate(alpha));
        }
        return result;
    }

    public Polygon transforms(Function<Point2D, Point2D> transformation) {
        Polygon result = new Polygon();
        for (int i = 0; i < points.size(); i++) {
            result.addPoint(transformation.apply(points.get(i)));
        }
        return result;
    }

    public Polygon transformed(Mat3 transformation) {
        Polygon result = new Polygon();
        for (int i = 0; i < points.size(); i++) {
            Point2D point = points.get(i);
            transforms.Point2D transformed = new transforms.Point2D(point.getX(), point.getY()).mul(transformation);
            result.addPoint(new Point2D(transformed.getX(), transformed.getY()));
        }
        return result;
    }
}
