package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.administrator.custemview.R;

import java.util.List;

import utils.Utils;


/**
 * Created by zchao on 2017/3/2.
 * desc:
 * version:
 */

public class CusBoardView extends View {
    public static final int KEYCODE_CLEAR = -7;
    private Keyboard mKeyboard = null;
    private List<Keyboard.Key> mKeys;
    private TextPaint mTextPaint;
    private Paint mLinePaint;
    private Context mContext;
    private int mCurrentKey = 0;
    private OnKeyboardActionListener listener = null;
    private PopupWindow mPopWindow;
    private PopViewForKeyboard mPopView;

    public CusBoardView(Context context) {
        this(context, null);
    }

    public CusBoardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CusBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mKeyboard = new Keyboard(context, R.xml.keyboard);

        if (mKeys != null && mKeys.size() > 3) {
            mCurrentKey = 3;
        }

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(0xff000000);
        mTextPaint.setTextSize(Utils.dp2Px(getResources(),15));

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.RED);
        mLinePaint.setStrokeWidth(3);
        mLinePaint.setStyle(Paint.Style.STROKE);
        initData(context);
    }

    private void initData(Context context) {
        mKeys = mKeyboard.getKeys();
        mPopView = new PopViewForKeyboard(context, new PopViewForKeyboard.OnKeyBack() {
            @Override
            public void backKey(int keyCode) {
                if (mPopWindow.isShowing()) {
                    mPopWindow.dismiss();
                    if (listener != null) {
                        listener.onKey(keyCode);
                    }
                    Toast.makeText(mContext, Character.toString((char)keyCode), Toast.LENGTH_SHORT).show();
                }
            }
        });
        mPopWindow = new PopupWindow(mPopView);
        mPopWindow.setAnimationStyle(R.style.popup_window_anim_key);
        mPopWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);//必须设置宽和高
        mPopWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
//        mPopWindow.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0000000000);
        mPopWindow.setBackgroundDrawable(dw);
    }

    public void setKeyBoard(Keyboard keyBoard){
        mKeyboard = keyBoard;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeW = MeasureSpec.getSize(widthMeasureSpec);
        int sizeY = MeasureSpec.getSize(heightMeasureSpec);
        int modeW = MeasureSpec.getMode(widthMeasureSpec);
        int modeY = MeasureSpec.getMode(heightMeasureSpec);
        int height = 0;
        if (mKeys != null && mKeys.size() > 0) {
            height = mKeys.get(0).height;
            height += Utils.dp2Px(getResources(),10);
        }
        setMeasuredDimension(sizeW, Math.max(sizeY, height * 4));
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        onEventHandle(keyCode);
//        return false;
//    }

    public void onEventHandle(int event){
        if (!mPopWindow.isShowing()) {
            handleByView(event);
        } else {
            hanleByPopWindow(event);
        }
    }

    private void hanleByPopWindow(int event) {
        if (mPopView != null) {
            mPopView.onEventHandle(event);
        }
    }


    /**
     * View自身位置的变化
     * @param event
     */
    private void handleByView(int event) {
        switch (event) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (mCurrentKey < 8) {
                    mCurrentKey += 3;
                } else if (mCurrentKey == 8) {
                    mCurrentKey += 2;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (mCurrentKey > 2 && mCurrentKey > 8) {
                    mCurrentKey -= 2;
                } else if (mCurrentKey >2) {
                    mCurrentKey -= 3;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if ((mCurrentKey%3) != 0) {
                    mCurrentKey -= 1;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (mCurrentKey%3 != 2 && mCurrentKey != 10) {
                    mCurrentKey += 1;
                }
                break;
            case KeyEvent.KEYCODE_ENTER:
                openPopWindow(mKeys.get(mCurrentKey));
                return;
        }
        postInvalidate();
    }

    /**
     * 展开popupwindow
     * @param key
     */
    private void openPopWindow(Keyboard.Key key) {
        if (TextUtils.isEmpty(key.popupCharacters)) {
            if (listener != null) {
                listener.onKey(key.codes[0]);
            }
        } else {
            mPopView.setKey(key);
            mPopWindow.showAsDropDown(this, key.x, key.y - getHeight());
        }
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mKeys.size(); i++) {
            if (mCurrentKey == i) {
                mLinePaint.setColor(Color.GREEN);
            } else {
                mLinePaint.setColor(Color.RED);
            }
            Keyboard.Key key = mKeys.get(i);
            if (!TextUtils.isEmpty(key.label)) {
                canvas.drawText(key.label.toString(), (key.x + (key.width - mTextPaint.measureText(key.label.toString())) / 2), (key.y + key.height/2), mTextPaint);
            }
            canvas.drawRect(new RectF(key.x, key.y, key.x + key.width, key.y + key.height), mLinePaint);
        }
    }

    public interface OnKeyboardActionListener {

        void onKey(int primaryCode);

    }

    public void setOnKeyboardActionListener(OnKeyboardActionListener listener) {
        this.listener = listener;
    }
}
