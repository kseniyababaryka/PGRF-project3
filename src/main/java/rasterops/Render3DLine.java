package rasterops;

import objectdata.CoordinateAxes;
import objectdata.Curve;
import objectdata.Object3D;
import objectdata.Scene;
import rasterdata.RasterBI;
import transforms.Mat4;
import transforms.Point3D;
import transforms.Vec2D;

import java.util.List;

public class Render3DLine {
    public void renderScene(RasterBI raster, Scene scene, Mat4 matView, Mat4 matProjection, Liner liner) {

        // for all objects from scene
        for (int i = 0; i < scene.getObjects().size(); i++) {
            //     renderObject
            final Object3D object = scene.getObjects().get(i);
            final Mat4 objectTransformation = object.getModelMat().mul(matView).mul(matProjection);
            renderObject(raster, object, objectTransformation, liner);
        }


    }

    private void renderObject(RasterBI raster, Object3D object, Mat4 transformation, Liner liner){
        // 1. Transform all vertices of the object
        final List<Point3D> transformedVertices = object
                .getVertexBuffer()
                .stream()
                .map(p -> p.mul(transformation))
                .toList();
        // 2. Primitive assembly (lines) - first, second

        // for all lines
        for (int i = 0; i < object.getIndexBuffer().size(); i += 2) {
            final Point3D first = transformedVertices.get(object.getIndexBuffer().get(i));
            final Point3D second = transformedVertices.get(object.getIndexBuffer().get(i + 1));
            // 3. Clipping
            if (first.getX() < -first.getW() || first.getX() > first.getW()
                    || first.getY() < -first.getW() || first.getY() > first.getW()
                    || first.getZ() < 0 || first.getZ() > first.getW()) {
                continue;
            } else if (second.getX() < -second.getW() || second.getX() > second.getW()
                    || second.getY() < -second.getW() || second.getY() > second.getW()
                    || second.getZ() < 0 || second.getZ() > second.getW()) {
                continue;
            }
            // 4. Dehomog
            int finalI = i;
            first.dehomog().ifPresent(
                    p1 -> {
                        second.dehomog().ifPresent(p2 -> {
                            // 5. Projection 2D
                            Vec2D first2D = p1.ignoreZ();
                            Vec2D second2D = p2.ignoreZ();
                            // 6. Viewport transformation
                            Vec2D firstInViewSpace = toViewSpace(raster, first2D);
                            Vec2D secondInViewSpace = toViewSpace(raster, second2D);
                            // 7. Rasterization(liner.draw)
                            int color = determineColor(object, finalI);

                            // 7. Rasterizace (vykreslení úsečky)
                            liner.draw(raster,
                                    (int) firstInViewSpace.getX(), (int) firstInViewSpace.getY(),
                                    (int) secondInViewSpace.getX(), (int) secondInViewSpace.getY(),
                                    color);
                        });
                    }
            );


        }

    }

    private Vec2D toViewSpace(RasterBI raster, Vec2D point) {
        return new Vec2D(((point.getX() + 1) / 2 * (raster.width() -1)), (((point.getY() * -1) + 1) / 2 * (raster.height() -1)));
    }

    private int determineColor(Object3D object, int lineIndex) {
        if (object instanceof CoordinateAxes) {
            switch (lineIndex) {
                case 0: return 0xFF0000; // Červená (X osa)
                case 2: return 0x00FF00; // Zelená (Y osa)
                case 4: return 0x0000FF; // Modrá (Z osa)
                default: return 0xFFFFFF; // Výchozí bílá (pro případ chyby)
            }
        }
        return 0xFFFFFF; // Výchozí barva pro ostatní objekty
    }
}
