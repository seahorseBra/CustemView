package view;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.nineoldandroids.animation.TypeEvaluator;
import com.nineoldandroids.animation.ValueAnimator;

import utils.RandomGenerator;


/**
 * 雨的类, 移动, 移出屏幕会重新设置位置.
 */
public class RainFlack implements WeatherFlackInterface {
    private final Paint mPaint;                 // 画笔
    private final Bitmap bitmap;                //雨图片
    private final ValueAnimator valueAnimator;
    private final int mAlpha;                   //雨透明度
    private int mViewWidth ,mViewHeight;        //View的总宽高
    private Matrix matrix;

    private SunnyE mSunnyE;


    private RainFlack(int viewWidth, int viewHeight, Paint paint, Bitmap bitmap) {
        this.bitmap = bitmap;
        mViewWidth = viewWidth;
        mViewHeight = viewHeight;

        int x = (int) RandomGenerator.getRandom(0, viewWidth);
        int y = -bitmap.getHeight();
        mSunnyE = new SunnyE(x, y);
        mPaint = paint;
        mAlpha = (int) RandomGenerator.getRandom(100, 255);


        matrix = new Matrix();

        valueAnimator = new ValueAnimator();
        valueAnimator.setObjectValues(new SunnyE(x, y), new SunnyE(0, mViewHeight));
        valueAnimator.setStartDelay(RandomGenerator.getRandom(6000));
        valueAnimator.setEvaluator(new TypeEvaluator<SunnyE>(){
            @Override
            public SunnyE evaluate(float fraction, SunnyE startValue,
                                              SunnyE endValue) {
                SunnyE sunnyE = new SunnyE();
                sunnyE.rainY = startValue.rainY + 100 * (fraction *5) + 0.5f * 100 * (fraction * 5) * (fraction * 5);
                sunnyE.rainX = startValue.rainX + (-47f/174f) * sunnyE.rainY;
                return sunnyE;
            }
        });

        valueAnimator.setDuration(5000);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSunnyE = (SunnyE) animation.getAnimatedValue();
            }
        });
    }

    class SunnyE{
        public SunnyE() {
        }

        public SunnyE(float sunRotate, float circleScan) {
            this.rainX = sunRotate;
            this.rainY = circleScan;
        }
        public float rainX;
        public float rainY;
    }
    /**
     * 创建一片雨
     * @param width
     * @param height
     * @param paint
     * @param bitmap
     * @return
     */
    public static RainFlack create(int width, int height, Paint paint, Bitmap bitmap) {
        if (width <= 0 || height <= 0) {
            return null;
        }
        return new RainFlack(width, height, paint, bitmap);
    }

    public void draw(Canvas canvas) {
        mPaint.setAlpha(mAlpha);
        changePosition();
        canvas.drawBitmap(bitmap, matrix, mPaint);
        if (valueAnimator != null && !valueAnimator.isStarted()) {
            valueAnimator.start();
        }
    }

    /**
     * 改变雨位置
     */
    private void changePosition() {
        matrix.reset();
        matrix.postTranslate(mSunnyE.rainX, mSunnyE.rainY);
    }
}