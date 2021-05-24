package engine;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

import java.util.ArrayList;
import java.awt.event.*;
import java.nio.CharBuffer;

public class SceneCanvas implements GLEventListener, MouseMotionListener, MouseWheelListener, KeyListener, MouseListener {
    private GL2 gl;
    private PhysicsEngine engine;
    private ArrayList<EmptyObj> scene = new ArrayList<EmptyObj>();
    private float zNear= 0.001f, zFar = 10000f, fov = 45;
    private int frameStart = 0, frameEnd = 250;

    private int clickX, clickY, lastX, lastY, height;
    private float startZ, posX, posY, posZ = -5, angleX, angleY, angleZ;
    private EmptyObj sel = null;
    private int frame_current = frameStart;
    private boolean isClick, isWire, isSimulating;

    public boolean showOrigins, drawNormals;

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
        gl.glLineWidth(2f);
        gl.glPointSize(10);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        clear();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        engine = new PhysicsEngine(scene, frameStart, frameEnd);
        gl = drawable.getGL().getGL2();

        //#region ID Drawing
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        //#region Viewport Transformations
        gl.glTranslatef(posX, posY, posZ);
        gl.glRotatef(angleY, 1, 0, 0);
        gl.glRotatef(angleX, 0, 1, 0);
        gl.glRotatef(angleZ, 0, 0, 1);
        //#endregion

        frame_current = engine.update(frame_current, isSimulating);
       

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

        //#region Viewport Transformations
        gl.glTranslatef(posX, posY, posZ);
        gl.glRotatef(angleY, 1, 0, 0);
        gl.glRotatef(angleX, 0, 1, 0);
        gl.glRotatef(angleZ, 0, 0, 1);
        //#endregion

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

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GLU glu = new GLU();
        gl = drawable.getGL().getGL2();
        this.height = height;

        
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        glu.gluPerspective(fov, h, zNear, zFar);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void clear() {
        scene = new ArrayList<EmptyObj>();
        sel = null;
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
    }

    public float getStartZ() {
        return startZ;
    }

    public void setStartZ(float z) {
        startZ = z;
    }

    public boolean isWire() {
        return isWire;
    }

    public float getFov() {
        return fov;
    }

    public void setFov(float f, boolean radians) {
        if (radians) {

        }

        else {
            fov = f;
        }
    }

    public void setIsWire(boolean w) {
        isWire = w;
    }

    public EmptyObj getSelection() {
        return sel;
    }

    public void updateSelection() {
        for (EmptyObj obj : scene) {
            obj.setActive(obj.id() == sel.id());
        }
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

    public void addCube() {
        int id = makeID();
        scene.add(new Cube("Cube-" + scene.size(), id));
        //scene.get(scene.size() - 1).translate(scene.get(scene.size() - 1).center(), vec3.random(-10.0f, 10.0f));
    }

    public void addLine(vec3 o, vec3 d) {
        int id = makeID();
        scene.add(new Line("Line-" + scene.size(), id, o, d));
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
            clickY = height - e.getY();
        }
    }

    @Override 
    public void mouseReleased(MouseEvent e) {}

    @Override 
    public void mouseExited(MouseEvent e) {}

    @Override 
    public void mousePressed(MouseEvent e) {
    }

    @Override 
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getButton() == 1) {
            if (e.isShiftDown()) {
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
            posZ -= (((e.getY() - lastY) * Math.abs(posZ)) / 100.0);
        }
        lastX = e.getX();
        lastY = e.getY();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        posZ -= ((e.getWheelRotation() * Math.abs(posZ)) / 10.0);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isAltDown()) {
            if (e.getKeyCode() == 92) { // alt-\
                posX = 0;
                posY = 0;
                posZ = -5;
                angleX = 0;
                angleY = 0;
                angleZ = 0;
            }

            else if (e.getKeyCode() == 71) { // alt-G
                if (sel != null) {
                    frame_current = frameStart;
                    engine.update(frame_current, false);
                    sel.setLocation(new vec3(0, 0, 0));
                    sel.setChanged(true);
                }
            }

            else if (e.getKeyCode() == 82) { // alt-R
                if (sel != null) {
                    frame_current = frameStart;
                    engine.update(frame_current, false);
                    sel.setRotation(new vec3(0, 0, 0));
                    sel.setChanged(true);
                }
            }

            else if (e.getKeyCode() == 83) { // alt-S
                if (sel != null) {
                    frame_current = frameStart;
                    engine.update(frame_current, false);
                    sel.setScale(new vec3(1, 1, 1));
                    sel.setChanged(true);
                }
            }
        }

        else if (e.isControlDown()) {
            if (e.getKeyCode() == 32) { // ctrl-Spacebar
                frame_current = frameStart;
                engine.update(frame_current, false);
            }

            else if (e.getKeyCode() == 88) { // ctrl-X
                frame_current = frameStart;
                engine.update(frame_current, false);
                scene.remove(sel);
                sel = null;
            }
        }

        else if (e.isShiftDown()){

        }

        else {
            if (e.getKeyCode() == 127) { // Del
                frame_current = frameStart;
                scene.remove(sel);
                sel = null;
            }

            else if (e.getKeyCode() == 36) { // Home
                posX = 0;
                posY = 0;
                posZ = -5;
                angleX = 0;
                angleY = 0;
                angleZ = 0;
            }
    
            else if (e.getKeyCode() == 32) { // Spacebar
                isSimulating = !isSimulating;
            }
        }
    }

    //#endregion
}