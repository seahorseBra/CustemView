package view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * 天气状态视图，传入天气类型，根据类型加载不同的天气图, DELAY时间重绘, 绘制NUM_SNOWFLAKES个雪花
 * 目前暂定三种天气：0：晴天
 *                   1：雨
 *                   2：雪
 */
public class WeatherImageView extends View {
    private int weatherType;
    private static final int NUM_SNOWFLAKES = 12; // 雪花数量
    private static final int DELAY = 5; // 延迟
    private WeatherInterface[] mSnowFlakes; //
    private int width;
    private int height;
    private Paint paint;

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
            width = w;
            height = h;
            initSnow();
        }
    }

    private void initSnow() {
        mSnowFlakes = new WeatherInterface[NUM_SNOWFLAKES];
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        if (weatherType == 0) {
           paint.setShader(new RadialGradient(50, 50, 400, 0x55ffffff, 0x00ffffff, Shader.TileMode.CLAMP));
        }
        if (weatherType == 1) {
            for (int i = 0; i < NUM_SNOWFLAKES; ++i) {
                mSnowFlakes[i] = RainFlake.create(width, height, paint, getResources());
            }
        }
        if (weatherType == 2) {
            for (int i = 0; i < NUM_SNOWFLAKES; ++i) {
                mSnowFlakes[i] = SnowFlack.create(width, height, paint);
            }
        }
    }


    public void setWeatherType(int weatherType) {
        this.weatherType = weatherType;
        initSnow();
    }

    public int getWeatherType() {
        return weatherType;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (weatherType == 0) {
            canvas.drawCircle(50,50,400, paint);
        }
        if (weatherType == 1) {
            for (WeatherInterface s : mSnowFlakes) {
                ((RainFlake)s).draw(canvas);
            }
        }
        if (weatherType == 2) {
            for (WeatherInterface s : mSnowFlakes) {
                ((SnowFlack)s).draw(canvas);
            }
        }

    }


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };
}