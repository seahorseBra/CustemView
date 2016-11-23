package view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.custemview.R;

/**
 * Created by zchao on 2016/11/4.
 */

public class RecoderView extends View {
    private Path mPath;
    private Paint mPaint;
    private Bitmap voiceBmp;
    private int bmpWidth = 0,bmpHeight = 0;
    private int index = 0;
    private boolean start = false;
    public RecoderView(Context context) {
        super(context);
    }

    public RecoderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        voiceBmp = BitmapFactory.decodeResource(getResources(), R.drawable.voice);
        bmpWidth = voiceBmp.getWidth();
        bmpHeight = voiceBmp.getHeight();
        initePath();
    }

    /**
     * 初始化path
     */
    private void initePath() {
        if (mPath == null) {
            mPath = new Path();
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            index +=5;
            postInvalidate();
            startAnim();
        }
    };

    /**
     *
     */
    public void start() {
        start = true;
        startAnim();
    }

    private void startAnim(){
        if (start) {
            new Handler().postDelayed(runnable, 30);
        }
    }

    public void stop(){
        start = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (index >= bmpWidth) {
            index = 0;
        }
        int  count = (getWidth()/bmpWidth) + 1;
        int top = (getHeight() - bmpHeight)/2;
        for (int i = 0; i < count; i++) {
            canvas.drawBitmap(voiceBmp, index + bmpWidth * i ,top,mPaint);
        }
        canvas.drawBitmap(voiceBmp, index - bmpWidth ,top,mPaint);
    }
}
