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
import java.nio.FloatBuffer;

public class SceneCanvas implements GLEventListener, MouseMotionListener, MouseWheelListener, KeyListener, MouseListener {
    private GLU glu = new GLU();
    private float zNear= 0.001f, zFar = 1000f, fov = 45;
    private boolean isAltDown, isShiftDown, isCtrlDown;
    private float lastX, lastY;
    private float vw, vh;
    private float posX, posY, posZ = - 5, angleX, angleY, angleZ;
    private ArrayList<EmptyObj> scene = new ArrayList<EmptyObj>();
    public boolean isWire;
    private EmptyObj sel;

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        if (isWire) {
            gl.glLineWidth(2f);
            gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
        }

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glTranslatef(posX, posY, posZ);
        gl.glRotatef(angleY, 1, 0, 0);
        gl.glRotatef(angleX, 0, 1, 0);
        gl.glRotatef(angleZ, 0, 0, 1);
        

        for (EmptyObj obj : scene) {
            gl = obj.addToGL(gl);
        }

        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glFlush();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {}

    @Override 
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0f, 0f, 0f, 0f);
        gl.glShadeModel(GL2.GL_SMOOTH);

        //gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, new float[] {0.5f, 0.5f, 0.5f}, 0);

        //gl.glEnable(GL2.GL_LIGHTING);
        //gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
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

    public void addCube() {
        scene.add(new Cube("Cube-" + scene.size()));
        //scene.get(scene.size() - 1).translate(vec3.random(-10.0f, 10.0f));
    }

    public void addIcoSphere() {
        scene.add(new IcoSphere("Ico-" + scene.size()));
        //scene.get(scene.size() - 1).translate(vec3.random(-10.0f, 10.0f));
    }

    public void clean() {
        scene = new ArrayList<EmptyObj>();
    }

    //#region mousecontrols

    @Override
    public void mouseMoved(MouseEvent e) {
        lastX = e.getX();
        lastY = e.getY();
    }

    @Override 
    public void mouseClicked(MouseEvent e) {
        System.out.println("CLICKED");

        vec3 llc = new vec3(
            1,
            -1,
            0
        );

        vec3 target = new vec3(
            llc.x - (e.getX() / vw) * 2,
            llc.y + (e.getY() / vh) * 2,
            1
        ); 

        ray r = new ray(new vec3(), target);
        System.out.println(r);

        EmptyObj sel_tmp = null;
        float t = Float.MAX_VALUE;
        float t_tmp;


        for (EmptyObj obj : scene) {
            t_tmp = obj.intersect(r);
            if (t_tmp > 0.0000001 && t_tmp < t) {
                t = t_tmp;
                sel_tmp = obj;
            }
        }

        if (sel_tmp != null) {
            sel = sel_tmp;
            System.out.println(sel.name());
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
        //System.out.println(e.getButton());
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

        System.out.println("(" + posX + ", " + posY + ", " + posZ + ")");
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        //System.out.println(posZ);
        posZ -= (e.getWheelRotation() / 10.0);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
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
        //System.out.println(e.getKeyChar() + ":  " + e.getKeyCode());
        if (isAltDown) {
            if (e.getKeyCode() == 82) {
                posX = 0;
                posY = 0;
                posZ = 0;
                angleX = 0;
                angleY = 0;
                angleZ = 0;
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
    }

    //#endregion
}