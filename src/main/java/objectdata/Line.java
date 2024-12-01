package objectdata;

import rasterdata.Raster;
import rasterops.Liner;

import java.util.Optional;

public class Line {
    private final Point2D start;
    private final Point2D end;

    public Line(Point2D start, Point2D end){
        this.start = start;
        this.end = end;
    }

    public Line(int x1, int y1, int x2, int y2){
        this.start = new Point2D(x1,y1);
        this.end = new Point2D(x2,y2);
    }



//    public Line withStart(Point2D point){
//        return new Line(point, this.end);
//    }



    public boolean isHorizontal(){
        return start.getY() == end.getY();
    }

    public Optional<Float> yIntercept(int y){
        if (!hasIntercept(y)) {
            return Optional.empty();
        } else {
            double k = (double) (end.getY() - start.getY()) / (end.getX() - start.getX());
            double q = start.getY() - k * start.getX();
            float x = (float) ((float) (y - q) / k);

            return Optional.of(x);
        }
    }

    public boolean hasIntercept(int y){
        if (start.getY() < end.getY()){
            return y > start.getY() && y < end.getY();
        } else return y > end.getY() && y < start.getY();

    }

    public Line reverse(){
        Point2D newStart = new Point2D(end.getX(), end.getY());
        Point2D newEnd = new Point2D(start.getX(), start.getY());

        return new Line(newStart, newEnd);
    }

    public Point2D toVec() {
        // return end - start
        return new Point2D(end.getX() - start.getX(), end.getY() - start.getY());
    }

    public boolean isInside(Point2D p){
        // 1. get normal vector -> n
        Point2D n = toVec();
        // 2. get vec(start, p) -> v
        Point2D v = new Point2D(p.getX() - start.getX(), p.getY() - start.getY());
        // 3. compute dot product(n,v) -> cosAlpha (dot product = x1 * x2 + y1 * y2)
        double cosAlpha = n.getX() * v.getX() + n.getY() * v.getY();
        // return cosAlpha > 0
        return cosAlpha > 0;
    }

    public Optional<Point2D> interception(Line other){
        // 1. compute k, q of this line
        double k1 = (end.getY() - start.getY()) / (end.getX() - start.getX());
        double q1 = start.getY() - k1 * start.getX();
        // 2. compute k, q of the other line
        double k2 = (other.end.getY() - other.start.getY()) / (other.end.getX() - other.start.getX());
        double q2 = other.start.getY() - k2 * other.start.getX();
        // 3. y1 = y2 (compute x)
        if (k1 == k2) {
            return Optional.empty();
        }
        double x = (q2 - q1) / (k1 - k2);

        // 4. compute y
        double y = k1 * x + q1;
        // 5. return Point2D(x, y)
        return Optional.of(new Point2D(x, y));
    }


    public Point2D getStart() {
        return start;
    }

    public Point2D getEnd() {
        return end;
    }
}
