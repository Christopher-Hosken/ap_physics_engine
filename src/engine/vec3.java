package engine;

public class vec3 {
    public float x, y, z;

    public vec3() {
        x=y=z=0;
    }

    public vec3(float i, float j, float k) {
        x=i;
        y=j;
        z=k;
    }

    public void add(vec3 v) {
        x += v.x;
        y += v.y;
        z += v.z;
    }

    public String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }

    public static vec3 mult(vec3 v, float d) {
        return new vec3(
            v.x * d,
            v.y * d,
            v.z * d
        );
    }

    public static vec3 mult(vec3 v, vec3 u) {
        return new vec3(
            v.x * u.x,
            v.y * u.y,
            v.z * u.z
        );
    }

    public void sub(vec3 v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
    }

    public vec3 unitVector() {
        return vec3.div(this, length());
    }

    public static vec3 sub(vec3 v, vec3 u) {
        return new vec3(v.x - u.x, v.y - u.y, v.z - u.z);
    }

    public static vec3 add(vec3 v, vec3 u) {
        return new vec3(v.x + u.x, v.y + u.y, v.z + u.z);
    }

    public static float dot(vec3 v, vec3 u) {
        return v.x * u.x + v.y * u.y + v.z * u.z;
    }
    public static vec3 cross(vec3 v, vec3 u) {
        return new vec3(
            v.y * u.z - v.z * u.y,
            v.z * u.x - v.x * u.z,
            v.x * u.y - v.y * u.x
        );
    }

    public static vec3 div(vec3 v, double d) {
        return new vec3(
            (float) (v.x / d),
            (float) (v.y / d),
            (float) (v.z / d)
        );
    }

    public float lengthSquared() {
        return x*x + y*y + z*z;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    private static float randomf() {
        return (float) Math.random();
    }

    private static float randomf(float min, float max) {
        return min + ((float) Math.random() * (max - min));
    }

    public static vec3 random() {
        return new vec3(randomf(), randomf(), randomf());
    }

    public static vec3 random(float min, float max) {
        return new vec3(randomf(min, max), randomf(min, max), randomf(min, max));
    }
}
