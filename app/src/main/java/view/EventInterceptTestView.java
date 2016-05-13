package view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by zchao on 2016/5/13.
 */
public class EventInterceptTestView extends TextView {
    private static final String TAG = "INTER";

    public EventInterceptTestView(Context context) {
        this(context, null);
    }

    public EventInterceptTestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EventInterceptTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "View onTouchEvent() called with: " + "event = [" + event + "]");
        return true;
    }
}
