package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.inputmethodservice.Keyboard;
import android.text.TextPaint;
import android.view.KeyEvent;
import android.view.View;

import utils.Utils;

/**
 * Created by mavin on 2017/3/5.
 */
public class PopViewForKeyboard extends View {
    private Keyboard.Key key = null;
    private int[] value = new int[4];
    private TextPaint mTextPaint;
    private int m14dp;
    private OnKeyBack keyBackLisenter = null;

    public PopViewForKeyboard(Context context, OnKeyBack keyBackLisenter) {
        super(context, null);
        this.keyBackLisenter = keyBackLisenter;
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        m14dp = Utils.dp2Px(getResources(), 14);
        mTextPaint.setTextSize(m14dp);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        onEventHandle(keyCode);
//        return false;
//    }

    public void onEventHandle(int event) {
        int primaryCode = 0;
        switch (event) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                primaryCode = value[1];
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                primaryCode = value[0];
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                primaryCode = value[2];
                break;
            case KeyEvent.KEYCODE_ENTER:
                primaryCode = value[3];
                break;
        }
        if (keyBackLisenter != null) {
            keyBackLisenter.backKey(primaryCode);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(Utils.dp2Px(getResources(), 50), Utils.dp2Px(getResources(), 60));
    }

    public void setKey(Keyboard.Key key) {
        this.key = key;
        if (key.codes.length == 4) {
            value = key.codes;
        } else if (key.codes.length == 3) {
            value[0] = key.codes[0];
            value[1] = 0;
            value[2] = key.codes[1];
            value[3] = key.codes[2];
        }

        postInvalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0xff99ffff);
        canvas.drawText(String.valueOf((char) value[0]), 0, getHeight() / 2, mTextPaint);
        canvas.drawText(String.valueOf((char) value[1]), getWidth() / 2, m14dp, mTextPaint);
        canvas.drawText(String.valueOf((char) value[2]), getWidth() - m14dp, getHeight() / 2, mTextPaint);
        canvas.drawText(String.valueOf((char) value[3]), getWidth() / 2, getHeight() / 2, mTextPaint);
    }

    interface OnKeyBack {
        void backKey(int keyCode);
    }

}
