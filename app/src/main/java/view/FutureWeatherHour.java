package view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
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

        initePaint(context);
        for (int i = 0; i < LIST_SIZE; i++) {
            list.add(new HourWeather(20, 1, 35));
        }

    }

    protected void initePaint(Context context) {
        mScroller = new OverScroller(context);

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
        sizeX = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        int sizeY = MeasureSpec.getSize(heightMeasureSpec) - getPaddingBottom() - getPaddingTop();

        itemWidth = sizeX / visiableCount;
        realWidth = itemWidth * list.size();
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
                lastX = (int)x;
                lastY = (int)y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                int dx =  lastX - x;
                int dy =  lastY - y;
                
                if (Math.abs(dx) > mTouchSlop && Math.abs(dx) *0.5> Math.abs(dy)) {
                    getParent().requestDisallowInterceptTouchEvent(true);

                    if (getScrollX() < 0) {
                        dx = 0;
                    }
                    if (getScrollX() > (realWidth - sizeX)) {
                        dx = 0;
                    }
                    scrollBy(dx, 0);
                    lastX = x;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (getScrollX() < 0 ) {
                    mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0, 500);
                }
                if (getScrollX() > realWidth - sizeX) {
                    mScroller.startScroll(getScrollX(), 0, -(getScrollX()-(realWidth - sizeX)), 0, 500);
                }
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        postInvalidate();
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
            Bitmap weatherBitmap = getWeatherBitmap(weather);
            int bitmapW = weatherBitmap.getWidth();
            int bitmapH = weatherBitmap.getHeight();

            canvas.drawText(time, (startX + i * itemWidth - timePaint.measureText(time)/2), timeTextSize, timePaint);
            canvas.drawBitmap(weatherBitmap, startX + i * itemWidth - bitmapW/2, realHeight / 2 - bitmapH / 2, iconPaint);
            canvas.drawLine(0, realHeight/2, realWidth, realHeight/2, linePaint);
            canvas.drawText(temp, (startX + i * itemWidth - tempPaint.measureText(temp)/2), realHeight, tempPaint);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), 0);
            postInvalidate();
        }
    }

    protected Bitmap getWeatherBitmap(int weather) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nd0);
        return Bitmap.createScaledBitmap(bitmap, 80, 80, false);
    }
}
