package view;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.nineoldandroids.animation.TypeEvaluator;
import com.nineoldandroids.animation.ValueAnimator;

import utils.RandomGenerator;


/**
 * 雨的类, 移动, 移出屏幕会重新设置位置.
 */
public class RainFlack implements WeatherFlackInterface {
    private Paint mPaint;                 // 画笔
    private Bitmap bitmap;                //雨图片
    private ValueAnimator valueAnimator;
    private int mAlpha, mAlpha1;                   //雨透明度
    private int mViewWidth, mViewHeight;        //View的总宽高
    private Matrix matrix;
    private static final float mXYRate = -47f / 174f;          //这是按照雨滴图片的像素长宽比例算的，用此比例来计算X,Y方向上雨滴的速度，保证雨滴移动方向跟图片上雨滴方向一致
    private RainValue mRainValue;


    private RainFlack(int viewWidth, int viewHeight, Paint paint, final Bitmap bitmap) {
        this.bitmap = bitmap;
        mViewWidth = viewWidth;
        mViewHeight = viewHeight;

        int x = (int) RandomGenerator.getRandom(0, viewWidth);
        int y = -bitmap.getHeight();
        mRainValue = new RainValue(x, y);
        mPaint = paint;
        mAlpha1 = mAlpha = (int) RandomGenerator.getRandom(100, 255);
        matrix = new Matrix();
        iniValueAnimator();
    }

    /**
     * 初始化动画
     */
    private void iniValueAnimator() {
        valueAnimator = new ValueAnimator();
        valueAnimator.setObjectValues(mRainValue, new RainValue(0, mViewHeight));
        valueAnimator.setStartDelay(RandomGenerator.getRandom(6000));
        valueAnimator.setEvaluator(new TypeEvaluator<RainValue>() {
            @Override
            public RainValue evaluate(float fraction, RainValue startValue,
                                      RainValue endValue) {
                RainValue sunnyE = new RainValue();
                if (fraction <= 0.5f) {//动画重新开始，重置alpha;
                    mAlpha = mAlpha1;
                } else {
                    mAlpha -= 5;
                    if (mAlpha < 0) {
                        mAlpha = 0;
                    }
                }
                sunnyE.rainY = startValue.rainY + 100 * (fraction * 5) + 0.5f * 100 * (fraction * 5) * (fraction * 5);
                sunnyE.rainX = startValue.rainX + mXYRate * sunnyE.rainY;
                return sunnyE;
            }
        });

        valueAnimator.setDuration(5000);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRainValue = (RainValue) animation.getAnimatedValue();
            }
        });
    }

    class RainValue {
        public RainValue() {
        }

        public RainValue(float sunRotate, float circleScan) {
            this.rainX = sunRotate;
            this.rainY = circleScan;
        }

        public float rainX;
        public float rainY;
    }

    /**
     * 创建一片雨
     *
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
        changePosition();
        mPaint.setAlpha(mAlpha);
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
        matrix.postTranslate(mRainValue.rainX, mRainValue.rainY);
    }
}