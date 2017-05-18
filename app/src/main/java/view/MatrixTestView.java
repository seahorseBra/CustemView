package view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.custemview.R;

/**
 * Created by zchao on 2017/5/18.
 * desc:
 * version:
 */

public class MatrixTestView extends View {

    private Bitmap bitmap;
    private Matrix matrix, matrix1;
    private Paint paint;
    private int a = 0;
    private int x = 0, y = 0;
    private float scan = 0f;

    private float[] value = new float[9];

    public MatrixTestView(Context context) {
        this(context, null);
    }

    public MatrixTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.snow);
        matrix = new Matrix();
        matrix1 = new Matrix();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        matrix1.preScale(0.5f, 0.5f);
        matrix.setScale(0.6f,0.6f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        changeMatrix();
        canvas.drawBitmap(bitmap, matrix, paint);
        canvas.drawBitmap(bitmap, matrix1, paint);
//        invalidate();
    }

    private void changeMatrix() {
//        matrix.preRotate(1, bitmap.getWidth()/2, bitmap.getHeight()/2);
        matrix.postTranslate(1, 1);

        matrix1.getValues(value);
        if (value[2] >= getWidth() || value[5] >= getHeight()) {
            matrix.reset();
            value[2] = 0;
            value[5] = 0;
        }

//        matrix1.preRotate(2, bitmap.getWidth()/2, bitmap.getHeight()/2);
        matrix1.postTranslate(1,2);

    }
}
