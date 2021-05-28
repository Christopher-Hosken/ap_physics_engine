package engine;

import com.jogamp.opengl.GL2;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class EmptyObj {
    protected String name;
    protected int id;
    protected float mass;
    protected ArrayList<vec3> vertices;
    protected int vertexCount;
    protected vec3 center, scale, pCenter, velocity, pVelocity, pAcc;
    protected float[] color;
    protected boolean isLine, hasQuads, active, changed, isStatic = true;
    protected float friction;
    protected String[] debugData = new String[10];

    public int collision;

    public boolean drawVelocity, drawAcc, drawNormals, showOrigins;

    public String getDebug() {
        return "Object";
    }

    public EmptyObj(String name, int id, boolean hasQuads) {
        this.friction = 0;
        this.name = name;
        this.id = id;
        mass = 1;
        init();
        this.vertexCount = vertices.size();
        center=new vec3();
        velocity=new vec3();
        pCenter=new vec3();
        pVelocity=new vec3();
        pAcc = new vec3();
        scale=new vec3(1, 1, 1);
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

    public vec3 scale() {
        return scale;
    }

    public vec3 pCenter() {
        return pCenter;
    }

    public vec3 velocity() {
        return velocity;
    }

    public vec3 pVelocity() {
        return pVelocity;
    }

    public float[] color() {
        return color;
    }

    public boolean active() {
        return active;
    }

    public boolean isLine() {
        return isLine;
    }

    public float friction() {
        return friction;
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

    public void setFriction(float f) {
        friction = f;
    }

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

    public void translate(vec3 t) {
        center.add(t);
        for (int vdx = 0; vdx < vertices.size(); vdx++) {
            vertices.get(vdx).add(t);
        }
    }

    public void PTranslate(vec3 t) {
        pCenter.add(t);
        for (int vdx = 0; vdx < vertices.size(); vdx++) {
            vertices.get(vdx).add(t);
        }
    }

    public void setLocation(vec3 l) {
        vec3 PL = vec3.sub(l, center);
        translate(PL);
    }

    public void setPLocation(vec3 l) {
        vec3 PL = vec3.sub(l, pCenter);
        PTranslate(PL);
    }
    
    public void setPVelocity(vec3 u) {
        pVelocity = u;
    }

    public void setVelocity(vec3 u) {
        velocity = u;
    }

    public void setMass(float m) {
        mass = m;
    }

    public vec3 getMomentum() {
        return vec3.mult(pVelocity, mass);
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

    public void setAcceleration(vec3 f) {
        pAcc = f;
    }

    public void applyForce(vec3 force) {
        // F = ma
        // a = F / m
        pAcc = vec3.add(pAcc, vec3.div(force, mass));
        pVelocity.add(pAcc);
        updateVel();
    }

    public void updateVel() {
        PTranslate(pVelocity);
    }

    public void setColor(Color c) {
        color[0] = (float) c.getRed();
        color[1] = (float) c.getGreen();
        color[2] = (float) c.getBlue();
    }
    
    public EmptyObj collide(ArrayList<EmptyObj> world) {
        return null;
    }

    public float[][] getBounds() {
        float[] x = new float[] {vertices.get(0).x, vertices.get(0).x}; 
        float[] y = new float[] {vertices.get(0).y, vertices.get(0).y}; ; 
        float[] z = new float[] {vertices.get(0).z, vertices.get(0).z}; ;

        for (vec3 v : vertices) {
            if (v.x < x[0]) x[0] = v.x;
            if (v.x > x[1]) x[1] = v.x;

            if (v.y < y[0]) y[0] = v.y;
            if (v.y > y[1]) y[1] = v.y;

            if (v.z < z[0]) z[0] = v.z;
            if (v.z > z[1]) z[1] = v.z;
        }

        return new float[][] {x, y, z};
    }

    //#endregion

    public vec3 idToColor() {
        int r = (id & 0x000000FF) >>  0;
        int g = (id & 0x0000FF00) >>  8;
        int b = (id & 0x00FF0000) >> 16;
        return new vec3(r, g, b);
    }

    public void draw(GL2 gl, boolean drawWire, vec3 camP, vec3 camA) {
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

            if (drawNormals) {
                if (active) {
                    gl.glColor3f(vec3.clamp((pCenter.x - v.x) + 0.25f, 0f, 1f), vec3.clamp((pCenter.y - v.y) + 0.25f, 0f, 1f), vec3.clamp((pCenter.z - v.z) + 0.25f, 0f, 1f));
                }
    
                else {
                    gl.glColor3f(vec3.clamp((pCenter.x - v.x), 0f, 1f), vec3.clamp((pCenter.y - v.y), 0f, 1f), vec3.clamp((pCenter.z - v.z), 0f, 1f));
                } 
            }

            else {
                if (active) {
                    gl.glColor3f(vec3.clamp(color[0] + 0.25f, 0f, 1f), vec3.clamp(color[1] + 0.25f, 0f, 1f), vec3.clamp(color[2] + 0.25f, 0f, 1f));
                }
    
                else {
                    gl.glColor3f(vec3.clamp(color[0], 0f, 1f), vec3.clamp(color[1], 0f, 1f), vec3.clamp(color[2], 0f, 1f));
                }
            }


            gl.glVertex3f(v.x, v.y, v.z);
        }
        gl.glEnd();
        
        if (showOrigins) {
            gl.glBegin(GL2.GL_POINTS);
            gl.glColor3f(1, 1, 0);
            gl.glVertex3f(pCenter.x, pCenter.y, pCenter.z);
            gl.glEnd();
        }

        if (drawVelocity) {
            gl.glBegin(GL2.GL_LINES);
            gl.glColor3f(1f, 0f, 0f);
            gl.glVertex3f(pCenter.x, pCenter.y, pCenter.z);

            vec3 t = vec3.add(pCenter, pVelocity);

            gl.glVertex3f(t.x, t.y, t.z);
            gl.glEnd();
        }

        if (drawAcc) {
            gl.glBegin(GL2.GL_LINES);
            gl.glColor3f(0f, 1f, 0f);
            gl.glVertex3f(pCenter.x, pCenter.y, pCenter.z);

            vec3 t = vec3.add(pCenter, pAcc);

            gl.glVertex3f(t.x, t.y, t.z);
            gl.glEnd();
        }

        if (!active)  {
            gl.glLineWidth(0.5f);
            gl.glPolygonOffset(0, -1);
            gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);

            gl.glBegin(GL2.GL_QUADS);
            for (int vdx = 0; vdx < vertices.size(); vdx++) {
                vec3 v = vertices.get(vdx);
    
                gl.glColor3f(0.3f, 0.3f, 0.3f);
                gl.glVertex3f(v.x, v.y, v.z);
            }
            gl.glEnd();
        }

        gl.glLineWidth(2f);
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
            gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);

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
