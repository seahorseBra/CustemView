package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.administrator.custemview.R;

import utils.Utils;


/**
 * Created by Administrator on 2015/3/26.
 */
public class RoundBgView extends ImageView {
    private Context context;
    private float round = 0;                //圆角大小
    private int lineColor = 0x1e666666;     //边框线颜色
    private Paint mPaint, mLinePaint;
    private Bitmap bitmap;

    public RoundBgView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initeStyled(attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        mLinePaint.setColor(lineColor);
        mLinePaint.setStrokeWidth(Utils.dp2Px(1));
        mLinePaint.setStyle(Paint.Style.STROKE);
    }

    private void initeStyled(AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundBgView);
        round = typedArray.getDimension(R.styleable.RoundBgView_BorderRadius, Utils.dp2Px(5));
        lineColor = typedArray.getColor(R.styleable.RoundBgView_LineColor, 0x1e666666);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        super.onDraw(canvas);
        createBitmap(getWidth());
        if (bitmap != null && !bitmap.isRecycled()) {
            canvas.drawBitmap(bitmap, 0,0,mPaint);
        }
        canvas.restore();

        //绘制边框线
        RectF rectF = new RectF(0,0,getWidth(),getHeight());
        canvas.drawRoundRect(rectF,round, round, mLinePaint);
    }

    //生成一个跟view大小一致的黑色带圆角的图,使用此图来做蒙层
    private void createBitmap(int w) {
        if (bitmap == null || bitmap.isRecycled()) {
            bitmap = Bitmap.createBitmap(w,w, Bitmap.Config.ARGB_8888);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.BLACK);
            Canvas canvas = new Canvas(bitmap);
            RectF rectF = new RectF(0,0,w,w);
            canvas.drawRoundRect(rectF,round, round,paint);
        }
    }

}
