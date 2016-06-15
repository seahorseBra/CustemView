package view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.example.administrator.custemview.R;

/**
 * Created by mavin on 2016/6/15.
 */
public class ShadowScrollView extends ScrollView {

    private Bitmap bitmap;
    private Paint paint;
    private PorterDuffXfermode porterDuffXfermode;
    private int totleWidth;
    private int totleHeight;

    public ShadowScrollView(Context context) {
        this(context, null);
    }

    public ShadowScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.shadow);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.ADD);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        totleWidth = w;
        totleHeight = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        totleWidth = MeasureSpec.getSize(widthMeasureSpec);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, totleWidth, 100, false);
        bitmap = scaledBitmap;
        paint.setXfermode(porterDuffXfermode);
        canvas.drawBitmap(scaledBitmap, 0, getScrollY(), paint);
    }
}
