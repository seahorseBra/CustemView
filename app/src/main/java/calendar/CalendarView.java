package calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import utils.Utils;

/**
 * Created by zchao on 2016/10/20.
 */

public class CalendarView extends ImageView {
    private String dataString;
    private Paint mTextPaint;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.RED);
        mTextPaint.setTextSize(Utils.dp2Px(14));
    }


    public void setDate(String dataString) {
        this.dataString = dataString;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        if (!TextUtils.isEmpty(dataString)) {
            canvas.drawText(dataString, (width/2 - mTextPaint.measureText(dataString)/2), (height/2 - Utils.dp2Px(14)/2), mTextPaint);
        }
    }
}
