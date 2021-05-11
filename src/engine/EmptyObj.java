package engine;

import com.jogamp.opengl.GL2;
import java.util.ArrayList;

public class EmptyObj {
    protected String name;
    protected int id;
    protected vec3 center, rotation, scale;
    protected ArrayList<vec3> vertices;
    protected int vertexCount;
    protected boolean isLine, hasQuads, active;

    public EmptyObj(int id, String name) {
        center = new vec3();
        rotation = new vec3();
        scale = new vec3(1, 1, 1);
        this.name = name;
        this.id = id;
        init();
        this.vertexCount = vertices.size();
    }

    public EmptyObj(int id, String name, boolean hasQuads) {
        center = new vec3();
        this.name = name;
        this.id = id;
        this.hasQuads = hasQuads;
        init();
        this.vertexCount = vertices.size();
    }

    public void init() {
        this.vertices = new ArrayList<vec3>();
    }

    public String name() {
        return name;
    }

    public int id() {
        return id;
    }

    public vec3 center() {
        return center;
    }

    public vec3 rotation() {
        return rotation;
    }

    public vec3 scale() {
        return scale;
    }

    public boolean active() {
        return active;
    }

    public boolean isLine() {
        return isLine;
    }

    public float colorID() {
        vec3 rgb = idToColor();
        return (rgb.x + rgb.y * 256 + rgb.z);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void translate(vec3 t) {
        center.add(t);
        for (int vdx = 0; vdx < vertices.size(); vdx++) {
            vertices.get(vdx).add(t);
        }
    }

    // Not entirely sure if this works lol.
    public void setLocation(vec3 l) {
        vec3 PL = vec3.sub(l, center);
        translate(PL);
    }

    public vec3 idToColor() {
        int r = (id & 0x000000FF) >>  0;
        int g = (id & 0x0000FF00) >>  8;
        int b = (id & 0x00FF0000) >> 16;
        return new vec3(r, g, b);
    }

    public void draw(GL2 gl, boolean drawWire) {
        if (isLine) {
            drawLines(gl);
        }

        if (drawWire) {
            gl.glLineWidth(2f);
            gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE); 
        }

        if (hasQuads) {
            gl.glBegin(GL2.GL_QUADS);
        }

        else {
            gl.glBegin(GL2.GL_TRIANGLES);
        }

        for (int vdx = 0; vdx < vertices.size(); vdx++) {
            vec3 v = vertices.get(vdx);
            vec3 rgb = vec3.sub(center, v).normalize();

            if (active) {
                gl.glColor3f(rgb.x - 0.25f, rgb.y - 0.25f, rgb.x - 0.25f);
            }

            else {
                gl.glColor3f(rgb.x, rgb.y, rgb.z);
            }

            gl.glVertex3f(v.x, v.y, v.z);
        }
        gl.glEnd();
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL); 
    }

    public void drawID(GL2 gl) {
        vec3 rgb = vec3.div(idToColor(), 255f);

        if (!isLine) {
            if (hasQuads) {
                gl.glBegin(GL2.GL_QUADS);
            }

            else {
                gl.glBegin(GL2.GL_TRIANGLES);
            }

            for (int vdx = 0; vdx < vertices.size(); vdx++) {
                vec3 v = vertices.get(vdx);

                gl.glColor3f(rgb.x, rgb.y, rgb.z);
                gl.glVertex3f(v.x, v.y, v.z);
            }
    
            gl.glEnd();
        }
    }

    public void drawLines(GL2 gl) {
        gl.glLineWidth(2f);
        gl.glPolygonOffset(0, -1);

        if (isLine) {
            for (int vdx = 0; vdx < vertices.size() - 1; vdx += 2) {  
                gl.glBegin(GL2.GL_LINES);
                vec3 v0 = vertices.get(vdx);
                vec3 v1 = vertices.get(vdx + 1);
    
                gl.glColor3f(1f, 1f, 0f);
                gl.glVertex3f(v0.x, v0.y, v0.z);
                gl.glVertex3f(v1.x, v1.y, v1.z);
                gl.glEnd();
            }
        }

        else {
            gl.glPolygonMode(GL2.GL_FRONT, GL2.GL_LINE);

            if (hasQuads) {
                gl.glBegin(GL2.GL_QUADS);
            }
    
            else {
                gl.glBegin(GL2.GL_TRIANGLES);
            }

            for (int vdx = 0; vdx < vertices.size(); vdx++) {
                vec3 v = vertices.get(vdx);

                gl.glColor3f(1f, 1f, 0f);
                gl.glVertex3f(v.x, v.y, v.z);
            }
            gl.glEnd();
        }

        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
    }
}