package view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ScrollView;

/**
 * Created by zchao on 2016/6/15.
 */
public class ShadowScrollView extends ScrollView {
    private OnScrollTobottomListener onScrollTobottomListener;
    private Paint paint;
    private Bitmap bitmap;

    public ShadowScrollView(Context context) {
        this(context, null);
    }

    public ShadowScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void setOnScrollTobottomListener(OnScrollTobottomListener onScrollTobottomListener) {
        this.onScrollTobottomListener = onScrollTobottomListener;

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if(t + getHeight() >=  computeVerticalScrollRange()){
            //ScrollView滑动到底部了
            if (onScrollTobottomListener != null) {
                onScrollTobottomListener.onScrollToBottom(true);
            }
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (paint == null) {
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        }
        canvas.saveLayer(0, getScrollY(), getWidth(), getScrollY() + getHeight(), null, Canvas.ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);
        createMaskBitmap();
        if (bitmap != null && !bitmap.isRecycled()) {
            canvas.drawBitmap(bitmap, 0, getScrollY(), paint);
        }
        canvas.restore();


    }

    private void createMaskBitmap() {
        if (bitmap == null || bitmap.isRecycled()) {
            try {
                bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{Color.WHITE, Color.TRANSPARENT});
                gd.setBounds(0, 0, getWidth(), Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics())));
                gd.draw(canvas);
            } catch (Throwable e) {

            }
        }
    }

    public interface OnScrollTobottomListener {
        public void onScrollToBottom(boolean scrollToBottom);
    }
}
