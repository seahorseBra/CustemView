package view;

import android.content.Context;
import android.content.IntentFilter;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

/**
 * Created by zchao on 2016/5/13.
 */
public class ViewGroupTestA extends FrameLayout {

    private static final String TAG = "INTER";
    private int mLastX;
    private int mLastY;
    private boolean needIntercept = false;
    private int mTouchSlop;

    public ViewGroupTestA(Context context) {
        this(context, null);
    }

    public ViewGroupTestA(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewGroupTestA(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        int x = (int) ev.getX();
        int y = (int) ev.getY();
        needIntercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                int dx = Math.abs(x - mLastX);
                int dy = Math.abs(y - mLastY);
                if (dx > mTouchSlop && dx * 0.5f > dy) {
                    needIntercept = true;
                } else {
                    needIntercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
//        return super.onInterceptTouchEvent(ev);
        Log.d(TAG, "A onInterceptTouchEvent() called with: " + "ev = "+ needIntercept +"[" + ev + "]");
        return needIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "A onTouchEvent() called with: " + "event =  "+ needIntercept +"[" + event + "]");
        if (needIntercept) {
            needIntercept = false;
//            requestDisallowInterceptTouchEvent(true);
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

}
