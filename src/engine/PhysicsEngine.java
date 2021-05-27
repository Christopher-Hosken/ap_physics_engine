package engine;

import java.util.ArrayList;

public class PhysicsEngine {
    protected int frameStart, frameEnd;
    protected float g = -9.8f;
    protected float timeScale = 0.0001f;
    protected ArrayList<EmptyObj> world;

    public PhysicsEngine(ArrayList<EmptyObj> world, int frameStart, int frameEnd) {
        this.world = world;
        this.frameStart = frameStart;
        this.frameEnd = frameEnd;
    }

    public void setGrav(float _g) {
        g = _g;
    }

    public void setTimeScale(float t) {
        timeScale = t;
    }

    public int update(int frame, boolean isSimulating) {
        if (frame < frameStart || frame > frameEnd) {
            frame = frameStart;
        }

        if (frame == frameStart) {
            for (EmptyObj obj : world) {
                float x = obj.center().x;
                float y = obj.center().y;
                float z = obj.center().z;
                obj.setPVelocity(new vec3(obj.velocity().x, obj.velocity().y, obj.velocity().z));
                obj.setPLocation(new vec3(x, y, z));
            }
        }

        if (isSimulating) {
            for (EmptyObj obj : world) {
                vec3 force = new vec3();
                if (!obj.isStatic()) {
                    force.add(vec3.mult(new vec3(0, g, 0), timeScale));

                    EmptyObj i = obj.collide(world);

                    if (i != null) {
                        if (i.isStatic()) {
                            force.sub(force);
                            obj.setAcceleration(new vec3(0f, 0f, 0f));
                            if (i.collision == 0) {
                                obj.setPVelocity(new vec3(obj.pVelocity().x * (1 - i.friction()), 0f, obj.pVelocity().z * (1 - i.friction())));
                            }

                            else {
                                obj.setPVelocity(new vec3(0f, 0f, 0f));
                            }
                        }

                        else {
                            force.sub(force);
                            if (obj.getMomentum().length() != 0) {
                                vec3 tmp = i.pVelocity();
                                i.setPVelocity(obj.pVelocity());
                                obj.setPVelocity(tmp);
                            }
                        }
                    }

                    obj.applyForce(force);
                }
            }

            frame++;
        }

        return frame;

    }
}
