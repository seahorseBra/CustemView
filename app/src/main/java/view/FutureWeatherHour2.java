package view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
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
public class FutureWeatherHour2 extends View {
    protected static final int LIST_SIZE = 35;
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

    private  ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            /*if (mMainView.getLeft() < mWidth / 2) {
                viewDragHelper.smoothSlideViewTo(mMainView, 0, 0);
                ViewCompat.postInvalidateOnAnimation(DragHelperViewGroup.this);
            } else {
                viewDragHelper.smoothSlideViewTo(mMainView, mWidth, 0);
                ViewCompat.postInvalidateOnAnimation(DragHelperViewGroup.this);
            }*/
        }

    };

    public FutureWeatherHour2(Context context) {
        this(context, null);
    }

    public FutureWeatherHour2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FutureWeatherHour2(Context context, AttributeSet attrs, int defStyleAttr) {
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
        realWidth = itemWidth * list.size();

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
       /* if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }*/
    }

    protected Bitmap getWeatherBitmap(int weather) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nd0);
        return Bitmap.createScaledBitmap(bitmap, 80, 80, false);
    }
}
