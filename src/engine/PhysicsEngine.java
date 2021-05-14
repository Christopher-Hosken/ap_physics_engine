package engine;

import java.util.ArrayList;

public class PhysicsEngine {
    private ArrayList<EmptyObj> world;
    private int frameStart, frameEnd;
    private float g = -0.98f;

    public PhysicsEngine(ArrayList<EmptyObj> world, int frameStart, int frameEnd) {
        this.world = world;
        this.frameStart = frameStart;
        this.frameEnd = frameEnd;
    }

    public int update(int frame, boolean isSimulating) {
        if (frame > frameEnd || frame < frameStart) {
            frame = frameStart;
        }

        for (EmptyObj obj : world) {
            if (frame == frameStart) {
                obj.setLocation(obj.pCenter(), obj.center());
            }

            if (isSimulating) {
                vec3 force = new vec3(0, g, 0);
                obj.applyForce(force);
            }
        }

        if (isSimulating) {
            frame ++;
        }

        return frame;
    }
}
