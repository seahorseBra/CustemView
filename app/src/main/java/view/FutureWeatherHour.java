package view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.BounceInterpolator;
import android.widget.OverScroller;

import com.example.administrator.custemview.R;

import java.util.ArrayList;

import Utils.Utils;
import javaBean.HourWeather;

/**
 * Created by zchao on 2016/6/6.
 */
public class FutureWeatherHour extends View {
    protected static final int LIST_SIZE = 36;
    protected static final String TAG = "FutureWeatherHour";
    protected Context context;
    protected ArrayList<HourWeather> list = new ArrayList<>(LIST_SIZE);
    protected int timeTextSize = Utils.dp2Px(14);
    protected int tempTextSize = Utils.dp2Px(14);
    protected int visiableCount = 6;
    protected int textColor = 0x55ff0000;
    protected int minHeight = 200;
    protected int realWidth;
    protected int realHeight;
    protected int weatherWidth;
    protected Paint iconPaint;
    protected Paint timePaint;
    protected Paint tempPaint;
    protected int itemWidth;
    protected int startX = getPaddingLeft() + 50;
    protected Paint linePaint;
    protected int lastX;
    protected OverScroller mScroller;
    protected int lastY;
    protected int mTouchSlop;
    protected int sizeX;
    private boolean isInDrag = false;
    private int DAMP_DEFAULT = 100;
    private Bitmap bitmap = null;

    private VelocityTracker mVelocityTracker;
    private int MIN_VELOCITY = 100;
    private int MAX_VELOCITY = 200;
    private int mPointerId;

    public FutureWeatherHour(Context context) {
        this(context, null);
    }

    public FutureWeatherHour(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FutureWeatherHour(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        mScroller = new OverScroller(context, new BounceInterpolator());

        initePaint(context);
        for (int i = 0; i < LIST_SIZE; i++) {
            list.add(new HourWeather(20, 1, 35));
        }

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        sizeX = metrics.widthPixels - getPaddingLeft() - getPaddingRight();
        itemWidth = sizeX / visiableCount;
        realWidth = itemWidth * list.size() - itemWidth / 2;

        bitmap = getWeatherBitmap();
    }

    protected void initePaint(Context context) {

        timePaint = new Paint();
        iconPaint = new Paint();
        tempPaint = new Paint();
        linePaint = new Paint();

        timePaint.setTextSize(tempTextSize);
        timePaint.setColor(textColor);
        tempPaint.setTextSize(tempTextSize);
        tempPaint.setColor(textColor);

        iconPaint.setAntiAlias(true);
        linePaint.setColor(Color.RED);
    }

    public ArrayList<HourWeather> getList() {
        return list;
    }

    public void setList(ArrayList<HourWeather> list) {
        this.list = list;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeY = MeasureSpec.getSize(heightMeasureSpec) - getPaddingBottom() - getPaddingTop();
        realHeight = Math.max(sizeY, minHeight);
        setMeasuredDimension(realWidth, realHeight);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                acquireVelocityTracker(event);

                mPointerId = event.getPointerId(0);
                getParent().requestDisallowInterceptTouchEvent(false);
                lastX = (int) x;
                lastY = (int) y;
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = lastX - x;
                int dy = lastY - y;
                if (Math.abs(dy) > mTouchSlop && Math.abs(dx) * 0.5f < Math.abs(dy)) {
                    lastX = x;
                    lastY = y;
                    return false;
                }

                if (!isInDrag) {
                    if (Math.abs(dx) > mTouchSlop && Math.abs(dx) * 0.5f > Math.abs(dy)) {
                        isInDrag = true;
                    }
                }
                if (isInDrag) {
                    getParent().requestDisallowInterceptTouchEvent(true);

                    if (getScrollX() < 0 && dx < 0) {
                        dx = -Math.min(((DAMP_DEFAULT--) / 10), Math.abs(dx));
                        if (dx >= 0) dx = 0;
                    }
                    if (getScrollX() > (realWidth - sizeX) && dx > 0) {
                        dx = Math.min(((DAMP_DEFAULT--) / 10), Math.abs(dx));
                        if (dx <= 0) dx = 0;
                    }
                    scrollBy(dx, 0);
                    lastX = x;
                    lastY = y;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                DAMP_DEFAULT = 100;
//                final VelocityTracker verTracker = mVelocityTracker;
//                verTracker.computeCurrentVelocity(1000, MAX_VELOCITY);
//                final float velocityX = verTracker.getXVelocity(mPointerId);
//mScroller.fling();
                if (getScrollX() < 0) {
                    mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0, 500);
                    postInvalidate();
                }
                if (getScrollX() > realWidth - sizeX) {
                    mScroller.startScroll(getScrollX(), 0, -(getScrollX() - (realWidth - sizeX)), 0, 500);
                    postInvalidate();
                }
                isInDrag = false;
                getParent().requestDisallowInterceptTouchEvent(false);
                releaseVelocityTracker();
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        for (int i = 0; i < LIST_SIZE; i++) {
            HourWeather hourWeather = list.get(i);
            int weather = hourWeather.getWeather();
            String time = String.valueOf(hourWeather.getTime() + i);
            String temp = String.valueOf(hourWeather.getTemp()) + "°";
            Bitmap weatherBitmap = bitmap;
            int bitmapW = weatherBitmap.getWidth();
            int bitmapH = weatherBitmap.getHeight();

            if (startX + (i + 1) * itemWidth > getScrollX() && startX + (i - 1) * itemWidth < sizeX + getScrollX()) {
                canvas.drawText(time, (startX + i * itemWidth - timePaint.measureText(time) / 2), timeTextSize, timePaint);
                canvas.drawBitmap(weatherBitmap, startX + i * itemWidth - bitmapW / 2, realHeight / 2 - bitmapH / 2, iconPaint);
                canvas.drawText(temp, (startX + i * itemWidth - tempPaint.measureText(temp) / 2), realHeight, tempPaint);
            }
        }
    }

    @Override
    public void computeScroll() {
        Log.d(TAG, "computeScroll() called with: " + "");
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), 0);
            postInvalidate();
        }
    }

    protected Bitmap getWeatherBitmap() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nd0);
        return Bitmap.createScaledBitmap(bitmap, 80, 80, false);
    }

    private void acquireVelocityTracker(final MotionEvent event) {
        if (null == mVelocityTracker) {
            mVelocityTracker = VelocityTracker.obtain();
        } else {
            mVelocityTracker.clear();
        }
        mVelocityTracker.addMovement(event);
    }

    private void releaseVelocityTracker() {
        if (null != mVelocityTracker) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

}
