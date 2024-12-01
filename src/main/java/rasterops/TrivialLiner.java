package rasterops;

import rasterdata.Raster;

public class TrivialLiner implements Liner {
    @Override
    public void draw(Raster raster, int c1, int r1, int c2, int r2, int color) {



        double k = (double) (r2 - r1) / (c2 - c1);
        double q = r1 - k * c1;
        for (int c = c1; c <= c2; c++) {
            int r = (int) (k * c + q);
            raster.setColor(c, r, color);
        }
    }
}
