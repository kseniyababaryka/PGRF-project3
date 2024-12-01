package objectdata;

import java.util.ArrayList;
import java.util.List;



public class Scene {

    private final List<Object3D> objects;

    public Scene() {
        this.objects = new ArrayList<>();
    }

    public void add(Object3D object3D){
        this.objects.add(object3D);
    }

    public List<Object3D> getObjects(){
        return objects;
    }

    public void remove(){

    }
}
