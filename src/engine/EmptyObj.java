package engine;

import com.jogamp.opengl.GL2;
import java.util.ArrayList;

public class EmptyObj {
    protected String name;
    protected vec3 center;
    protected ArrayList<vec3> vertices;
    protected ArrayList<vec3> color; 
    protected int vertexCount;
    protected boolean hasQuads;

    public EmptyObj(ArrayList<vec3> data, boolean isQuads) {
        vertices = data;
        vertexCount = vertices.size() * 3;
        hasQuads = isQuads;
        center = new vec3();
    }

    public String name() {
        return name;
    }

    public GL2 addToGL(GL2 gl) {
        if (hasQuads) {gl.glBegin(GL2.GL_QUADS);}
        else {gl.glBegin(GL2.GL_TRIANGLES);}

        
        for (int vdx = 0; vdx < vertices.size(); vdx++) {
            vec3 v = vertices.get(vdx);
            
            gl.glColor3f(center.x - v.x, center.y - v.y, center.z - v.z);
            gl.glVertex3f(v.x, v.y, v.z);
        }

        gl.glEnd();

        return gl;
    }

    public void translate(vec3 t) {
        center.add(t);
        for (int vdx = 0; vdx < vertices.size(); vdx++) {
            vertices.get(vdx).add(t);
        }
    }

    public float intersect(ray r) {
        ArrayList<tri> tris = new ArrayList<tri>();
        if (hasQuads) {
            for (int vdx = 0; vdx < vertices.size() - 4; vdx += 4) {
                tris.add(new tri(vertices.get(vdx + 2), vertices.get(vdx + 1), vertices.get(vdx)));
                tris.add(new tri(vertices.get(vdx + 3), vertices.get(vdx + 2), vertices.get(vdx + 1)));
            }
        }

        else {
            for (int vdx = 0; vdx < vertices.size() - 3; vdx += 3) {
                tris.add(new tri(vertices.get(vdx), vertices.get(vdx + 1), vertices.get(vdx + 2)));
            }
        }

        float t = Float.MAX_VALUE;
        float t_tmp;

        for (tri tri : tris) {
            t_tmp = tri.intersect(r);

            if (t_tmp > 0.00001 && t_tmp < t) {
                t = t_tmp;
            }
        }

        return t;
    }
}