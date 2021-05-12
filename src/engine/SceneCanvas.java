package engine;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.common.nio.*;

import java.util.ArrayList;
import java.awt.event.*;
import java.nio.Buffer;
import java.nio.CharBuffer;
import java.nio.FloatBuffer;
import gui.Gui;

public class SceneCanvas implements GLEventListener, MouseMotionListener, MouseWheelListener, KeyListener, MouseListener {
    private GLU glu = new GLU();
    private GL2 gl;
    private Gui appGui;
    private float zNear= 0.001f, zFar = 1000f, fov = 45;
    private boolean isAltDown, isShiftDown, isCtrlDown;
    private float lastX, lastY;
    private int clickX, clickY;
    private boolean isClick;
    private float vw, vh;
    private float posX, posY, posZ = -5, angleX, angleY, angleZ;
    private ArrayList<EmptyObj> scene = new ArrayList<EmptyObj>();
    public boolean isWire;
    private EmptyObj sel = null;
    private int frameStart = 0, frameEnd = 250;
    private int frame_current = frameStart;
    private boolean isSimulating;
    private PhysicsEngine engine;

    @Override
    public void display(GLAutoDrawable drawable) {
        gl = drawable.getGL().getGL2();

        //#region ID Drawing
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        engine = new PhysicsEngine(scene, frameStart, frameEnd);

        //#region Viewport Transformations
        
        gl.glTranslatef(posX, posY, posZ);
        gl.glRotatef(angleY, 1, 0, 0);
        gl.glRotatef(angleX, 0, 1, 0);
        gl.glRotatef(angleZ, 0, 0, 1);
        //#endregion
        
        frame_current = engine.update(frame_current, isSimulating);
        //System.out.println(frame_current);

        for (EmptyObj obj : scene) {
            obj.drawID(gl);
        }

        gl.glFlush();
        gl.glFinish();

        if (isClick) {
            gl.glPixelStorei(GL2.GL_UNPACK_ALIGNMENT, 1);
            CharBuffer data = CharBuffer.allocate(4);

            gl.glReadPixels(clickX, clickY, 1, 1, GL2.GL_RGB, GL2.GL_UNSIGNED_BYTE, data);
            int cid = data.get(0) + data.get(1);
            for (EmptyObj obj : scene) {
                if (obj.colorID() == cid) {
                    sel = obj;
                    updateSelection();
                }
            }
        }

        //#endregion

        //#region Viewport Drawing
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glTranslatef(posX, posY, posZ);
        gl.glRotatef(angleY, 1, 0, 0);
        gl.glRotatef(angleX, 0, 1, 0);
        gl.glRotatef(angleZ, 0, 0, 1);

        for (EmptyObj obj : scene) {
            obj.draw(gl, isWire);
            if (obj.active()) {
                obj.drawLines(gl);
            }
            gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        }

        gl.glFlush();
        //#endregion

        isClick = false;
    }

    public EmptyObj getSelection() {
        return sel;
    }

    public void updateSelection() {
        for (EmptyObj obj : scene) {
            obj.setActive(obj.id() == sel.id());
        }
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {}


    @Override 
    public void init(GLAutoDrawable drawable) {
        gl = drawable.getGL().getGL2();
        gl.glClearColor(0f, 0f, 0f, 0f);
        gl.glShadeModel(GL2.GL_SMOOTH);

        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDepthMask(true);
        gl.glDepthFunc(GL2.GL_LESS);
        gl.glEnable(GL2.GL_CULL_FACE);
        gl.glEnable(GL2.GL_MULTISAMPLE);
        gl.glEnable(GL2.GL_LINE_SMOOTH);
        gl.glEnable(GL2.GL_POLYGON_OFFSET_LINE);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        gl = drawable.getGL().getGL2();
        vw = width;
        vh = height;
        
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        glu.gluPerspective(fov, h, zNear, zFar);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public int makeID() {
        int tmp_id;
        while (true) {
            boolean n = true;
            tmp_id = (int) (Math.random() * Integer.MAX_VALUE);
            for (EmptyObj o : scene) {
                if (tmp_id == o.id()) {
                    n = false;
                    break;
                }
            }
            if (n) {
                break;
            }
        }
        return tmp_id;
    }

    public void wipe() {
        scene = new ArrayList<EmptyObj>();
        sel = null;
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
    }

    public void addCube() {
        int id = makeID();
        scene.add(new Cube(id, "Cube-" + scene.size()));
        scene.get(scene.size() - 1).translate(vec3.random(-10.0f, 10.0f));
    }

    public void addIcoSphere() {
        int id = makeID();
        scene.add(new IcoSphere(id, "Ico-" + scene.size()));
        scene.get(scene.size() - 1).translate(vec3.random(-10.0f, 10.0f));
    }

    public void addLine(vec3 o, vec3 d) {
        int id = makeID();
        scene.add(new Line(id, "Line-" + scene.size(), o, d));
    }

    //#region Controls

    @Override
    public void mouseMoved(MouseEvent e) {
        lastX = e.getX();
        lastY = e.getY();
    }

    @Override 
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == 1) {
            isClick = true;
            clickX = e.getX();
            clickY = (int) vh - e.getY();
        }
    }

    @Override 
    public void mouseReleased(MouseEvent e) {}

    @Override 
    public void mouseExited(MouseEvent e) {}

    @Override 
    public void mousePressed(MouseEvent e) {}

    @Override 
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getButton() == 1) {
            if (isShiftDown) {
                posX += ((e.getX() - lastX) / 100.0);
                posY -= ((e.getY() - lastY) / 100.0);
            }

            else {
                angleX += (e.getX() - lastX);
                angleY += (e.getY() - lastY);
            }
        }

        else if (e.getButton() == 2) {
            posX += ((e.getX() - lastX) / 100.0);
            posY -= ((e.getY() - lastY) / 100.0);
        }

        else if (e.getButton() == 3) {
            posZ -= ((e.getY() - lastY) / 100.0);
        }
        lastX = e.getX();
        lastY = e.getY();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        posZ -= (e.getWheelRotation() / 10.0);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println(e.getKeyChar() + ": " + e.getKeyCode());
        if (e.getKeyCode() == 16) {
            isShiftDown = false;
        }

        else if (e.getKeyCode() == 17) {
            isCtrlDown = false;
        }

        else if (e.getKeyCode() == 18) {
            isAltDown = false;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (isAltDown) {
            if (e.getKeyCode() == 92) {
                posX = 0;
                posY = 0;
                posZ = -5;
                angleX = 0;
                angleY = 0;
                angleZ = 0;
            }

            else if (e.getKeyCode() == 71) {
                if (sel != null) {
                    sel.setLocation(new vec3(0, 0, 0));
                    sel.setChanged(true);
                }
            }

            else if (e.getKeyCode() == 82) {
                if (sel != null) {
                    sel.setRotation(new vec3(0, 0, 0));
                    sel.setChanged(true);
                }
            }

            else if (e.getKeyCode() == 83) {
                if (sel != null) {
                    sel.setScale(new vec3(1, 1, 1));
                    sel.setChanged(true);
                }
            }

            else if (e.getKeyCode() == 70) {
                frame_current = frameStart;
            }
        }

        if (e.getKeyCode() == 16) {
            isShiftDown = true;
        }

        else if (e.getKeyCode() == 17) {
            isCtrlDown = true;
        }

        else if (e.getKeyCode() == 18) {
            isAltDown = true;
        }

        else if (e.getKeyCode() == 32) {
            isSimulating = !isSimulating;
        }
    }

    //#endregion
}