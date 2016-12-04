package view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by mavin on 2016/8/30.
 */
public class MoveLayoutView extends View {

    private static final String TAG = "MoveLayoutView";
    private float lastX;
    private float lastY;
    private int mTouchSlop;

    public MoveLayoutView(Context context) {
        this(context, null);
    }

    public MoveLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(context));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getRawX();
        float y = event.getRawY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float xDiff = x - lastX;
                float yDiff = y - lastY;
                Log.d(TAG, "onTouchEvent() called with: " + "xDiff = [" + xDiff + "]"+ "yDiff = [" + yDiff + "]" + mTouchSlop);

//                    layout(Math.round(getLeft() + xDiff), Math.round(getTop() + yDiff)
//                            , Math.round(getRight() + xDiff), Math.round(getBottom() + yDiff));
                offsetLeftAndRight(Math.round(xDiff));
                offsetTopAndBottom(Math.round(yDiff));
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        Log.d(TAG, "layout() called with: " + "l = [" + l + "], t = [" + t + "], r = [" + r + "], b = [" + b + "]");
        super.layout(l, t, r, b);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d(TAG, "onLayout() called with: " + "changed = [" + changed + "], left = [" + left + "], top = [" + top + "], right = [" + right + "], bottom = [" + bottom + "]");
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw() called with: " + "canvas = [" + canvas + "]");
        super.onDraw(canvas);
    }
}
