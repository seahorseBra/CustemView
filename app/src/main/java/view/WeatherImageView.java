package view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 天气状态视图，传入天气类型，根据类型加载不同的天气图, DELAY时间重绘, 绘制NUM_SNOWFLAKES个雪花
 * 目前暂定三种天气：0：晴天
 *                   1：雨
 *                   2：雪
 */
public class WeatherImageView extends View {
    private int weatherType = 0;
    private static final int NUM_SNOWFLAKES = 8; // 雪花数量
    private static final int DELAY = 5; // 延迟
    private WeatherInterface[] mSnowFlakes; //

    public WeatherImageView(Context context) {
        this(context, null);
    }

    public WeatherImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            initSnow(w, h);
        }
    }

    private void initSnow(int width, int height) {
        Paint paint = new Paint(); // 抗锯齿
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE); // 填充;
        paint.setColor(Color.WHITE);
        mSnowFlakes = new WeatherInterface[NUM_SNOWFLAKES];
        //mSnowFlakes所有的雪花都生成放到这里面
        for (int i = 0; i < NUM_SNOWFLAKES; ++i) {
            mSnowFlakes[i] = RainFlake.create(width, height, paint, 50);
        }
    }

    public void setWeatherType(int weatherType) {
        this.weatherType = weatherType;
    }

    public int getWeatherType() {
        return weatherType;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //for返回SnowFlake
        for (WeatherInterface s : mSnowFlakes) {
            //然后进行绘制
            ((RainFlake)s).draw(canvas);
        }
        // 隔一段时间重绘一次, 动画效果
        getHandler().postDelayed(runnable, DELAY);
    }

    // 重绘线程
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //自动刷新
            invalidate();
        }
    };
}