package view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by zchao on 2016/7/12.
 */
public class OpenGLView extends GLSurfaceView {

    private float lastY;
    private float lastX;
    private OpenGLRenderer openGLRenderer;

    public OpenGLView(Context context) {
        this(context, null);
    }

    public OpenGLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        openGLRenderer = new OpenGLRenderer();
        setRenderer(openGLRenderer);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {

        queueEvent(new Runnable() {
            @Override
            public void run() {
                float x = event.getX();
                float y = event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = x;
                        lastY = y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float diffX = x - lastX;
                        float diffY = y - lastY;

                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
            }
        });
        return true;
    }
}
