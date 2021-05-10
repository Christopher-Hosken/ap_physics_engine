package engine;
import java.util.ArrayList;


public class IcoSphere extends EmptyObj {

    public IcoSphere(int id, String name) {
        super(id, name, false);
    }

    public void init() {
        this.vertices = new ArrayList<vec3>();
        float X = 0.525731112119133606f;
        float Z = 0.850650808352039932f;
        float N = 0f;

        vec3[] verts = new vec3[] {
            new vec3(-X, N, Z), new vec3(X, N, Z), new vec3(-X, N, -Z), new vec3(X, N, -Z),
            new vec3(N, Z, X), new vec3(N, Z, -X), new vec3(N, -Z, X), new vec3(N, -Z, -X),
            new vec3(Z, X, N), new vec3(-Z, X, N), new vec3(Z, -X, N), new vec3(-Z, -X, N)
        };

        int[][] faces = new int[][] {
            {0,4,1},{0,9,4},{9,5,4},{4,5,8},{4,8,1},
            {8,10,1},{8,3,10},{5,3,8},{5,2,3},{2,7,3},
            {7,10,3},{7,6,10},{7,11,6},{11,0,6},{0,1,6},
            {6,1,10},{9,0,11},{9,11,2},{9,2,5},{7,2,11}
        };

        for (int[] f : faces) {
            vertices.add(verts[f[0]]);
            vertices.add(verts[f[1]]);
            vertices.add(verts[f[2]]);
        }
    }
}
