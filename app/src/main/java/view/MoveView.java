package view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

/**
 * Created by zchao on 2016/5/13.
 */
public class MoveView extends View {


    private static final String TAG = "MoveView";
    private int mLastX;
    private int mLastY;
    private Scroller mScroller;

    public MoveView(Context context) {
        this(context, null);
    }

    public MoveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }



    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        View parent = (View) getParent();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - mLastX;
                int offsetY = y - mLastY;
//                offsetLeftAndRight(offsetX);
//                offsetTopAndBottom(offsetY);

//                ViewGroup.MarginLayoutParams ma = (ViewGroup.MarginLayoutParams) getLayoutParams();
//                ma.leftMargin = (int) (getLeft() + offsetX);
//                ma.topMargin = (int) (getTop() + offsetY);
//                setLayoutParams(ma);

                parent.scrollBy(0, -offsetY);
                break;
            case MotionEvent.ACTION_UP:

                mScroller.startScroll(parent.getScrollX(), parent.getScrollY(), -parent.getScrollX(), -parent.getScrollY(), 2000);
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            ((View)getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw() called with: " + "canvas = [" + canvas + "]");
        super.onDraw(canvas);
    }
}
