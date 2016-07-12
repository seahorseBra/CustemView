package view;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import utils.Utils;


/**
 * Created by zchao on 2016/7/12.
 */
public class OpenGLRenderer implements GLSurfaceView.Renderer {
    private float cr;
    private float cg;
    private float cb;
    private float vertices[] = {
                    -1.0f,  1.0f, 0.0f,
                    -1.0f, -1.0f, 0.0f,
                    1.0f, -1.0f, 0.0f,
                    1.0f, 1.0f, 0.0f,
    };
    private short[] indices = { 0, 1, 2, 0, 2, 3 };
    private FloatBuffer vertexBuffer;
    private ShortBuffer indexBuffer;
    private float angle = 45;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glShadeModel(GL10.GL_SMOOTH);

        gl.glClearDepthf(1.0f);

        gl.glClearColor(0f, 0f, 0f, 0.5f);

        gl.glEnable(GL10.GL_DEPTH_TEST);

        gl.glDepthFunc(GL10.GL_LEQUAL);

        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        vertexBuffer = Utils.floatToBuffer(vertices);
        indexBuffer = Utils.shortToBuffer(indices);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Sets the current view port to the new size.
        gl.glViewport(0, 0, width, height);// OpenGL docs.
        // Select the projection matrix
        gl.glMatrixMode(GL10.GL_PROJECTION);// OpenGL docs.
        // Reset the projection matrix
        gl.glLoadIdentity();// OpenGL docs.
        // Calculate the aspect ratio of the window
        GLU.gluPerspective(gl, 45.0f,
                (float) width / (float) height,
                0.1f, 100.0f);
        // Select the modelview matrix
        gl.glMatrixMode(GL10.GL_MODELVIEW);// OpenGL docs.
        // Reset the modelview matrix
        gl.glLoadIdentity();// OpenGL docs.
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // Clears the screen and depth buffer.
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        // Replace the current matrix with the identity matrix
        gl.glLoadIdentity();
        // Translates 10 units into the screen.
        gl.glTranslatef(0, 0, -10);

        // SQUARE A
        // Save the current matrix.
        gl.glPushMatrix();
        // Rotate square A counter-clockwise.
        gl.glRotatef(angle, 0, 1, 0);
        // Draw square A.
        draw(gl);
        // Restore the last matrix.
        gl.glPopMatrix();

        // SQUARE B
        // Save the current matrix
        gl.glPushMatrix();
        // Rotate square B before moving it, making it rotate around A.
        gl.glRotatef(-angle, 0, 0, 1);
        // Move square B.
        gl.glTranslatef(2, 0, 0);
        // Scale it to 50% of square A
        gl.glScalef(.5f, .5f, .5f);
        // Draw square B.
        draw(gl);

        // SQUARE C
        // Save the current matrix
        gl.glPushMatrix();
        // Make the rotation around B
        gl.glRotatef(-angle, 0, 0, 1);
        gl.glTranslatef(2, 0, 0);
        // Scale it to 50% of square B
        gl.glScalef(.5f, .5f, .5f);
        // Rotate around it's own center.
        gl.glRotatef(angle*10, 0, 0, 1);
        // Draw square C.
        draw(gl);

        // Restore to the matrix as it was before C.
        gl.glPopMatrix();
        // Restore to the matrix as it was before B.
        gl.glPopMatrix();

        // Increse the angle.
        angle++;
    }

    public void draw(GL10 gl) {

//        gl.glFrontFace(GL10.GL_CCW);

//        gl.glEnable(GL10.GL_CULL_FACE);

//        gl.glCullFace(GL10.GL_BACK);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,
                GL10.GL_UNSIGNED_SHORT, indexBuffer);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glDisable(GL10.GL_CULL_FACE);
    }
}
