package engine;

import com.jogamp.opengl.GL2;
import java.util.ArrayList;

public class EmptyObj {
    protected int id;
    protected String name;
    protected vec3 center;
    protected ArrayList<vec3> vertices;
    protected ArrayList<vec3> color; 
    protected int vertexCount;
    protected boolean hasQuads;

    public EmptyObj(int id, ArrayList<vec3> data, boolean isQuads) {
        this.id = id;
        vertices = data;
        vertexCount = vertices.size() * 3;
        hasQuads = isQuads;
        center = new vec3();
    }

    public String name() {
        return name;
    }

    public int getID() {
        return id;
    }

    public vec3 getIDColor() {
        int r = (id & 0x000000FF) >>  0;
        int g = (id & 0x0000FF00) >>  8;
        int b = (id & 0x00FF0000) >> 16;
        return new vec3(r, g, b);
    }

    public GL2 addToGL(GL2 gl) {
        vec3 rgb = vec3.div(getIDColor(), 255f);
        if (isLine()) {gl.glBegin(GL2.GL_LINES);}
        else if (hasQuads) {gl.glBegin(GL2.GL_QUADS);}
        else {gl.glBegin(GL2.GL_TRIANGLES);}

        for (int vdx = 0; vdx < vertices.size(); vdx++) {
            vec3 v = vertices.get(vdx);
            if (!isLine()) {
                gl.glColor3f(rgb.x, rgb.y, rgb.z);
                gl.glVertex3f(v.x, v.y, v.z);
            }

            else {
                gl.glColor3f(1f, 1f, 0f);
                gl.glVertex3f(v.x, v.y, v.z);
            }
        }

        gl.glEnd();

        return gl;
    }

    public vec3 location() {
        return center;
    }

    public boolean isLine() {
        return false;
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