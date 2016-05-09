package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zchao on 2016/5/9.
 */
public class HorizontalScrollView extends ViewGroup {



    public HorizontalScrollView(Context context) {
        this(context, null);
    }

    public HorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        MarginLayoutParams layoutParams = (MarginLayoutParams) getLayoutParams();

        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() != GONE) {
//                childAt.layout();
            }
        }
    }
}
