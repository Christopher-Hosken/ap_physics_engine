package engine;

import java.util.ArrayList;

public class PhysicsEngine {
    protected int frameStart, frameEnd;
    protected float g = -9.8f;
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
                obj.setLocation(obj.pCenter(), obj.center());
                obj.setVelocity(obj.pVelocity(), obj.velocity());
                obj.setAngularVelocity(obj.pAngularVelocity(), obj.angularVelocity());
            }
        }

        if (isSimulating) {
            for (EmptyObj obj : world) {
                if (!obj.isStatic()) {
                    obj.applyForce(new vec3(0f, -0.1f, 0f));
                }
            }

            frame++;
        }

        return frame;
        
    }
}
