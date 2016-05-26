package view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by zchao on 2016/5/17.
 */
public class BarrageViewGroup extends ViewGroup {
    private static final String TAG = "BarrageViewGroup";
    private int horizontalInterval = 10;
    private int verticalInterval = 5;
    private int width;
    private int height;


    public BarrageViewGroup(Context context) {
        this(context, null);
    }

    public BarrageViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarrageViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingRight() - getPaddingLeft();
        height = MeasureSpec.getSize(heightMeasureSpec);

        int mode = MeasureSpec.getMode(heightMeasureSpec);
        if (mode == MeasureSpec.AT_MOST) {
            int totleHeight = 0;
            int lineMaxHeight = 0;
            int totlewidth = 0;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                int measuredWidth = child.getMeasuredWidth();
                int measuredHeight = child.getMeasuredHeight();

                totlewidth += (measuredWidth + horizontalInterval);
                lineMaxHeight = Math.max(lineMaxHeight, measuredHeight);

                if (totlewidth > width) {
                    totleHeight += lineMaxHeight + verticalInterval;
                    totlewidth = measuredWidth;
                    lineMaxHeight = measuredHeight;
                }

                if (totlewidth <= width && i == getChildCount() -1) {
                    totleHeight += lineMaxHeight;
                }
            }

            height = totleHeight + getPaddingTop() + getPaddingBottom();
        }
        setMeasuredDimension(width + getPaddingLeft() + getPaddingRight(), height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();

        int startY = 0;
        int startX = 0;
        int lineMaxHeight = 0;
        int lineTotleWidth = 0;

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int measuredWidth = child.getMeasuredWidth();
            int measuredHeight = child.getMeasuredHeight();
            lineTotleWidth = (startX + measuredWidth);
            lineMaxHeight = Math.max(measuredHeight, lineMaxHeight);

            if (lineTotleWidth > width) {
                startX = 0;
                startY += (lineMaxHeight + verticalInterval);
                lineMaxHeight = measuredHeight;
                lineTotleWidth = measuredWidth;
            }

            if (child.getVisibility() != GONE) {
                child.layout(startX, startY, startX + measuredWidth, startY + measuredHeight);
            }
            startX += (measuredWidth + horizontalInterval);
        }
    }

}
