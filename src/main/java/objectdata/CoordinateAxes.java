package objectdata;

import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import transforms.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoordinateAxes extends Object3D {

    public CoordinateAxes() {
        super(createVertexBuffer(), createIndexBuffer(), new Mat4Identity());
    }

    private static List<Point3D> createVertexBuffer() {

        return List.of(
                new Point3D(0, 0, 0), // Origin
                new Point3D(4, 0, 0), // X axis
                new Point3D(0, 4, 0), // Y axis
                new Point3D(0, 0, 4)  // Z axis
        );
    }

    private static List<Integer> createIndexBuffer() {

        return List.of(
                0, 1,
                0, 2,
                0, 3
        );
    }
}

