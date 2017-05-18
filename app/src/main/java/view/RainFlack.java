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
public class RainFlack implements WeatherFlackInterface {
    // 雪花的大小
    private static final float FLAKE_SIZE_LOWER = 15f;
    private final Point mPosition;
    private final Paint mPaint;                 // 画笔
    private final double mAngle = Math.PI / 4;  //雪花随机方向
    private final Bitmap bitmap;                //雪花图片
    private int mValue = 2;                     //雪花Y轴上移动速度
    private int mXValue = 1;                    //雪花X轴上移动速度
    private final int mAlpha;                   //雪花透明度
    private int mViewWidth ,mViewHeight;        //View的总宽高
    private int round = 1;
    private Matrix matrix;

    private float[] mValue1 = new float[9];

    private float scanValue = 0.5f;

    private RainFlack(int viewWidth, int viewHeight, Paint paint, Bitmap bitmap) {
        this.bitmap = bitmap;
        int x = (int) RandomGenerator.getRandom(0, viewWidth);
        int y = (int) RandomGenerator.getRandom(0, viewHeight);
        mPosition = new Point(x, y);
        mPaint = paint;
        mAlpha = (int) RandomGenerator.getRandom(25, 125);
        double angle = RandomGenerator.getRandom(-(float) Math.PI/4, (float) Math.PI / 4);
        mXValue = (int) (Math.tan(angle)*2);
        mValue = (int) RandomGenerator.getRandom(2,4);

        round = (int) RandomGenerator.getRandom(-1, 1);
        mViewWidth = viewWidth;
        mViewHeight = viewHeight;

        matrix = new Matrix();
        scanValue = RandomGenerator.getRandom(0.2f, 1f);
        resetMatrix();
    }

    /**
     * 创建一片雪花
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
    }

    /**
     * 改变雪花位置
     */
    private void changePosition() {
        matrix.postTranslate(mXValue,mValue);
        round++;
        matrix.getValues(mValue1);
        if (mValue1[2] >= mViewWidth ||mValue1[2] <= (-bitmap.getWidth()) || mValue1[5] >= mViewHeight ) {
            resetFlack();
            matrix.getValues(mValue1);
        }
    }

    /**
     * 重置雪花位置到开始位置
     */
    private void resetFlack() {
        mPosition.y = 0;
        mPosition.x = RandomGenerator.getRandom(mViewWidth);
        double angle = RandomGenerator.getRandom(-(float) Math.PI/4, (float) Math.PI / 4);
        mXValue = (int) (Math.tan(angle)*2);
        resetMatrix();
    }

    private void resetMatrix(){
        matrix.reset();
        matrix.setScale(scanValue, scanValue);
        matrix.postTranslate(mPosition.x, mPosition.y);
    }

}