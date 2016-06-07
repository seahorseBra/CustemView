package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.app.NavUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.OverScroller;

/**
 * Created by mavin on 2016/6/6.
 */
public class DampView extends View {

    private static final String TAG = "DampView";
    private int size;
    private Paint paint;
    private int itemWidth;
    private OverScroller overScroller;
    private int lastX;
    private int realWidth;

    public DampView(Context context) {
        this(context, null);
    }

    public DampView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DampView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        size = metrics.widthPixels;
        itemWidth = (size - getPaddingLeft() - getPaddingRight())/6;
        realWidth = itemWidth * 20;
        overScroller = new OverScroller(context, new DecelerateInterpolator());
        paint = new Paint();
        paint.setTextSize(dp2px(14));
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!overScroller.isFinished()) {
                    overScroller.abortAnimation();
                }
                int dx = (lastX - x);
                if (getScrollX() < 0) {
                    dx = 0;
                }
                if (getScrollX() > realWidth - (size - getPaddingLeft() - getPaddingRight())) {
                    dx = 0;
                }
                
                
                scrollBy(dx, 0);
                lastX =  x;
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
//        postInvalidate();
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (overScroller.computeScrollOffset()) {
            scrollTo(overScroller.getCurrX(), 0);
        }
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure() called with: " + "widthMeasureSpec = [" + widthMeasureSpec + "], heightMeasureSpec = [" + heightMeasureSpec + "]");

        setMeasuredDimension(realWidth, 200);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < 20; i++) {
            canvas.drawText(String.valueOf(i),  i * itemWidth, 100, paint);
        }
    }

    private float dp2px(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return density * dp;
    }
}
