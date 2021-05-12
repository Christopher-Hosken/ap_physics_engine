package engine;

import com.jogamp.opengl.GL2;
import java.util.ArrayList;

public class EmptyObj {
    protected String name;
    protected int id;
    protected vec3 center, rotation, scale;
    protected vec3 pCenter, pRotation, pScale;
    protected vec3 color;
    protected ArrayList<vec3> vertices;
    protected int vertexCount;
    protected boolean isLine, hasQuads, active, changed;

    public EmptyObj(int id, String name) {
        center = new vec3();
        rotation = new vec3();
        scale = new vec3(1, 1, 1);
        color = new vec3(0.5f, 0.5f, 0.5f);
        this.name = name;
        this.id = id;
        init();
        this.vertexCount = vertices.size();
        pCenter = new vec3();
        pRotation = new vec3();
        pScale = new vec3(1, 1, 1);
    }

    public EmptyObj(int id, String name, boolean hasQuads) {
        center = new vec3();
        rotation = new vec3();
        scale = new vec3(1, 1, 1);
        color = new vec3(0.5f, 0.5f, 0.5f);
        this.name = name;
        this.id = id;
        this.hasQuads = hasQuads;
        init();
        this.vertexCount = vertices.size();
        pCenter = new vec3();
        pRotation = new vec3();
        pScale = new vec3(1, 1, 1);
    }

    public void init() {
        this.vertices = new ArrayList<vec3>();
    }

    //#region Getters
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

    public vec3 color() {
        return color;
    }

    public boolean active() {
        return active;
    }

    public boolean isLine() {
        return isLine;
    }

    public boolean changed() {
        return changed;
    }

    public float colorID() {
        vec3 rgb = idToColor();
        return (rgb.x + rgb.y * 256 + rgb.z);
    }

    //#endregion

    //#region Setters

    public void setChanged(boolean c) {
        changed = c;
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

    public void rotate(vec3 r) {
        rotation.add(r);
        for (int vdx = 0; vdx < vertices.size(); vdx++) {
            vec3 v = vertices.get(vdx);
            v.sub(center);
            // X Rotation
            v.y = v.y * (float) Math.cos(Math.toRadians(r.x)) - v.z *  (float) Math.sin(Math.toRadians(r.x));
            v.z = v.z * (float) Math.cos(Math.toRadians(r.x)) + v.y * (float) Math.sin(Math.toRadians(r.x));

            // Y Rotation
            v.x = v.x * (float) Math.cos(Math.toRadians(r.y)) + v.z *  (float) Math.sin(Math.toRadians(r.y));
            v.z = v.z * (float) Math.cos(Math.toRadians(r.y)) - v.x * (float) Math.sin(Math.toRadians(r.y));

            // Z Rotation
            v.x = v.x * (float) Math.cos(Math.toRadians(r.z)) - v.y *  (float) Math.sin(Math.toRadians(r.z));
            v.y = v.y * (float) Math.cos(Math.toRadians(r.z)) + v.x * (float) Math.sin(Math.toRadians(r.z));
            
            v.add(center);

            vertices.set(vdx, v);
        }
    }

    public void setRotation(vec3 r) {
        vec3 rrot = vec3.sub(r, rotation);
        rotation = r;
        for (int vdx = 0; vdx < vertices.size(); vdx++) {
            vec3 v = vertices.get(vdx);
            v.sub(center);
            // X Rotation
            v.y = v.y * (float) Math.cos(Math.toRadians(rrot.x)) - v.z *  (float) Math.sin(Math.toRadians(rrot.x));
            v.z = v.z * (float) Math.cos(Math.toRadians(rrot.x)) + v.y * (float) Math.sin(Math.toRadians(rrot.x));

            // Y Rotation
            v.x = v.x * (float) Math.cos(Math.toRadians(rrot.y)) + v.z *  (float) Math.sin(Math.toRadians(rrot.y));
            v.z = v.z * (float) Math.cos(Math.toRadians(rrot.y)) - v.x * (float) Math.sin(Math.toRadians(rrot.y));

            // Z Rotation
            v.x = v.x * (float) Math.cos(Math.toRadians(rrot.z)) - v.y *  (float) Math.sin(Math.toRadians(rrot.z));
            v.y = v.y * (float) Math.cos(Math.toRadians(rrot.z)) + v.x * (float) Math.sin(Math.toRadians(rrot.z));
            
            v.add(center);

            vertices.set(vdx, v);
        }
    }

    public void scale(vec3 s) {
        scale.mult(s);
        for (int vdx = 0; vdx < vertices.size(); vdx++) {
            vertices.set(vdx, vec3.add(vec3.mult(vec3.sub(vertices.get(vdx), center), s), center));
        }
    }

    public void setScale(vec3 s) {
        vec3 rscl = vec3.div(s, scale);
        scale = s;
        for (int vdx = 0; vdx < vertices.size(); vdx++) {
            vertices.set(vdx, vec3.add(vec3.mult(vec3.sub(vertices.get(vdx), center), rscl), center));
        }
    }

    // Not entirely sure if this works lol.
    public void setLocation(vec3 l) {
        vec3 PL = vec3.sub(l, center);
        translate(PL);
    }

    public void physicsTranslate(vec3 t) {
        pCenter.add(t);
        for (int vdx = 0; vdx < vertices.size(); vdx++) {
            vertices.get(vdx).add(t);
        } 
    }

    public void setPhysicsLocation(vec3 l) {
        vec3 PL = vec3.sub(l, pCenter);
        physicsTranslate(PL);
    }

    //#endregion

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