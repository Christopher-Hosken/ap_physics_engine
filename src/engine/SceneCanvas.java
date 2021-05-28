package engine;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.awt.event.*;
import java.nio.CharBuffer;

public class SceneCanvas implements GLEventListener, MouseMotionListener, MouseWheelListener, KeyListener, MouseListener {
    private GL2 gl;
    private GLU glu = new GLU();
    private PhysicsEngine engine;
    private ArrayList<EmptyObj> scene = new ArrayList<EmptyObj>();
    private float zNear= 1f, zFar = 100f, fov = 45;
    private int frameStart = 0, frameEnd = 250;
    private float[] background = new float[] {0f, 0f, 0f};

    private int clickX, clickY, lastX, lastY, width, height;
    private float startZ = -5, posX, posY, posZ = -5, angleX, angleY, angleZ;
    private EmptyObj sel = null;
    private int frame_current = frameStart;
    private boolean isClick, isWire, isSimulating;
    private String[] debugData = new String[10];

    @Override 
    public void init(GLAutoDrawable drawable) {
        gl = drawable.getGL().getGL2();
        gl.glClearColor(background[0], background[1], background[2], 1f);
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
        addToDebug("OpenGL Initialized.");
    }

    public String getDebug() {
        String out = "";
        for (int sdx = debugData.length - 1; sdx >= 0; sdx--) {
            if (debugData[sdx] == null) {
                debugData[sdx] = " ";
            }
            out += debugData[sdx] + "\n";
        }

        return out;
    }

    public void addToDebug(String s) {
        for (int sdx = debugData.length - 1; sdx > 0; sdx--) {
            debugData[sdx] = debugData[sdx - 1];
        }

        debugData[0] = s;
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

        frame_current = engine.update(frame_current, isSimulating, debugData);
       

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
        gl.glClearColor(background[0], background[1], background[2], 1f);
        gl.glLoadIdentity();

        //#region Viewport Transformations
        gl.glTranslatef(posX, posY, posZ);
        gl.glRotatef(angleY, 1, 0, 0);
        gl.glRotatef(angleX, 0, 1, 0);
        gl.glRotatef(angleZ, 0, 0, 1);
        //#endregion

        for (EmptyObj obj : scene) {
            obj.draw(gl, isWire, new vec3(posX, posY, posZ), new vec3(angleX, angleY, angleZ));

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
        gl = drawable.getGL().getGL2();
        this.height = height;
        this.width = width;
        updatePersp();
    }

    public void setWorldColor(Color c) {
        background[0] = (float) c.getRed();
        background[1] = (float) c.getGreen();
        background[2] = (float) c.getBlue();
    }

    public void updatePersp() {
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

    public void setFov(float f) {
        fov = f;
        updatePersp();
    }

    public void setIsWire(boolean w) {
        isWire = w;
    }

    public void setFrameStart(int fs) {
        frameStart = fs;
    }

    public void setFrameEnd(int fe) {
        frameEnd = fe;
    }
    
    public int getFrameStart() {
        return frameStart;
    }

    public int getFrameEnd() {
        return frameEnd;
    }

    public EmptyObj getSelection() {
        return sel;
    }

    public void updateSelection() {
        for (EmptyObj obj : scene) {
            obj.setActive(obj.id() == sel.id());
        }
    }

    public void setNearClipping(float nc) {
        zNear = nc;
        updatePersp();
    }

    public float getNearClipping() {
        return zNear;
    }
  
    public void setFarClipping(float fc) {
        zFar = fc;
        updatePersp();
    }

    public float getFarClipping() {
        return zFar;
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
        addToDebug("Cube created.");
    }

    public void addLine(vec3 o, vec3 d) {
        int id = makeID();
        scene.add(new Line("Line-" + scene.size(), id, o, d));
        addToDebug("Line created.");
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
                posZ = startZ;
                angleX = 0;
                angleY = 0;
                angleZ = 0;
                addToDebug("World view reset.");
            }

            else if (e.getKeyCode() == 71) { // alt-G
                if (sel != null) {
                    frame_current = frameStart;
                    engine.update(frame_current, false, debugData);
                    sel.setLocation(new vec3(0, 0, 0));
                    sel.setChanged(true);
                    addToDebug("Object location reset.");
                }
            }

            else if (e.getKeyCode() == 83) { // alt-S
                if (sel != null) {
                    frame_current = frameStart;
                    engine.update(frame_current, false, debugData);
                    sel.setScale(new vec3(1, 1, 1));
                    sel.setChanged(true);
                    addToDebug("Object scale reset.");
                }
            }

            else if (e.getKeyCode() == 70) { // alt-f
                frame_current = frameStart;
                engine.update(frame_current, false, debugData);
                addToDebug("Simulation reset.");
            }
        }

        else if (e.isControlDown()) {
            if (e.getKeyCode() == 88) { // ctrl-X
                frame_current = frameStart;
                engine.update(frame_current, false, debugData);
                scene.remove(sel);
                sel = null;
                addToDebug("Object deleted.");
            }

        }

        else if (e.isShiftDown()){

        }

        else {
            if (e.getKeyCode() == 127) { // Del
                frame_current = frameStart;
                scene.remove(sel);
                sel = null;
                addToDebug("Object deleted.");
            }

            else if (e.getKeyCode() == 36) { // Home
                posX = 0;
                posY = 0;
                posZ = startZ;
                angleX = 0;
                angleY = 0;
                angleZ = 0;
                addToDebug("World view reset.");
            }
    
            else if (e.getKeyCode() == 32) { // Spacebar
                isSimulating = !isSimulating;
                addToDebug("Simulation Toggled.");
            }
        }
    }

    //#endregion
}