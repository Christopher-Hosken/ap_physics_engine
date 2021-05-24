package engine;
import java.util.ArrayList;

public class Cube extends EmptyObj {
    public Cube(String name, int id) {
        super(name, id, true);
        this.type = "cube";
    }

    public void init() {
        this.vertices = new ArrayList<vec3>();

        // Top Face
        vertices.add(new vec3(1, 1, -1));
        vertices.add(new vec3 (-1 ,1, -1));
        vertices.add(new vec3 (-1, 1, 1));
        vertices.add(new vec3 (1, 1, 1));

        // Bottom Face
        vertices.add(new vec3 (1, -1, 1));
        vertices.add(new vec3 (-1 ,-1, 1));
        vertices.add(new vec3 (-1, -1, -1));
        vertices.add(new vec3 (1, -1, -1));

        // Front face
        vertices.add(new vec3 (1, 1, 1));
        vertices.add(new vec3 (-1 ,1, 1));
        vertices.add(new vec3 (-1, -1, 1));
        vertices.add(new vec3 (1, -1, 1));

        // Back Face
        vertices.add(new vec3 (1, -1, -1));
        vertices.add(new vec3 (-1 ,-1, -1));
        vertices.add(new vec3 (-1, 1, -1));
        vertices.add(new vec3 (1, 1, -1));

        // Left Face
        vertices.add(new vec3 (-1, 1, 1));
        vertices.add(new vec3 (-1 ,1, -1));
        vertices.add(new vec3 (-1, -1, -1));
        vertices.add(new vec3 (-1, -1, 1));

        // Right Face
        vertices.add(new vec3 (1, 1, -1));
        vertices.add(new vec3 (1 ,1, 1));
        vertices.add(new vec3 (1, -1, 1));
        vertices.add(new vec3 (1, -1, -1));
    }
}
