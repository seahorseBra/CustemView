package view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.custemview.R;

import utils.Utils;

/**
 * Created by zchao on 2016/6/12.
 */
public class SunRiseView extends View {
    private static final String TAG = "SunRiseView";
    private Paint mTextPaint;
    private Paint mImagePaint;
    private int mTextSize = Utils.dp2Px(15);
    private int sunColor = 0xffffcf4d;
    private int divisionColor = 0x1effffff;
    private int textColor = Color.WHITE;
    private static final int VIEW_MINHEIGHT = Utils.dp2Px(200);
    private int height = VIEW_MINHEIGHT;
    private String sunRiseTime;
    private String sunSetTime;
    private float currentSunAngl = 0;
    private float circleR = Utils.dp2Px(4);
    private Paint mLinePaint;
    private int width;
    private DashPathEffect effect;
    private Bitmap sunBitmap = null;
    private Bitmap cloudBitmap1 = null;
    private Bitmap cloudBitmap2 = null;
    private float sunLeft;
    private float sunTop;
    private int centerX;
    private int centerY;
    private int radius;
    private float angle = 130;
    public SunRiseView(Context context) {
        this(context, null);
    }

    public SunRiseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SunRiseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        width = getResources().getDisplayMetrics().widthPixels;
        initepaint();
        initeBitmap();
    }

    private void initeBitmap() {
        sunBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sun_icon);
        cloudBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.hill_imgx);
        cloudBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.cloud_img);
    }

    private void initepaint() {
        mTextPaint =  new TextPaint();
        mImagePaint = new Paint();
        mLinePaint = new Paint();

        resetPaint();
        effect = new DashPathEffect(new float[]{20, 10}, 1);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            startAnimation();
            refreshView();
        }
    };

    /**
     * 让太阳从开始的位置移动到当前应该到的位置；
     * 使用currentSunAngl不断自加，然后重绘视图，直到currentSunAngl到达当前时间对应的角度
     */
    public void startAnimation(){
        if (currentSunAngl < angle) {
            currentSunAngl++;
            postDelayed(runnable, 5);
        }
    }

    private void resetPaint() {
        mTextPaint.setAntiAlias(true);
        mLinePaint.setAntiAlias(true);
        mImagePaint.setAntiAlias(true);

        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(mTextSize);

        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(5);
        mLinePaint.setColor(Color.WHITE);
    }

//    private void setSun

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int size = MeasureSpec.getSize(heightMeasureSpec);
        height = Math.max(size, VIEW_MINHEIGHT);

        centerX = width /2;
        centerY = height - 78;
        radius = Math.min((width - 160) / 2, centerY - 40);

        refreshView();
        setMeasuredDimension(width, height);
    }

    private void refreshView() {
        sunLeft = (float) (centerX - radius * Math.cos(currentSunAngl * Math.PI/180) - sunBitmap.getWidth() / 2);
        sunTop = (float) (centerY - radius * Math.sin(currentSunAngl * Math.PI/180) - sunBitmap.getHeight() / 2);
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        resetPaint();
        String s = "离日落还有3小时45分";
        String s1 = "05:58";
        String s2 = "19:38";

        mLinePaint.setColor(divisionColor);
        mLinePaint.setStrokeWidth(3);
        canvas.drawLine(Utils.dp2Px(16), centerY, width - Utils.dp2Px(16), centerY, mLinePaint);

        canvas.drawBitmap(cloudBitmap1, Utils.dp2Px(16), centerY - cloudBitmap1.getHeight(), mImagePaint);
        canvas.drawBitmap(cloudBitmap2, centerX * 3 / 2, centerY / 3, mImagePaint);

        resetPaint();
        RectF rectF = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        mLinePaint.setPathEffect(effect);
        canvas.drawArc(rectF, 180, 180, false, mLinePaint);   //太阳未走过的圆弧

        mLinePaint.setColor(sunColor);
        canvas.drawArc(rectF, 180, currentSunAngl, false, mLinePaint);                          //太阳走过的圆弧

        canvas.drawBitmap(sunBitmap, sunLeft, sunTop, mImagePaint);                             //画太阳


        mLinePaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX - radius, centerY, circleR, mLinePaint);                      //圆弧两端小黄点
        canvas.drawCircle(centerX + radius, centerY, circleR, mLinePaint);

        canvas.drawText(s, centerX - mTextPaint.measureText(s)/2, centerY - radius / 2, mTextPaint);     //中间文字
        mTextPaint.setTextSize(Utils.dp2Px(10));
        canvas.drawText(s1, centerX - radius - mTextPaint.measureText(s1)/2, centerY + 40, mTextPaint); //日出时间
        canvas.drawText(s2, centerX + radius - mTextPaint.measureText(s2)/2, centerY + 40, mTextPaint); //日落时间
    }


}
