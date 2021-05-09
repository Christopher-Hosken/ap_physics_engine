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
        ArrayList<vec3[]> tris = new ArrayList<vec3[]>();
        if (hasQuads) {
            for (int vdx = 0; vdx < vertices.size() - 4; vdx += 4) {
                tris.add(new vec3[] {vertices.get(vdx), vertices.get(vdx + 1), vertices.get(vdx + 2)});
                tris.add(new vec3[] {vertices.get(vdx + 1), vertices.get(vdx + 2), vertices.get(vdx + 3)});
            }
        }

        else {
            for (int vdx = 0; vdx < vertices.size() - 3; vdx += 3) {
                tris.add(new vec3[] {vertices.get(vdx), vertices.get(vdx + 1), vertices.get(vdx + 2)});
            }
        }

        float t = Float.MAX_VALUE;
        float t_tmp;

        for (vec3[] tri : tris) {
            vec3 A = tri[0];
            vec3 B = tri[1];
            vec3 C = tri[2];

            vec3 AB = vec3.sub(B, A);
            vec3 AC = vec3.sub(C, A);
            vec3 P = vec3.cross(r.direction(), AC);
            float det = vec3.dot(AB, P);

            if (Math.abs(det) < 0.0000001) t_tmp = -1;

            else {
                float invDet = 1 / det;
                vec3 tuv = vec3.sub(r.origin(), A);
                float u = vec3.dot(tuv, P) * invDet;
                if (u < 0 || u > 1) t_tmp = -1;

                else {
                    vec3 q = vec3.cross(tuv, AB);
                    float v = vec3.dot(r.direction(), q) * invDet;
                    if (v < 0 || u + v > 1) t_tmp = -1;

                    else {
                        t_tmp = vec3.dot(AC, q) * invDet;
                    }
                }
            }

            if (t_tmp > 0 && t_tmp < t) {
                t = t_tmp;
            }            
        }

        if (t == Float.MAX_VALUE) {
            t = -1;
        }

        System.out.println(name + " " + t);
        return t;
    }
}