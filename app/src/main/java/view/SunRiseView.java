package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import Utils.Utils;

/**
 * Created by zchao on 2016/6/12.
 */
public class SunRiseView extends View {
    private Paint mTextPaint;
    private Paint mImagePaint;
    private int mTextSize = Utils.dp2Px(15);
    private int sunColor = 0xffcf4d;
    private int textColor = Color.WHITE;
    private int VIEW_MINHEIGHT = Utils.dp2Px(200);
    private int height;
    private long sunRiseTime;
    private long sunSetTime;
    public SunRiseView(Context context) {
        this(context, null);
    }

    public SunRiseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SunRiseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initepaint();
    }

    private void initepaint() {
        mTextPaint =  new TextPaint();
        mImagePaint = new Paint();
        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(mTextSize);
    }

//    private void setSun

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        int size = MeasureSpec.getSize(heightMeasureSpec);
        height = Math.max(size, VIEW_MINHEIGHT);
        setMeasuredDimension(widthMeasureSpec, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }


}
