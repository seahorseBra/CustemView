package view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by zchao on 2016/11/9.
 */

public class CaleanderGroup extends FrameLayout {

    private ViewDragHelper helper;
    private Scroller scroller;
    private int mTouchSlop;
    private VelocityTracker tracker;
    private GestureDetector detector;
    private int minVelocity;
    private int maxVelocitu;
    private View view0;
    private View view1;
    private View view2;

    public CaleanderGroup(Context context) {
        this(context, null);
    }
    public CaleanderGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        minVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
        maxVelocitu = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        detector = new GestureDetector(context, listener);
    }

    GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener(){

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            ViewCompat.offsetLeftAndRight(view0, (int) distanceX);
            ViewCompat.offsetLeftAndRight(view1, (int) distanceX);
            ViewCompat.offsetLeftAndRight(view2, (int) distanceX);
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return true;
        }
    };

    ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            String tag = (String) child.getTag();

            return tag.equals("1") ;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            View view0 = findViewWithTag("0");
            View view1 = findViewWithTag("1");
            View view2 = findViewWithTag("2");
            ViewCompat.offsetLeftAndRight(view0, dx);
            ViewCompat.offsetLeftAndRight(view2, dx);
            ViewCompat.offsetTopAndBottom(view0, 0);
            ViewCompat.offsetTopAndBottom(view2, 0);
        }



        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            View view0 = findViewWithTag("0");
            View view1 = findViewWithTag("1");
            View view2 = findViewWithTag("2");
            if (releasedChild.getLeft() > getWidth() / 2) {
                helper.smoothSlideViewTo(view1, getWidth(), 0);
                helper.smoothSlideViewTo(view0, 0, 0);
                ViewCompat.postInvalidateOnAnimation(CaleanderGroup.this);
                view0.setTag("1");
                view1.setTag("2");
                view2.setTag("0");
                requestLayout();
            } else if (releasedChild.getLeft() < -getWidth() / 2) {
                helper.smoothSlideViewTo(view1, -getWidth(), 0);
                helper.smoothSlideViewTo(view2, 0, 0);
                ViewCompat.postInvalidateOnAnimation(CaleanderGroup.this);
                view0.setTag("2");
                view1.setTag("0");
                view2.setTag("1");
                requestLayout();
            } else {
//                helper.smoothSlideViewTo(view0, -getWidth(), 0);
                helper.smoothSlideViewTo(view1, 0, 0);
//                helper.smoothSlideViewTo(view2, getWidth(), 0);
                ViewCompat.postInvalidateOnAnimation(CaleanderGroup.this);
            }
        }
    };

    @Override
    protected void onFinishInflate() {
        view0 = findViewWithTag("0");
        view1 = findViewWithTag("1");
        view2 = findViewWithTag("2");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        view0.layout(-getWidth(), 0, 0, getHeight());
        view1.layout(0, 0, getWidth(), getHeight());
        view2.layout(getWidth(), 0, getWidth() * 2, getHeight());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
//        int action = ev.getAction();
//        if (tracker == null) {
//            tracker = VelocityTracker.obtain();
//        }
//        tracker.addMovement(ev);
//        tracker.computeCurrentVelocity();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//
//                break;
//            case MotionEvent.ACTION_MOVE:
//
//                break;
//            case MotionEvent.ACTION_UP:
//
//                break;
//        }
//        return helper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return true;
    }


    @Override
    public void computeScroll() {
        if (helper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}
