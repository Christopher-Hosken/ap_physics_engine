package engine;
import java.util.ArrayList;

public class Cube extends EmptyObj {
    public Cube(String name) {
        super(new ArrayList<vec3>(), true);
        this.name = name;
        initCube();
        vertexCount = vertices.size() * 3;
    }

    private void initCube() {
        vec3[] verts = new vec3[] {
            // Top Face
            new vec3(1, 1, -1),
            new vec3 (-1 ,1, -1),
            new vec3 (-1, 1, 1),
            new vec3 (1, 1, 1),

            // Bottom Face
            new vec3 (1, -1, 1),
            new vec3 (-1 ,-1, 1),
            new vec3 (-1, -1, -1),
            new vec3 (1, -1, -1),

            // Front face
            new vec3 (1, 1, 1),
            new vec3 (-1 ,1, 1),
            new vec3 (-1, -1, 1),
            new vec3 (1, -1, 1),

            // Back Face
            new vec3 (1, -1, 1),
            new vec3 (-1 ,-1, 1),
            new vec3 (-1, 1, -1),
            new vec3 (1, 1, -1),

            // Left Face
            new vec3 (-1, 1, 1),
            new vec3 (-1 ,1, -1),
            new vec3 (-1, -1, -1),
            new vec3 (-1, -1, 1),

            // Right Face
            new vec3 (1, 1, -1),
            new vec3 (1 ,1, 1),
            new vec3 (1, -1, 1),
            new vec3 (1, -1, -1)
        };

        for (vec3 v : verts) {
            vertices.add(v);
        }
    }
}
