package engine;

public class vec3 {
    public float x, y, z;

    //#region Constructors

    public vec3() {
        x=y=z=0;
    }

    public vec3(float i, float j, float k) {
        x=i;
        y=j;
        z=k;
    }

    public String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }

    //#endregion

    //#region Assignment Operators

    public void add(vec3 v) {
        x += v.x;
        y += v.y;
        z += v.z;
    }

    public void add(float f) {
        x += f;
        y += f;
        z += f;
    }

    public void sub(vec3 v) {
        x -= v.x;
        y -= v.y;
        z -= v.z; 
    }

    public void sub(float f) {
        x -= f;
        y -= f;
        z -= f; 
    }

    public void mult(vec3 v) {
        x *= v.x;
        y *= v.y;
        z *= v.z; 
    }

    public void mult(float f) {
        x *= f;
        y *= f;
        z *= f; 
    }

    public void div(vec3 v) {
        x /= v.x;
        y /= v.y;
        z /= v.z; 
    }

    public void div(float f) {
        x /= f;
        y /= f;
        z /= f; 
    }

    //#endregion

    //#region Unary Operators

    public void invert() {
        x = -x;
        y = -y;
        z = -z;
    }

    public static vec3 invert(vec3 v) {
        return new vec3(-v.x, -v.y, -v.z);
    }

    //#endregion

    //#region Additive Operators
    public static vec3 add(vec3 v, vec3 u) {
        return new vec3(v.x + u.x, v.y + u.y, v.z + u.z);
    }

    public static vec3 add(vec3 v, float f) {
        return new vec3(v.x + f, v.y + f, v.z + f);
    }

    public static vec3 add (float f, vec3 v) {
        return add(v, f);
    }

    public static vec3 sub(vec3 v, vec3 u) {
        return new vec3(v.x - u.x, v.y - u.y, v.z - u.z);
    }

    public static vec3 sub(vec3 v, float f) {
        return new vec3(v.x - f, v.y - f, v.z - f);
    }

    public static vec3 sub(float f, vec3 v) {
        return sub(v, f);
    }

    //#endregion

    //#region Multiplicative Operators

    public static vec3 mult(vec3 v, vec3 u) {
        return new vec3(
            v.x * u.x,
            v.y * u.y,
            v.z * u.z
        );
    }

    public static vec3 mult(vec3 v, float f) {
        return new vec3(
            v.x * f,
            v.y * f,
            v.z * f
        );
    }

    public static vec3 mult(float f, vec3 v) {
        return mult(v, f);
    }

    public static vec3 div(vec3 v, float f) {
        return new vec3(
            v.x / f,
            v.y / f,
            v.z / f
        );
    }

    public static vec3 div(vec3 v, vec3 u) {
        return new vec3(
            v.x / u.x,
            v.y / u.y,
            v.z / u.z
        );
    }

    //#endregion

    //#region Vector Operators

    public float lengthSquared() {
        return x * x + y * y + z * z;
    }

    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    public vec3 unitVector() {
        return vec3.div(this, length());
    }

    public static vec3 unitVector(vec3 v) {
        return vec3.div(v, v.length());
    }

    public vec3 normalize() {
        return vec3.div(this, length());
    }

    public static vec3 normalize(vec3 v) {
        return vec3.div(v, v.length());
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

    //#endregion

    //#region Random

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

    //#endregion
}
