package view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.custemview.R;

/**
 * Created by mavin on 2016/6/15.
 */
public class TestView extends ImageView {

    private Bitmap bitmap;
    private Paint paint;
    private PorterDuffXfermode porterDuffXfermode;
    private int totleWidth;
    private int totleHeight;

    private int width;
    private int height;
    private Paint textPaint;
    private Paint bitmapPaint;

    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        textPaint = new Paint();
        bitmapPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(40);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.shadow);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.D);
//        Bitmap.createBitmap()
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = 400;
        setMeasuredDimension(width, heightMeasureSpec);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText("啊我欸过哈我欸给", 0, 100, textPaint);
        canvas.save();
        canvas.restore();
        paint.setXfermode(porterDuffXfermode);
        canvas.drawBitmap(bitmap, 0, 80, paint);

    }
}
