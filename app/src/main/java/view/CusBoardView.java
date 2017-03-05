package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.inputmethodservice.Keyboard;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.custemview.R;

import java.util.List;

import utils.Utils;

/**
 * Created by zchao on 2017/3/2.
 * desc:
 * version:
 */

public class CusBoardView extends View {
    private Keyboard mKeyboard = null;
    private List<Keyboard.Key> mKeys;
    private TextPaint mTextPaint;
    private Paint mLinePaint;

    public CusBoardView(Context context) {
        this(context, null);
    }

    public CusBoardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CusBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mKeyboard = new Keyboard(context, R.xml.keyboard);
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(0xff000000);
        mTextPaint.setTextSize(Utils.dp2Px(15));

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.RED);
        mLinePaint.setStrokeWidth(3);
        mLinePaint.setStyle(Paint.Style.STROKE);
        initData();
    }

    private void initData() {
        mKeys = mKeyboard.getKeys();
    }

    public void setKeyBoard(Keyboard keyBoard){
        mKeyboard = keyBoard;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeW = MeasureSpec.getSize(widthMeasureSpec);
        int sizeY = MeasureSpec.getSize(heightMeasureSpec);
        int modeW = MeasureSpec.getMode(widthMeasureSpec);
        int modeY = MeasureSpec.getMode(heightMeasureSpec);
        int height = 0;
        if (mKeys != null && mKeys.size() > 0) {
            height = mKeys.get(0).height;
            height += Utils.dp2Px(10);
        }
        setMeasuredDimension(sizeW, Math.min(sizeY, height * 4));
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mKeys.size(); i++) {
            Keyboard.Key key = mKeys.get(i);
            if (!TextUtils.isEmpty(key.label)) {
                canvas.drawText(key.label.toString(), (key.x + (key.width - mTextPaint.measureText(key.label.toString())) / 2), (key.y + key.height/2), mTextPaint);
            }
            canvas.drawRect(new RectF(key.x, key.y, key.x + key.width, key.y + key.height), mLinePaint);
        }
    }


}
