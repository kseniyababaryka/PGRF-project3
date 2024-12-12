package objectdata;

import transforms.Mat4Identity;
import transforms.Point3D;

import java.util.List;

public class Tetrahedron extends Object3D{

    public Tetrahedron() {
        super(
                List.of(
                        new Point3D(2.5, 1, 1),
                        new Point3D(0.5, -1, 1),
                        new Point3D(0.5, 1, -1),
                        new Point3D(2.5, -1, -1)
                ),
                List.of(
                        0,1,
                        0,2,
                        0,3,
                        1,2,
                        1,3,
                        2,3
                ),
                new Mat4Identity()
        );
    }
}
