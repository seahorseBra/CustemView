package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zchao on 2016/5/16.
 */
public class ClockView extends View {


    private int mwidth;
    private int mheight;
    private Paint mText;
    private Paint mDegree;
    private int radius;

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDegree = new Paint();
        mText = new Paint();
        mDegree.setColor(Color.BLACK);
        mDegree.setStrokeWidth(4);
        mDegree.setStyle(Paint.Style.STROKE);
        mDegree.setAntiAlias(true);

        mText.setStrokeWidth(1);
        mText.setTextSize(20);
        mText.setAntiAlias(true);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mwidth = MeasureSpec.getSize(widthMeasureSpec);
        mheight = MeasureSpec.getSize(heightMeasureSpec);
        int width = Math.min(mwidth - getPaddingRight() - getPaddingLeft(), mheight - getPaddingTop() - getPaddingBottom());
        radius = width / 2;
        setMeasuredDimension(width, mheight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mwidth/2, mheight/2, radius, mDegree);

        canvas.save();
        for (int i = 0; i < 12; i++) {
            if (i == 0 || i == 3 || i == 6 || i == 9) {
                mDegree.setStrokeWidth(4);
                String s = String.valueOf(i);
                canvas.drawLine(mwidth/2, mheight/2 - radius, mwidth/2, mheight/2-radius+60, mDegree);
                canvas.drawText(s, mwidth/2-mText.measureText(s)/2, mheight/2-radius+80, mText);
            } else {
                mDegree.setStrokeWidth(1);
                canvas.drawLine(mwidth/2, mheight/2 - radius, mwidth/2, mheight/2-radius+30, mDegree);
            }
            canvas.rotate(30, mwidth/2, mheight/2);
        }
        canvas.restore();
        canvas.save();

        canvas.rotate(45, mwidth/2, mheight/2);
        mDegree.setStrokeWidth(6);
        canvas.drawLine(mwidth/2, mheight/2+10, mwidth/2, mheight/2-60, mDegree);
        canvas.restore();
        canvas.save();

        canvas.rotate(135, mwidth/2, mheight/2);
        mDegree.setStrokeWidth(3);
        canvas.drawLine(mwidth/2, mheight/2+15, mwidth/2, mheight/2-80, mDegree);
//        canvas.restore();

        mDegree.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mwidth/2, mheight/2, 8, mDegree);

    }
}
