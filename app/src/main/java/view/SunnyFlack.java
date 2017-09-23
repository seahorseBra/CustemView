package view;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.animation.TypeEvaluator;
import com.nineoldandroids.animation.ValueAnimator;


/**
 * 晴天的类, 移动, 移出屏幕会重新设置位置.
 */
public class SunnyFlack implements WeatherFlackInterface {
    private Point mPosition;
    private Paint mPaint;                 // 画笔
    private Bitmap bitmap, bitmap1;                //晴天图片
    private int mViewWidth ,mViewHeight;        //View的总宽高
    private Matrix matrix,matrix1;

    private SunnyE mSunnyE = new SunnyE(0, 1f, 300);

    private ValueAnimator valueAnimator;

    private SunnyFlack(int viewWidth, int viewHeight, Paint paint, Bitmap bitmap, Bitmap bitmap1) {
        this.bitmap = bitmap;
        this.bitmap1 = bitmap1;
        mPosition = new Point(-400, -400);
        mPaint = paint;

        mViewWidth = viewWidth;
        mViewHeight = viewHeight;

        matrix = new Matrix();
        matrix1 = new Matrix();
        matrix.setTranslate(mPosition.x, mPosition.y);

        iniAnimator();
    }

    /**
     * 初始化动画
     */
    private void iniAnimator() {
        valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setObjectValues(new SunnyE(0, 1.5f, 250), new SunnyE(-20, 1f,350));
        valueAnimator.setEvaluator(new TypeEvaluator<SunnyE>(){
            @Override
            public SunnyE evaluate(float fraction, SunnyE startValue,
                                   SunnyE endValue) {
                SunnyE sunnyE = new SunnyE();
                sunnyE.sunRotate = (endValue.sunRotate - startValue.sunRotate) * fraction + startValue.sunRotate;
                sunnyE.circleScan = (endValue.circleScan - startValue.circleScan) * fraction + startValue.circleScan;
                sunnyE.circleTranslate = (endValue.circleTranslate - startValue.circleTranslate) * fraction + startValue.circleTranslate;
                return sunnyE;
            }
        });

        valueAnimator.setDuration(20000);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
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

        public SunnyE(float sunRotate, float circleScan, float circleTranslate) {
            this.sunRotate = sunRotate;
            this.circleScan = circleScan;
            this.circleTranslate = circleTranslate;
        }

        public float sunRotate;
        public float circleScan;
        public float circleTranslate;
    }


    /**
     * 创建一片晴天
     * @param width
     * @param height
     * @param paint
     * @param bitmap
     * @return
     */
    public static SunnyFlack create(int width, int height, Paint paint, Bitmap bitmap, Bitmap bitmap1) {
        if (width <= 0 || height <= 0) {
            return null;
        }
        return new SunnyFlack(width, height, paint, bitmap, bitmap1);
    }

    @Override
    public void draw(Canvas canvas) {
        changeMatrix();
        canvas.drawBitmap(bitmap, matrix, mPaint);

        //三个圆圈圈
        changeCircleMatrix(mSunnyE.circleTranslate, mSunnyE.circleTranslate + 200, mSunnyE.circleScan - 0.5f, mSunnyE.sunRotate);
        canvas.drawBitmap(bitmap1, matrix1, mPaint);
        changeCircleMatrix(mSunnyE.circleTranslate + 30, mSunnyE.circleTranslate + 260, mSunnyE.circleScan, mSunnyE.sunRotate);
        canvas.drawBitmap(bitmap1, matrix1, mPaint);
        changeCircleMatrix(mSunnyE.circleTranslate + 60, mSunnyE.circleTranslate + 310, mSunnyE.circleScan - 0.3f, mSunnyE.sunRotate);
        canvas.drawBitmap(bitmap1, matrix1, mPaint);

        if (valueAnimator != null && !valueAnimator.isStarted()) {
            valueAnimator.start();
        }
    }

    /**
     * 根据参数设置圆圈的matrix
     * @param offsetX
     * @param offsetY
     * @param scan
     * @param sunRotate
     */
    private void changeCircleMatrix(float offsetX, float offsetY, float scan, float sunRotate) {
        matrix1.reset();
        matrix1.preTranslate(offsetX, offsetY);
        matrix1.postScale(scan, scan, offsetX, offsetY);
        matrix1.postRotate(sunRotate);
    }

    /**
     * 改变晴天位置
     */
    private void changeMatrix() {
        matrix.reset();
        matrix.preTranslate(-400, -400);
        matrix.postRotate(mSunnyE.sunRotate);
    }

}