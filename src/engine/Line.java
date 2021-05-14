package engine;

public class Line extends EmptyObj {
    public Line(String name, int id, vec3 origin, vec3 direction) {
        super(name, id, false);
        isLine = true;
        vertices.add(origin);
        vertices.add(direction);
        vertexCount = vertices.size();
    }
}
