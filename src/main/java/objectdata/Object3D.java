package objectdata;

import transforms.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Object3D {
    private final List<Point3D> vertexBuffer;
    private final List<Integer> indexBuffer;
    private final Mat4 modelMat;

    public Object3D(List<Point3D> vertexBuffer, List<Integer> indexBuffer, Mat4 modelMat){
        this.vertexBuffer = vertexBuffer;
        this.indexBuffer = indexBuffer;
        this.modelMat = modelMat;
    }



    public List<Point3D> getVertexBuffer() {
        return vertexBuffer;
    }

    public List<Integer> getIndexBuffer() {
        return indexBuffer;
    }

    public Mat4 getModelMat() {
        return modelMat;
    }

    public Object3D translate(double dx, double dy, double dz) {

        Mat4Transl translationMatrix = new Mat4Transl(dx, dy, dz);

        List<Point3D> newVertexBuffer = new ArrayList<>();
        for (Point3D vertex : vertexBuffer) {
            Point3D transformedVertex = applyTransformation(vertex, translationMatrix);
            newVertexBuffer.add(transformedVertex);
        }
        Mat4 modelMat = getModelMat().mul(translationMatrix);
        return new Object3D(newVertexBuffer, indexBuffer, modelMat);
    }

    public Object3D scale(double kx, double ky, double kz) {
        Mat4Scale scaleMatrix = new Mat4Scale(kx, ky, kz);

        List<Point3D> newVertexBuffer = new ArrayList<>();
        for (Point3D vertex : vertexBuffer) {
            Point3D transformedVertex = applyTransformation(vertex, scaleMatrix);
            newVertexBuffer.add(transformedVertex);
        }

        return new Object3D(newVertexBuffer, indexBuffer, getModelMat());
    }

    public Object3D rotate(double alpha, double axisX, double axisY, double axisZ) {
        Mat4Rot rotationMatrix = new Mat4Rot(alpha, axisX, axisY, axisZ);

        List<Point3D> newVertexBuffer = new ArrayList<>();
        for (Point3D vertex : vertexBuffer) {
            Point3D transformedVertex = applyTransformation(vertex, rotationMatrix);
            newVertexBuffer.add(transformedVertex);
        }

        return new Object3D(newVertexBuffer, indexBuffer, getModelMat());
    }

    private Point3D applyTransformation(Point3D point, Mat4 transformationMatrix) {
        double[] pointArray = { point.getX(), point.getY(), point.getZ(), 1.0 };
        double[] result = new double[4];

        for (int i = 0; i < 4; i++) {
            result[i] = 0;
            for (int j = 0; j < 4; j++) {
                result[i] += transformationMatrix.get(i, j) * pointArray[j];
            }
        }

        return new Point3D(result[0], result[1], result[2]);
    }






}
