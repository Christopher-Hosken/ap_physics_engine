package engine;
import java.util.ArrayList;

public class Cube extends EmptyObj {
    public Cube(String name, int id) {
        super(name, id, true);
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

    public EmptyObj collide(ArrayList<EmptyObj> world) {

        for (EmptyObj obj : world) {
            if (obj.id() != id) {
                float[][] objBounds = obj.getBounds();

                for (int vdx = 0; vdx < vertices.size(); vdx++) {
                    vec3 v = vertices.get(vdx);
                    if ((v.x >= objBounds[0][0] && v.x <= objBounds[0][1]) && (v.y >= objBounds[1][0] && v.y <= objBounds[1][1]) && (v.z >= objBounds[2][0] && v.z <= objBounds[2][1])) {
                        
                        if (vdx < 8) {
                            collision = 1;
                        }

                        else {
                            collision = 0;
                        }

                        return obj;
                    }
                }
            }
        }

        return null;
    }
}