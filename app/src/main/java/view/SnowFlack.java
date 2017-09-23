package view;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;

import utils.RandomGenerator;


/**
 * 雪花的类, 移动, 移出屏幕会重新设置位置.
 */
public class SnowFlack implements WeatherFlackInterface {
    private Point mPosition, mRotateCenter;
    private final Paint mPaint;                 // 画笔
    private final double mAngle = Math.PI / 4;  //雪花随机方向
    private final Bitmap bitmap;                //雪花图片
    private int mYValue = 2;                     //雪花Y轴上移动速度
    private int mXValue = 1;                    //雪花X轴上移动速度
    private int mAlpha = 255;                   //雪花透明度
    private int mAlpha1 = 255;                   //雪花透明度
    private int mViewWidth ,mViewHeight;        //View的总宽高
    private float mRotate = 0;                    //雪花旋转角度
    private float mRotateRate = 1f;                //雪花旋转速度
    private Matrix matrix;

    private float scanValue = 0.5f;             //雪花缩放度，以做出不同大小的雪花

    private SnowFlack(int viewWidth, int viewHeight, Paint paint, Bitmap bitmap) {
        this.bitmap = bitmap;
        int x = (int) RandomGenerator.getRandom(0, viewWidth);
        int y = (int) RandomGenerator.getRandom(0, viewHeight);
        mPosition = new Point(x, y);
        mRotateCenter = new Point(bitmap.getWidth()/2, bitmap.getHeight()/2);
        mPaint = paint;
        mAlpha1 = mAlpha = (int) RandomGenerator.getRandom(50, 255);
        double angle = RandomGenerator.getRandom(-(float) Math.PI/4, (float) Math.PI / 4);
        mXValue = (int) (Math.tan(angle)*2);
        mYValue = (int) RandomGenerator.getRandom(2,4);

        mRotateRate = RandomGenerator.getRandom(-2, 2);
        mViewWidth = viewWidth;
        mViewHeight = viewHeight;

        matrix = new Matrix();
        scanValue = RandomGenerator.getRandom(0.2f, 1f);
    }

    /**
     * 创建一片雪花
     * @param width
     * @param height
     * @param paint
     * @return
     */
    public static SnowFlack create(int width, int height, Paint paint, Bitmap bitmap) {
        if (width <= 0 || height <= 0) {
            return null;
        }
        return new SnowFlack(width, height, paint, bitmap);
    }

    public void draw(Canvas canvas) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        mPaint.setAlpha(mAlpha);
        changePosition();
        canvas.drawBitmap(bitmap, matrix, mPaint);
    }

    /**
     * 改变雪花位置
     */
    private void changePosition() {
        mPosition.x += mXValue;
        mPosition.y += mYValue;
        mRotate += mRotateRate;
        matrix.reset();
        matrix.setScale(scanValue, scanValue);
        matrix.preRotate(mRotate, mRotateCenter.x, mRotateCenter.y);
        matrix.postTranslate(mPosition.x, mPosition.y);
        //渐隐
        if (mPosition.y >= 300) {
            mAlpha--;
        }
        if (mPosition.x >= mViewWidth ||mPosition.x <= (-bitmap.getWidth()) || mPosition.y >= mViewHeight || mAlpha <= 0) {
            resetFlack();
        }
        if (mRotate >= 360) {
            mRotate = 0;
        }
    }

    /**
     * 重置雪花位置到开始位置
     */
    private void resetFlack() {
        mPosition.y = 0;
        mPosition.x = RandomGenerator.getRandom(mViewWidth);
        double angle = RandomGenerator.getRandom(-(float) Math.PI/4, (float) Math.PI / 4);
        mAlpha = mAlpha1;
        mXValue = (int) (Math.tan(angle)*2);
    }
}