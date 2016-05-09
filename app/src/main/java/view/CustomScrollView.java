package view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.OverScroller;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * Created by zchao on 2016/5/9.
 */
public class CustomScrollView extends ViewGroup {
    private static final String TAG = "CustomScrollView";
    private int mScreenHeight;
    private int mLastY = 0;
    private OverScroller mScroller;
    private int mEnd;
    private int mStart;
    private MarginLayoutParams layoutParams;

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new OverScroller(context);
//        mScreenHeight = context.getResources().getDisplayMetrics().heightPixels;
        mScreenHeight = 400;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int y = (int)event.getY();
//        Log.d(TAG, "onTouchEvent() called with: " + "event = [" + getScrollY() + "]" + y);
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
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int childCount = getChildCount();
        layoutParams = (MarginLayoutParams)getLayoutParams();

        Log.d(TAG, "onMeasure() called with: " + "mode = [" + mode +"]");
        switch (mode) {
            case MeasureSpec.AT_MOST:
                layoutParams.height = mScreenHeight * childCount;
                break;
            case MeasureSpec.EXACTLY:
                layoutParams.height = size;
                mScreenHeight = size;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        for (int i = 0; i < childCount; ++i) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        setLayoutParams(layoutParams);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                child.layout(l, mScreenHeight * i, r, mScreenHeight * (i + 1));
            }
        }
    }
}
