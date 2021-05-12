package engine;

import com.jogamp.opengl.GL2;
import java.util.ArrayList;

public class EmptyObj {
    protected String name;
    protected int id;
    protected vec3 oCenter, oRotation, oScale;
    protected vec3 cCenter, cRotation, cScale;
    protected vec3 color;
    protected ArrayList<vec3> vertices;
    protected int vertexCount;
    protected boolean isLine, hasQuads, active;

    public EmptyObj(int id, String name) {
        cCenter = oCenter = new vec3();
        cRotation = oRotation = new vec3();
        cScale = oScale = new vec3(1, 1, 1);
        color = new vec3(0.5f, 0.5f, 0.5f);
        this.name = name;
        this.id = id;
        init();
        this.vertexCount = vertices.size();
    }

    public EmptyObj(int id, String name, boolean hasQuads) {
        cCenter = oCenter = new vec3();
        cRotation = oRotation = new vec3();
        cScale = oScale = new vec3(1, 1, 1);
        color = new vec3(0.5f, 0.5f, 0.5f);
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

    public vec3 oCenter() {
        return oCenter;
    }

    public vec3 oRotation() {
        return oRotation;
    }

    public vec3 oScale() {
        return oScale;
    }

    public vec3 color() {
        return color;
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
        oCenter.add(t);
        for (int vdx = 0; vdx < vertices.size(); vdx++) {
            vertices.get(vdx).add(t);
        }
    }

    // Not entirely sure if this works lol.
    public void setLocation(vec3 l) {
        vec3 PL = vec3.sub(l, oCenter);
        translate(PL);
    }

    public void physicsTranslate(vec3 t) {
        cCenter.add(t);
        for (int vdx = 0; vdx < vertices.size(); vdx++) {
            vertices.get(vdx).add(t);
        } 
    }

    public void setPhysicsLocation(vec3 l) {
        vec3 PL = vec3.sub(l, cCenter);
        physicsTranslate(PL);
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
            //vec3 rgb = vec3.sub(center, v).normalize();

            if (active) {
                gl.glColor3f(color.x + 0.25f, color.y + 0.25f, color.z + 0.25f);
            }

            else {
                gl.glColor3f(color.x, color.y, color.z);
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