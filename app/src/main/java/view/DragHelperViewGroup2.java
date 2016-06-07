package view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by zchao on 2016/5/13.
 */
public class DragHelperViewGroup2 extends LinearLayout {

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
                ViewCompat.postInvalidateOnAnimation(DragHelperViewGroup2.this);
            } else {
                viewDragHelper.smoothSlideViewTo(mMainView, mWidth, 0);
                ViewCompat.postInvalidateOnAnimation(DragHelperViewGroup2.this);
            }
        }

    };
    private int mWidth;
    private int mLastX;
    private int mLastY;
    private int mTouchSlop;


    public DragHelperViewGroup2(Context context) {
        this(context, null);
    }

    public DragHelperViewGroup2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public DragHelperViewGroup2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        viewDragHelper = ViewDragHelper.create(this, callback);

        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration)/3;
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
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
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
