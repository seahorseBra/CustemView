package view;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;

import utils.RandomGenerator;


/**
 * 霾的类, 移动, 移出屏幕会重新设置位置.
 */
public class MaiFlack implements WeatherFlackInterface {
    private final Paint mPaint;                 // 画笔
    private final Bitmap bitmap;                //霾图片
    private float mYValue = 2;                  //霾Y轴上移动速度
    private float mXValue = 1;                  //霾X轴上移动速度
    private final int mAlpha = 255;                   //霾透明度
    private int mViewWidth ,mViewHeight;        //View的总宽高
    private Matrix matrix;

    private float[] mValue1 = new float[9];

    private MaiFlack(int viewWidth, int viewHeight, Paint paint, Bitmap bitmap) {
        this.bitmap = bitmap;
        int x = (int) (viewWidth - bitmap.getWidth())/2;
        int y = (int) (viewHeight - bitmap.getHeight())/2;
        mPaint = paint;

        matrix = new Matrix();
        matrix.setTranslate(x,y);

        mViewWidth = viewWidth;
        mViewHeight = viewHeight;
    }

    /**
     * 创建一片霾
     * @param width
     * @param height
     * @param paint
     * @param bitmap
     * @return
     */
    public static MaiFlack create(int width, int height, Paint paint, Bitmap bitmap) {
        if (width <= 0 || height <= 0) {
            return null;
        }
        return new MaiFlack(width, height, paint, bitmap);
    }

    public void draw(Canvas canvas) {
        mPaint.setAlpha(mAlpha);
        canvas.drawBitmap(bitmap, matrix, mPaint);
    }

}