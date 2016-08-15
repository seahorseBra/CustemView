package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.administrator.custemview.R;

/**
 * Created by zchao on 2016/8/9.
 */
public class ShadowImageView extends ImageView {
    private Drawable shadowDrawable = null;
    private Paint mShadowPaint = null;
    private Context context;
    private float shadowDx, shadowDy; //阴影的偏离方向
    private int shadowColor;           //阴影颜色
    private Paint paint;
    private Bitmap bitmap;

    public ShadowImageView(Context context) {
        this(context, null);
    }

    public ShadowImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initeAttr(attrs);
        initShadow();
    }

    private void initeAttr(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShadowImageView);
        shadowDx = typedArray.getFloat(R.styleable.ShadowImageView_shadowDx, 0);
        shadowDy = typedArray.getFloat(R.styleable.ShadowImageView_shadowDy, 0);
        shadowColor = typedArray.getColor(R.styleable.ShadowImageView_shadowColor, Color.BLACK);
    }




    private void initShadow() {
        mShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int sl = canvas.saveLayer(0,0,getWidth(), getHeight(),null, Canvas.ALL_SAVE_FLAG);
        shadowDrawable = getDrawable();

        if (shadowDrawable != null) {
            canvas.drawColor(shadowColor);
            mShadowPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            createShadow(shadowDrawable);
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, 0, 0, mShadowPaint);
            }
        }
        canvas.restoreToCount(sl);
        super.onDraw(canvas);
    }

    private void createShadow(Drawable shadowDrawable) {
        Bitmap b = ((BitmapDrawable)shadowDrawable).getBitmap();
        if (bitmap == null || bitmap.isRecycled()) {
            bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas1 = new Canvas(bitmap);
            canvas1.drawBitmap(b, (getWidth()-b.getWidth())/2+shadowDx, (getHeight()-b.getHeight())/2+shadowDy,new Paint());
        }
    }
}
