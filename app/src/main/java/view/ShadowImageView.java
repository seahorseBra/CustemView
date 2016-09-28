package view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.AttributeSet;
import android.view.View;
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
        shadowDy = typedArray.getFloat(R.styleable.ShadowImage_shadowDY, 0);
        shadowColor = typedArray.getColor(R.styleable.ShadowImageView_shadowColor, Color.BLACK);
    }




    private void initShadow() {
        mShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        BlurMaskFilter maskFilter = new BlurMaskFilter(5, BlurMaskFilter.Blur.SOLID);
        mShadowPaint.setMaskFilter(maskFilter);
//        mShadowPaint.setColor();
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        disableHardwareRendering(this);
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

        canvas.drawCircle(10, 10, 5,mShadowPaint);
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private Bitmap blur(Bitmap bkg, float radius) {
        Bitmap overlay = Bitmap.createBitmap(bkg.getWidth(), bkg.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.drawBitmap(bkg, 0, 0, null);
        RenderScript rs = RenderScript.create(context);
        Allocation overlayAlloc = Allocation.createFromBitmap(rs, overlay);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, overlayAlloc.getElement());
        blur.setInput(overlayAlloc);
        blur.setRadius(radius);
        blur.forEach(overlayAlloc);
        overlayAlloc.copyTo(overlay);
        return null;
//        return new BitmapDrawable(getResources(), overlay);
    }
    public static void disableHardwareRendering(View v) {
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }
}
