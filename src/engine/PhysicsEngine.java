package engine;

import java.util.ArrayList;

public class PhysicsEngine {
    protected int frameStart, frameEnd;
    protected float g = -9.8f;
    protected float timeScale = 0.001f;
    protected ArrayList<EmptyObj> world;

    public PhysicsEngine(ArrayList<EmptyObj> world, int frameStart, int frameEnd) {
        this.world = world;
        this.frameStart = frameStart;
        this.frameEnd = frameEnd;
    }

    public int update(int frame, boolean isSimulating) {
        if (frame < frameStart || frame > frameEnd) {
            frame = frameStart;
        }

        if (frame == frameStart) {
            for (EmptyObj obj : world) {
                obj.setPVelocity(new vec3(obj.velocity().x, obj.velocity().y, obj.velocity().z));
                obj.setPLocation(new vec3(obj.center().x, obj.center().y, obj.center().z));
            }
        }

        if (isSimulating) {
            for (EmptyObj obj : world) {
                if (!obj.isStatic()) {
                    if (obj.isPivot()) {

                    }

                    else {
                        obj.applyForce(vec3.mult(new vec3(0f, g, 0f), timeScale));
                        obj.collide(world);
                    }
                }
            }

            frame++;
        }

        return frame;
        
    }
}
