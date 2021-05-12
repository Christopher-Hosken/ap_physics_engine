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
                obj.setPhysicsLocation(obj.oCenter());
            }

            if (isSimulating) {
                obj.physicsTranslate(new vec3(0, g, 0));
            }
        }

        if (isSimulating) {
            frame ++;
        }

        return frame;
    }
}
