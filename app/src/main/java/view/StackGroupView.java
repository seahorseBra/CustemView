package view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
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
    private int mLastY = 0;
    private OverScroller mScroller;
    private int mEnd;
    private int mStart;


    public StackGroupView(Context context) {
        this(context, null);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public StackGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new OverScroller(context);
        mScreenHeight = context.getResources().getDisplayMetrics().heightPixels;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int y = (int)event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                mStart = getScrollY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                int dy = mLastY - y;
                if (getScrollY() < 0) {
                    dy = 0;
                }
                if (getScrollY() > (getChildCount()- 1) * mScreenHeight) {
                    dy = 0;
                }
                scrollBy(0, dy);
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                mEnd = getScrollY();
                int dScrollY = mEnd - mStart;
                if (dScrollY > 0) {
                    if (dScrollY < mScreenHeight / 3) {
                        mScroller.startScroll(0, getScrollY(), 0, -dScrollY);
                    } else {
                        mScroller.startScroll(0, getScrollY(), 0, mScreenHeight - dScrollY);
                    }
                } else {
                    if (Math.abs(dScrollY) < mScreenHeight / 3) {
                        mScroller.startScroll(0, getScrollY(), 0, -dScrollY);
                    } else {
                        mScroller.startScroll(0, getScrollY(), 0, -mScreenHeight - dScrollY);
                    }
                }
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
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; ++i) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childCount = getChildCount();

        MarginLayoutParams layoutParams = (MarginLayoutParams)getLayoutParams();
        layoutParams.height = mScreenHeight * childCount;
        setLayoutParams(layoutParams);
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                child.layout(l, mScreenHeight * i, r, mScreenHeight * (i + 1));
            }
        }
    }
}
