package view;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;


/**
 * 云的类, 移动, 移出屏幕会重新设置位置.
 */
public class CloudFlack implements WeatherFlackInterface {
    private final Point mPosition;
    private final Paint mPaint;                 // 画笔
    private final Bitmap bitmap;                //云图片
    private float mXValue = -1;                 //云移动速度
    private int mViewWidth ,mViewHeight;        //View的总宽高
    private Matrix matrix, matrix2;             //使用两个matrix来绘制图片以达到循环播放的效果

    private float[] mValue1 = new float[9];

    private CloudFlack(int viewWidth, int viewHeight, Paint paint, Bitmap bitmap) {
        this.bitmap = bitmap;
        int x = 0;
        int y = (viewHeight - bitmap.getHeight())/2;
        mPosition = new Point(x, y);
        mPaint = paint;

        mViewWidth = viewWidth;
        mViewHeight = viewHeight;

        matrix = new Matrix();
        matrix.setTranslate(0, mPosition.y);
        matrix2 = new Matrix();
        matrix2.setTranslate(bitmap.getWidth(), mPosition.y);
    }

    /**
     * 创建一片云
     * @param width
     * @param height
     * @param paint
     * @param bitmap
     * @return
     */
    public static CloudFlack create(int width, int height, Paint paint, Bitmap bitmap) {
        if (width <= 0 || height <= 0) {
            return null;
        }
        return new CloudFlack(width, height, paint, bitmap);
    }

    public void draw(Canvas canvas) {
        changePosition();
        canvas.drawBitmap(bitmap, matrix, mPaint);
        changeBitmapMatrix(false);  //matrix右平移bitmap的宽度,以绘制下一个衔接的图片
        canvas.drawBitmap(bitmap, matrix, mPaint);
        changeBitmapMatrix(true);   //matrix向右平移bitmap的宽度后需要恢复
    }

    /**
     * 移动matrix为bitmap宽度
     * @param reset
     */
    private void changeBitmapMatrix(boolean reset) {
        if (reset) {
            matrix.postTranslate(-bitmap.getWidth(), 0);
        } else {
            matrix.postTranslate(bitmap.getWidth(), 0);
        }
    }

    /**
     * 改变云位置
     */
    private void changePosition() {
        matrix.postTranslate(mXValue, 0);

        //判断前一个图片是否已移动出了屏幕边沿，如果出去则将matrix中减掉一个bitmap的宽度，防止matrix中translateX无限增长
        matrix.getValues(mValue1);
        if (Math.abs(mValue1[2]) >= bitmap.getWidth()) {
            changeBitmapMatrix(false);
            matrix.getValues(mValue1);
        }

    }
}