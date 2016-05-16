package view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by zchao on 2016/5/13.
 */
public class DragHelperViewGroup extends FrameLayout {

    private static final String TAG = "DragHelperViewGroup";
    private ViewDragHelper viewDragHelper;
    private View mMainView;
    private View mMenuView;
    private boolean needDragLeft = false;

    private  ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return mMainView == child;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (mMainView.getLeft() < mWidth / 2) {
                viewDragHelper.smoothSlideViewTo(mMainView, 0, 0);
                ViewCompat.postInvalidateOnAnimation(DragHelperViewGroup.this);
            } else {
                viewDragHelper.smoothSlideViewTo(mMainView, mWidth, 0);
                ViewCompat.postInvalidateOnAnimation(DragHelperViewGroup.this);
            }
        }

    };
    private int mWidth;
    private int mLastX;
    private int mLastY;
    private int mTouchSlop;


    public DragHelperViewGroup(Context context) {
        this(context, null);
    }

    public DragHelperViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragHelperViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        viewDragHelper = ViewDragHelper.create(this, callback);

        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMenuView = getChildAt(0);
        mMainView = getChildAt(1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = mMenuView.getMeasuredWidth();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent() called with: " + "ev = [" + ev + "]");
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = Math.abs(x - mLastX);
                int dy = Math.abs(y - mLastY);
                if (dx > mTouchSlop && dx * 0.5f > dy && x > mLastX) {
                    needDragLeft = viewDragHelper.shouldInterceptTouchEvent(ev);
                    needDragLeft = true;
                } else {
                    needDragLeft = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return needDragLeft;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (needDragLeft) {
            viewDragHelper.processTouchEvent(event);
            return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    public void computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

}
