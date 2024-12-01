package objectdata;

public class Point2D {
    private double x;
    private double y;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Point2D translate(double dx, double dy) {
        return new Point2D(x + dx, y + dy);
    }

    public Point2D scale(double k) {
        return new Point2D(x * k, y * k);
    }

    public Point2D rotate(double alpha) {
        return new Point2D(Math.cos(alpha) * x - Math.sin(alpha) * y, Math.sin(alpha) * x + Math.cos(alpha) * y);
    }
}
