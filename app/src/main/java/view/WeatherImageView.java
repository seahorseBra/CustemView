package view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;


import com.example.administrator.custemview.R;

import java.util.ArrayList;

import utils.Utils;


/**
 * 天气状态视图，传入天气类型，根据类型加载不同的天气图, DELAY时间重绘, 绘制NUM_SNOWFLAKES个雪花
 * 目前暂定三种天气：0：晴天
 *                   1：多云
 *                   19,10,8,7,5,4：雨
 *                   14：雪
 *                   30：霾
 *
 */

public class WeatherImageView extends View {
    private int weatherType = -1;
    private boolean isDay = true;
    private static final int NUM_SNOWFLAKES = 50;       //雪数量
    private static final int NUM_RAINFLAKES = 10;       //雨数量
    private static final int NUM_CLOUDFLAKES = 1;       //云数量
    private static final int NUM_SUNFLAKES = 1;         //太阳数量
    private static final int NUM_MAIFLAKES = 1;         //霾数量
    private static final int DELAY = 5;
    private ArrayList<WeatherFlackInterface> mFlakes;   //各种天气图片集合
    private int mWidth;
    private int mHeight;
    private Paint paint;
    private int switchImg = 0;
    private int viewheight = (int) Utils.dp2Px(getResources(), 220);

    public WeatherImageView(Context context) {
        this(context, null);
    }

    public WeatherImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mFlakes = new ArrayList<>();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            mHeight = h;
            mWidth = w;
            iniSnow();
        }
    }

    private void initSnow() {
        if (mWidth <= 0 || mHeight <= 0) {
            return;
        }
        switch (weatherType) {
            //太阳
            case 0:
                if (isDay) {
                    Bitmap sunBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tq_bg_sun_pic);
                    Bitmap sunBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.tq_bg_sun_shine2);
                    mFlakes.clear();
                    for (int i = 0; i < NUM_SUNFLAKES; ++i) {
                        SunnyFlack snowFlack = SunnyFlack.create(mWidth, mHeight, paint, sunBitmap, sunBitmap1);
                        if (snowFlack != null) {
                            mFlakes.add(snowFlack);
                        }
                    }
                    switchImg = 1;
                } else {
                    switchImg = 0;
                }
                break;
            //云
            case 1:
                Bitmap cloudBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tq_bg_cloud_pic);
                mFlakes.clear();
                for (int i = 0; i < NUM_CLOUDFLAKES; ++i) {
                    CloudFlack snowFlack = CloudFlack.create(mWidth, mHeight, paint, cloudBitmap);
                    if (snowFlack != null) {
                        mFlakes.add(snowFlack);
                    }
                }
                switchImg = 4;
                break;
            //各种雨
            case 4:
            case 5:
            case 7:
            case 8:
            case 10:
                Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.tq_bg_rain_icon);
                mFlakes.clear();
                for (int i = 0; i < NUM_RAINFLAKES; ++i) {
                    RainFlack snowFlack = RainFlack.create(mWidth, mHeight, paint, bitmap1);
                    if (snowFlack != null) {
                        mFlakes.add(snowFlack);
                    }
                }
                switchImg = 2;
                break;
            //雪
            case 14:
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.snow);
                mFlakes.clear();
                for (int i = 0; i < NUM_SNOWFLAKES; ++i) {
                    SnowFlack snowFlack = SnowFlack.create(mWidth, mHeight, paint, bitmap);
                    if (snowFlack != null) {
                        mFlakes.add(snowFlack);
                    }
                }
                switchImg = 3;
                break;
            //霾
            case 30:
                Bitmap bitmapMai = BitmapFactory.decodeResource(getResources(), R.drawable.tq_bg_haze_pic);
                mFlakes.clear();
                for (int i = 0; i < NUM_MAIFLAKES; ++i) {
                    MaiFlack snowFlack = MaiFlack.create(mWidth, mHeight, paint, bitmapMai);
                    if (snowFlack != null) {
                        mFlakes.add(snowFlack);
                    }
                }
                switchImg = 5;
                break;
            default:
                switchImg = 0;
                break;

        }
        postInvalidate();
    }


    public void setWeatherType(int weatherType, boolean isday) {
        this.weatherType = weatherType;
        this.isDay = isday;
        iniSnow();
    }

    private void iniSnow() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                initSnow();
            }
        }).start();
    }

    public int getWeatherType() {
        return weatherType;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (switchImg == 1 || switchImg == 2 || switchImg == 3 || switchImg == 4|| switchImg == 5) {
            for (int i = 0; i < mFlakes.size(); i++) {
                WeatherFlackInterface flake = mFlakes.get(i);
                if (flake != null) {
                    flake.draw(canvas);
                }
            }
        } else{
            canvas.restore();
        }
        if (switchImg == 1 ||switchImg == 2 || switchImg == 3 || switchImg == 4) {
            invalidate();   //有动画的需要无限刷新
        }
    }

}
