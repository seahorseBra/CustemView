package view;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.OverScroller;

/**
 * Created by zchao on 2016/5/9.
 */
public class StackGroupView extends ViewGroup {

    private static final String TAG = "StackGroupView";
    private int mScreenHeight;
    private int mViewWidth;
    private int mLastX = 0;
    private OverScroller mScroller;
    private int firstViewPosition = 0;
    private int mEnd;
    private int mStart;


    public StackGroupView(Context context) {
        this(context, null);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public StackGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScreenHeight = context.getResources().getDisplayMetrics().heightPixels;
        mViewWidth = context.getResources().getDisplayMetrics().widthPixels;
        mScroller = new OverScroller(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        View childAt = getChildAt(firstViewPosition);
        int x = (int)event.getX();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mStart = x;
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = mLastX - x;
                if (dx >) {
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        postInvalidate();
        return true;
    }

    /**
     * mScroller直接调用startScroll是不会有滚动效果的，只有在此方法中获取滚动情况才会做出滚动响应；
     * 此方法在父控件执行drawChild时会调用
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void computeScroll() {
        super.computeScroll();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureWidth(widthMeasureSpec);
        measureHeight(heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; ++i) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }

    }

    private int measureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);

        switch (mode){
            case MeasureSpec.AT_MOST:

                break;
            case MeasureSpec.EXACTLY:
                mViewWidth = size;
                break;
        }
        return size;
    }
    private int measureHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);

        switch (mode){
            case MeasureSpec.AT_MOST:
                mScreenHeight = mScreenHeight/3;
                break;
            case MeasureSpec.EXACTLY:
                mScreenHeight = size;
                break;
        }
        return size;
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childCount = getChildCount();
        int maxViewWidth = (mViewWidth - 60) / 3;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                break;
            }
            if (i < firstViewPosition || firstViewPosition + 4 < i) {
                child.layout(0, mScreenHeight/2, 0, mScreenHeight/2);
            }
            if (firstViewPosition == i) {
                child.layout(0, mScreenHeight / 3, maxViewWidth / 3, mScreenHeight * 2 / 3);
            }
            if (firstViewPosition + 1 == i) {
                if (child.getVisibility() != GONE) {
                    child.layout(maxViewWidth / 3 + 10, mScreenHeight / 6, maxViewWidth + 10, mScreenHeight * 5 / 6);
                }
            }
            if (firstViewPosition + 2 == i) {
                if (child.getVisibility() != GONE) {
                    child.layout(mViewWidth / 2 -  maxViewWidth / 2, 0, mViewWidth / 2 + maxViewWidth / 2, mScreenHeight);
                }
            }
            if (firstViewPosition + 3 == i) {
                if (child.getVisibility() != GONE) {
                    child.layout(mViewWidth / 2 + maxViewWidth / 2 + 20,  mScreenHeight / 6, mViewWidth / 2 + maxViewWidth * 7/ 6 + 20, mScreenHeight * 5 / 6);
                }
            }
            if (firstViewPosition + 4 == i) {
                if (child.getVisibility() != GONE) {
                    child.layout(mViewWidth - maxViewWidth / 3, mScreenHeight / 3, mViewWidth, mScreenHeight * 2 / 3);
                }
            }
        }
    }
}
