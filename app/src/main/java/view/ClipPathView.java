package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zchao on 2017/4/17.
 * desc:
 * version:
 */

public class ClipPathView extends View{

    private Path path;
    private GradientDrawable gradientDrawable;
    private Paint paint;
    private Path path1;
    private Paint paint1;

    public ClipPathView(Context context) {
        this(context, null);
    }

    public ClipPathView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(Color.TRANSPARENT);
        path = new Path();
        path.moveTo(0, 90);
        path.lineTo(150, 0);
        path.lineTo(300, 40);
        path.lineTo(400, 0);
        path.lineTo(400, 400);
        path.lineTo(0,400);
        path.close();

        path1 = new Path();
        path1.moveTo(10, 100);
        path1.lineTo(390, 100);
        path1.lineTo(390, 200);
        path1.lineTo(10, 200);
        path1.close();

        gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{Color.RED, Color.TRANSPARENT});
        gradientDrawable.setBounds(0,0,400,400);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setPathEffect(new CornerPathEffect(50));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setColor(Color.BLACK);
        paint1.setTextSize(40);
        invalidateDrawable(gradientDrawable);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        gradientDrawable.draw(canvas);
        canvas.drawPath(path, paint);
        canvas.restore();

        canvas.clipPath(path1);
        canvas.drawColor(Color.YELLOW);
        canvas.drawText("123", 20, 150, paint1);
    }
}
