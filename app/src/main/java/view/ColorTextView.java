package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by mavin on 2016/8/26.
 */
public class ColorTextView extends TextView{

    private int mViewWidth;
    private Paint mPaint;
    private LinearGradient mLinear;
    private Matrix matrix;
    private int mTranslate;

    public ColorTextView(Context context) {
        this(context, null);
    }

    public ColorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            if (mViewWidth != 0) {
                mPaint = getPaint();
                mLinear = new LinearGradient(0, 0, mViewWidth, 0, new int[]{0x00000000, 0xffffffff, 0x00000000}, null, Shader.TileMode.CLAMP);
                mPaint.setShader(mLinear);
                matrix = new Matrix();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (matrix != null) {
            mTranslate += (mViewWidth/5);
            if (mTranslate > 2* mViewWidth){
                mTranslate = -mViewWidth;
            }
            matrix.setTranslate(mTranslate, 0);
            mLinear.setLocalMatrix(matrix);
            postInvalidateDelayed(100);
        }

    }
}
