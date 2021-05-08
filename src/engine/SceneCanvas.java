package engine;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.common.nio.*;

import java.awt.event.*;

public class SceneCanvas implements GLEventListener, MouseMotionListener, MouseWheelListener, KeyListener {
    private GLU glu = new GLU();
    private float zNear= 0.001f, zFar = 1000f;
    private boolean isAltDown, isShiftDown, isCtrlDown;
    private float lastX, lastY;
    private float posX, posY, posZ, angleX, angleY, angleZ;


    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glTranslatef(posX, posY, posZ - 5);
        gl.glRotatef(angleY, 1, 0, 0);
        gl.glRotatef(angleX, 0, 1, 0);
        gl.glRotatef(angleZ, 0, 0, 1);
        
        GLUT teapot = new GLUT();
        teapot.glutSolidTeapot(0.5);
        
        gl.glFlush();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {}

    @Override 
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0f, 0f, 0f, 0f);
        gl.glShadeModel(GL2.GL_SMOOTH);

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, new float[] {0.5f, 1f, 0.2f}, 0);

        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, new float[] {0.5f, 0.5f, 0.5f}, 0);

        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        
        
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        glu.gluPerspective(45, h, zNear, zFar);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        lastX = e.getX();
        lastY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println(e.getButton());
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
        System.out.println(e.getKeyChar() + ":  " + e.getKeyCode());
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
}