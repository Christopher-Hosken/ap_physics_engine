package engine;

public class tri {
    public vec3 A, B, C;

    public tri() {
        A=B=C=new vec3();
    }

    public tri(vec3 i, vec3 j, vec3 k) {
        A = i;
        B = j;
        C = k;
    }

    public float intersect(ray ray) {
        vec3 AB = vec3.sub(B, A);
        vec3 AC = vec3.sub(C, A);
        vec3 P = vec3.cross(ray.direction(), AC);
        float det = vec3.dot(AB, P);

        if (Math.abs(det) < 0.0000001) return -1;

        float invDet = 1 / det;

        vec3 tuv = vec3.sub(ray.origin(), A);
        double u = vec3.dot(tuv, P) * invDet;
        if (u < 0 || u > 1) return -1;

        vec3 q = vec3.cross(tuv, AB);
        double v = vec3.dot(ray.direction(), q) * invDet;
        if (v < 0 || u + v > 1) return -1;

        float t = vec3.dot(AC, q) * invDet;

        return t;
    }
}
