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
    private static final int NUM_CLOUDFLAKES = 3;       //云数量
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
            case 0:
                if (isDay) {
                    switchImg = 1;
                    paint.setShader(new RadialGradient(150, 100, viewheight - 100, 0x55ffffff, 0x00ffffff, Shader.TileMode.CLAMP));
                } else {
                    switchImg = 0;
                }
                break;
            case 1:
                for (int i = 0; i < NUM_CLOUDFLAKES; ++i) {
                }
                switchImg = 4;
                break;
            case 4:
            case 5:
            case 7:
            case 8:
            case 10:
                for (int i = 0; i < NUM_RAINFLAKES; ++i) {
                }
                switchImg = 2;
                break;
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
            default:
                switchImg = 0;
                break;

        }
        postInvalidate();
    }


    public void setWeatherType(int weatherType, boolean isday) {
//        this.weatherType = weatherType;
//        this.isDay = isday;
        this.weatherType = 14;
        this.isDay = true;
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
        if (switchImg == 1) {
            canvas.drawCircle(150, 100, viewheight - 100, paint);
        }else if (switchImg == 2 || switchImg == 3 || switchImg == 4) {
            for (int i = 0; i < mFlakes.size(); i++) {
                WeatherFlackInterface flake = mFlakes.get(i);
                if (flake != null) {
                    flake.draw(canvas);
                }
            }
        } else{
            canvas.restore();
        }
        invalidate();   //无限刷新
    }

}
