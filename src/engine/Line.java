package engine;

public class Line extends EmptyObj {
    public Line(int id, String name, vec3 origin, vec3 direction) {
        super(id, name, false);
        isLine = true;
        vertices.add(origin);
        vertices.add(direction);
        vertexCount = vertices.size();
    }
}
