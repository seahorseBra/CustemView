package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by zchao on 2016/5/9.
 */
public class SurfaceViewTest extends SurfaceView implements SurfaceHolder.Callback, Runnable{
    public SurfaceViewTest(Context context) {
        super(context);
    }

    public SurfaceViewTest(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void run() {

    }
}
