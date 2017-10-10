package view;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.custemview.R;

/**
 * Created by zchao on 2017/10/10.
 * desc:
 * version:
 */

public class BreakViewGroup extends ViewGroup {
    public BreakViewGroup(Context context) {
        super(context);
    }

    public BreakViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BreakViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt != null && childAt.getVisibility() != GONE) {
                measureChild(childAt);
            }
        }
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int totleHeight = 0;
        int lineWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            int measuredWidth = childAt.getMeasuredWidth();
            int measuredHeight = childAt.getMeasuredHeight();
            if (measuredWidth <= size / 3) {
                measuredWidth = size / 3;
            } else if (measuredWidth <= size * 2 / 3) {
                measuredWidth = size * 2 / 3;
            } else {
                measuredWidth = size;
            }
            childAt.setTag(getId(), new Postion(lineWidth, totleHeight));
            lineWidth += measuredWidth;
            if (lineWidth > size) {
                lineWidth = measuredWidth;
                totleHeight += measuredHeight;
                childAt.setTag(getId(), new Postion(0, totleHeight));
                continue;
            }
        }
        if (getChildCount() > 0) {
            totleHeight += getChildAt(0).getMeasuredHeight();
        }
        setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(totleHeight, MeasureSpec.EXACTLY));
    }

    public void measureChild(View child) {
        child.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            Postion pos = (Postion) childAt.getTag(getId());
            childAt.layout(pos.x, pos.y, pos.x + childAt.getMeasuredWidth(), pos.y + childAt.getMeasuredHeight());
//            if (pos.x > getWidth() * 2 / 3 - 3) {
//                childAt.layout(r - childAt.getMeasuredWidth(), pos.y, r, pos.y + childAt.getMeasuredHeight());
//            } else {
//                childAt.layout(pos.x, pos.y, pos.x + childAt.getMeasuredWidth(), pos.y + childAt.getMeasuredHeight());
//            }
        }
    }

    public class Postion {
        public Postion(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int x;
        int y;

    }
}
