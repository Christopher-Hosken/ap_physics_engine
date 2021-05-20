package engine;

import com.jogamp.opengl.GL2;
import java.util.ArrayList;

public class EmptyObj {
    protected String name;
    protected int id;
    protected float mass;
    protected ArrayList<vec3> vertices;
    protected int vertexCount;
    protected vec3 center, rotation, scale, pCenter, pRotation, pScale, velocity, pVelocity, angularVelocity, pAngularVelocity;
    protected float[] color, specular, shinyness;
    protected boolean isLine, hasQuads, active, changed, isStatic;

    public EmptyObj(String name, int id, boolean hasQuads) {
        this.name = name;
        this.id = id;
        mass = 1;
        init();
        this.vertexCount = vertices.size();
        center=rotation=new vec3();
        pCenter=pRotation=new vec3();
        velocity=new vec3();
        pVelocity=new vec3();
        angularVelocity=new vec3();
        pAngularVelocity=new vec3();
        scale=pScale=new vec3(1, 1, 1);
        color = new float[] {0.5f, 0.5f, 0.5f};
        this.hasQuads = hasQuads;
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

    public float mass() {
        return mass;
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

    public vec3 pCenter() {
        return pCenter;
    }

    public vec3 pRotation() {
        return pRotation;
    }

    public vec3 pScale() {
        return pScale;
    }

    public vec3 velocity() {
        return velocity;
    }

    public vec3 pVelocity() {
        return pVelocity;
    }

    public vec3 angularVelocity() {
        return angularVelocity;
    }

    public vec3 pAngularVelocity() {
        return pAngularVelocity;
    }

    public float[] color() {
        return color;
    }

    public float[] specular() {
        return specular;
    }

    public float[] shinyness() {
        return shinyness;
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

    public boolean isStatic() {
        return isStatic;
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

    public void setName(String n) {
        name = n;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setStatic(boolean s) {
        isStatic = s;
    }

    public void translate(vec3 c, vec3 t) {
        c.add(t);
        for (int vdx = 0; vdx < vertices.size(); vdx++) {
            vertices.get(vdx).add(t);
        }
    }

    public void setLocation(vec3 c, vec3 l) {
        vec3 PL = vec3.sub(l, c);
        translate(c, PL);
    }
    
    public void setPVelocity(vec3 u) {
        pVelocity = u;
    }

    public void setVelocity(vec3 u) {
        velocity = u;
    }
    
    public void setAngularVelocity(vec3 av, vec3 u) {
        av = u;
    }

    public void rotate(vec3 r) {
        rotation.add(r);

        float sinX = (float) Math.sin(Math.toRadians(r.x));
        float cosX = (float) Math.cos(Math.toRadians(r.x));

        float sinY = (float) Math.sin(Math.toRadians(r.y));
        float cosY = (float) Math.cos(Math.toRadians(r.y));

        float sinZ = (float) Math.sin(Math.toRadians(r.z));
        float cosZ = (float) Math.cos(Math.toRadians(r.z));

        for (int vdx = 0; vdx < vertices.size(); vdx++) {
            vec3 v = vec3.sub(vertices.get(vdx), center);
            // X Rotation
            v.y = (v.y * cosX) - (v.z * sinX);
            v.z = (v.z * cosX) + (v.y * sinX);

            // Y Rotation
            v.x = (v.x * cosY) + (v.z * sinY);
            v.z = (v.z * cosY) - (v.x * sinY);

            // Z Rotation
            v.x = (v.x * cosZ) - (v.y * sinZ);
            v.y = (v.y * cosZ) + (v.x * sinZ);

            vertices.set(vdx, vec3.add(v, center));
        }
    }

    public void setRotation(vec3 r) {
        vec3 rrot = vec3.sub(r, rotation);
        rotation = r;

        float sinX = (float) Math.sin(Math.toRadians(rrot.x));
        float cosX = (float) Math.cos(Math.toRadians(rrot.x));

        float sinY = (float) Math.sin(Math.toRadians(rrot.y));
        float cosY = (float) Math.cos(Math.toRadians(rrot.y));

        float sinZ = (float) Math.sin(Math.toRadians(rrot.z));
        float cosZ = (float) Math.cos(Math.toRadians(rrot.z));

        for (int vdx = 0; vdx < vertices.size(); vdx++) {
            vec3 v = vec3.sub(vertices.get(vdx), center);
            float x, y, z;
            x = v.x;
            y = v.y;
            z = v.z;
            
            // X Rotation
            y = (v.y * cosX) - (v.z * sinX);
            z = (v.z * cosX) + (v.y * sinX);

            v.y = y;
            v.z = z;

            // Y Rotation
            x = (v.x * cosY) + (v.z * sinY);
            z = (v.z * cosY) - (v.x * sinY);

            v.x = x;
            v.z = z;

            // Z Rotation
            x = (v.x * cosZ) - (v.y * sinZ);
            y = (v.y * cosZ) + (v.x * sinZ);

            v.x = x;
            v.y = y;
            v.z = z;

            vertices.set(vdx, vec3.add(v, center));
        }
    }

    public void scale(vec3 s) {
        vec3 r = rotation;
        setRotation(new vec3(0, 0, 0));
        scale.mult(s);
        for (int vdx = 0; vdx < vertices.size(); vdx++) {
            vertices.set(vdx, vec3.add(vec3.mult(vec3.sub(vertices.get(vdx), center), s), center));
        }
        setRotation(r);
    }

    public void setScale(vec3 s) {
        vec3 r = rotation;
        setRotation(new vec3(0, 0, 0));
        vec3 rscl = vec3.div(s, scale);
        scale = s;
        for (int vdx = 0; vdx < vertices.size(); vdx++) {
            vertices.set(vdx, vec3.add(vec3.mult(vec3.sub(vertices.get(vdx), center), rscl), center));
        }
        setRotation(r);
    }

    public void applyForce(vec3 force) {
        // F = ma
        // a = F / m
        vec3 a = vec3.div(force, mass);
        pVelocity.add(a);
        translate(pCenter, pVelocity);
    }

    public void applyTorque(vec3 force, float distance, vec3 theta) {
        // t = r*F*sin()
        vec3 torque = vec3.mult(force, vec3.mult(distance, vec3.sin(theta)));
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

            if (active) {
                gl.glColor3f(vec3.clamp(color[0] + 0.25f, 0f, 1f), vec3.clamp(color[1] + 0.25f, 0f, 1f), vec3.clamp(color[1] + 0.25f, 0f, 1f));
            }

            else {
                gl.glColor3f(vec3.clamp(color[0], 0f, 1f), vec3.clamp(color[1], 0f, 1f), vec3.clamp(color[2], 0f, 1f));
            }

            gl.glVertex3f(v.x, v.y, v.z);
        }
        gl.glEnd();
        gl.glBegin(GL2.GL_POINTS);
        gl.glColor3f(1, 1, 0);
        gl.glVertex3f(pCenter.x, pCenter.y, pCenter.z);
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
