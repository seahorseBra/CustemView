package view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.BounceInterpolator;
import android.widget.OverScroller;

import com.example.administrator.custemview.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import utils.Utils;
import javaBean.HourWeather;

/**
 * Created by zchao on 2016/6/6.
 */
public class FutureWeatherHour2 extends View {
    protected static final int LIST_SIZE = 7;
    protected static final String TAG = "FutureWeatherHour2";
    protected Context context;
    protected ArrayList<HourWeather> list = new ArrayList<>();
    private Map<String, Bitmap> bitmapMap = new HashMap<>();
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
    protected int mTouchSlop;
    protected int sizeX;

    public FutureWeatherHour2(Context context) {
        this(context, null);
    }

    public FutureWeatherHour2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FutureWeatherHour2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        initePaint(context);
        for (int i = 0; i < LIST_SIZE; i++) {
            list.add(new HourWeather(1+i, 1, 35));
        }

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        sizeX = metrics.widthPixels - getPaddingLeft() - getPaddingRight();
        itemWidth = sizeX / visiableCount;
        realWidth = itemWidth * list.size();
        Log.d(TAG, "FutureWeatherHour2() called with: " + "context = [" + context + "], attrs = [" + attrs + "], defStyleAttr = [" + defStyleAttr + "]");
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
        realWidth = itemWidth * list.size();
        requestLayout();
//        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeY = MeasureSpec.getSize(heightMeasureSpec) - getPaddingBottom() - getPaddingTop();
        realHeight = Math.max(sizeY, minHeight);
        Log.d(TAG, "onMeasure() called with: " + " = [" + MeasureSpec.getMode(widthMeasureSpec) + "|" + MeasureSpec.getSize(widthMeasureSpec) +
                "],  = [" +  MeasureSpec.getMode(heightMeasureSpec) + "|" + MeasureSpec.getSize(heightMeasureSpec) + "]");
        setMeasuredDimension(realWidth, realHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged() called with: " + "w = [" + w + "], h = [" + h + "], oldw = [" + oldw + "], oldh = [" + oldh + "]");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout() called with: " + "changed = [" + changed + "], left = [" + left + "], top = [" + top + "], right = [" + right + "], bottom = [" + bottom + "]");

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw() called with: " + "canvas = [" + canvas + "]");
        for (int i = 0; i < list.size(); i++) {
            HourWeather hourWeather = list.get(i);
            int weather = hourWeather.getWeather();
            String time = String.valueOf(hourWeather.getTime());
            String temp = String.valueOf(hourWeather.getTemp()) + "°";

            canvas.drawText(time, (startX + i * itemWidth - timePaint.measureText(time)/2), timeTextSize, timePaint);
            canvas.drawText(temp, (startX + i * itemWidth - tempPaint.measureText(temp)/2), realHeight, tempPaint);
        }
    }


    protected Bitmap getWeatherBitmap(int weather) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nd0);
        return Bitmap.createScaledBitmap(bitmap, 80, 80, false);
    }
}
