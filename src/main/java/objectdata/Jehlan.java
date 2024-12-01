package objectdata;

import transforms.Mat4Identity;
import transforms.Point3D;

import java.util.List;

public class Jehlan extends Object3D{
    public Jehlan(){
        super(
                List.of(
                        new Point3D(-1,-1,-1),
                        new Point3D(-1, 1, -1),
                        new Point3D(1, -1, -1),
                        new Point3D(1, 1, -1),
                        new Point3D(0,0, 1)
                ),
                List.of(
                        0, 1,
                        0, 2,
                        1, 3,
                        2, 3,

                        0, 4,
                        1, 4,

                        2, 4,

                        3, 4
                ),
                new Mat4Identity()
        );
    }
}
