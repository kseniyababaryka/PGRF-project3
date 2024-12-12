package objectdata;

import transforms.Mat4Identity;
import transforms.Point3D;

import java.util.List;

public class Prism extends Object3D{

    public Prism() {
        super(
                List.of(
                       new Point3D(-1, -1, -1),
                       new Point3D(1, -1, -1),
                       new Point3D(1, 1, -1),
                       new Point3D(-1, 1, -1),
                       new Point3D(-0.5, -0.5, 1),
                       new Point3D(0.5, -0.5, 1),
                       new Point3D(0.5, 0.5, 1),
                       new Point3D(-0.5, 0.5, 1)
                ),

                List.of(
                        0,1,
                        1,2,
                        2,3,
                        3,0,
                        4,5,
                        5,6,
                        6,7,
                        7,4,
                        0,4,
                        1,5,
                        2,6,
                        3,7
                ),
                new Mat4Identity()
        );
    }
}
