package engine;
import java.util.ArrayList;

public class Line extends EmptyObj {
    public Line(int id, String name, vec3 o, vec3 d) {
        super(id, new ArrayList<vec3>(), false);
        this.name = name;
        init(o, d);
        vertexCount = vertices.size() * 3;
    }

    public void init(vec3 o, vec3 d) {
        vertices.add(o);
        vertices.add(d);
    }

    public boolean isLine() {
        return true;
    }

    public float intersect(ray r) {
        return -1;
    }
}
