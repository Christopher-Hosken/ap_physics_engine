package engine;

public class ray {
    private vec3 origin, direction;

    public ray() {
        origin=direction=new vec3();
    }

    public ray(vec3 o, vec3 d) {
        origin = o;
        direction = d;
    }

    public vec3 origin() {
        return origin;
    }

    public vec3 direction() {
        return direction;
    }

    public vec3 at(float t) {
        vec3 p = origin;
        p.add(vec3.mult(direction, t));
        return p;
    }

    public String toString() {
        return "From " + origin.toString() + " to " + direction.toString(); 
    }
}
