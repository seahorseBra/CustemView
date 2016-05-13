package view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by zchao on 2016/5/13.
 */
public class ViewGroupTestB extends LinearLayout {
    private static final String TAG = "INTER";

    public ViewGroupTestB(Context context) {
        this(context, null);
    }

    public ViewGroupTestB(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, " B onInterceptTouchEvent() called with: " + "ev = [" + ev + "]");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, " B onTouchEvent() called with: " + "event = [" + event + "]");
        return super.onTouchEvent(event);
    }
}
