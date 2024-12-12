package rasterops;

import rasterdata.Raster;

public class TrivialLiner implements Liner {
    @Override
    public void draw(Raster raster, int c1, int r1, int c2, int r2, int color) {
        int dx = Math.abs(c2 - c1);
        int dy = Math.abs(r2 - r1);
        int sx = c1 < c2 ? 1 : -1;
        int sy = r1 < r2 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            raster.setColor(c1, r1, color); // Устанавливаем цвет пикселя

            if (c1 == c2 && r1 == r2) break; // Конец линии
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                c1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                r1 += sy;
            }
        }
    }

}
