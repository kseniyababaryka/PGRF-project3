package rasterops;

import objectdata.Point2D;
import rasterdata.RasterBI;
import objectdata.Polygon;

import java.awt.*;

public class Polygoner {
    public void draw(RasterBI raster, Polygon polygon, Liner liner, int color) {
        for (int i = 0; i < polygon.size(); i++) {
            Point2D p = polygon.getPoint(i);
            Point2D next = polygon.getPoint((i + 1) % polygon.size());
            liner.draw(raster, (int) p.getX(), (int) p.getY(), (int) next.getX(), (int) next.getY(), color);
        }
    }
}
