package view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.custemview.R;


/**
 * Created by mavin on 2016/6/15.
 */
public class TestView extends View {

    private Bitmap bitmap;
    private Paint paint;
    private PorterDuffXfermode porterDuffXfermode;
    private int totleWidth;
    private int totleHeight;

    private int width;
    private int height;
    private Paint textPaint;
    private Paint bitmapPaint;
    private Bitmap bitmap1;

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

        final Drawable drawable = getResources().getDrawable(R.drawable.shadow);
        bitmap = Bitmap.createBitmap(400, 100, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0,0, 400, 100);
        drawable.draw(canvas);


        bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.index);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);

        paint.setShader(new RadialGradient(bitmap1.getWidth()/2, bitmap1.getHeight()/2, bitmap1.getHeight()/2, 0xff000000, 0x00000000, Shader.TileMode.CLAMP));
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

        canvas.drawBitmap(bitmap1, 0, 0, paint);
        canvas.drawCircle(bitmap1.getWidth()/2, bitmap1.getHeight()/2, bitmap1.getHeight()/2, paint);
//        paint.setXfermode(porterDuffXfermode);
//        canvas.drawBitmap(bitmap, 0, 0, paint);
//        paint.setXfermode(null);
    }
}
