package rasterops;

import rasterdata.Raster;

public interface Liner {
    void draw(Raster raster, int c1, int r1, int c2, int r2, int color);
}
