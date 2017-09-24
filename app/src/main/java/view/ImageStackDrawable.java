package view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.ViewUtils;


import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.administrator.custemview.R;

import java.util.ArrayList;
import java.util.HashMap;

import utils.GlideApp;
import utils.Utils;

/**
 * Created by zchao on 2017/9/22.
 * desc: Drawable的自定义过程。
 * version:
 */

public class ImageStackDrawable extends Drawable {
    private final Context mContext;
    private final Paint mPaint;
    ArrayList<String> list = new ArrayList<>();
    HashMap<String, Bitmap> mIconMap = new HashMap<>();
    private int mShowCompleteIconSize = 4;
    private int mIconSize = (int) Utils.dp2Px(50);
    private int mInterval = (int) Utils.dp2Px(15);
    private RectF dstRect = new RectF();
    private Bitmap defaultIcon = null;

    public ImageStackDrawable(Context context) {
        mContext = context;
        defaultIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_user_head_img);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void setData(ArrayList<String> headIconUrls) {
        if (headIconUrls != null) {
            list.clear();
            list.addAll(headIconUrls);
        }
        loadIcon();
    }

    /**
     * 加载图片，加载完后放到map里边然后刷新界面
     */
    private void loadIcon() {
        for (int i = 0; i < list.size(); i++) {
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    handler.sendEmptyMessageDelayed(finalI, finalI * 1000);
                }
            }).start();

        }
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            GlideApp.with(mContext)
                    .asBitmap()
                    .load(list.get(msg.what))
                    .placeholder(R.drawable.default_user_head_img)
                    .error(R.drawable.default_user_head_img)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .override(mIconSize, mIconSize)
                    .centerCrop()
                    .skipMemoryCache(false)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            if (resource != null && !resource.isRecycled()) {
                                mIconMap.put(String.valueOf(msg.what), resource);
                                invalidateSelf();
                            }
                        }
                    });
        }
    };

    @Override
    public int getIntrinsicHeight() {
        return mIconSize;
    }

    @Override
    public int getIntrinsicWidth() {
        int height = mIconSize;
        if (height > 0) {
            mIconSize = height;
        }
        int width = 0;
        if (list.size() >= 6) {
            width = mIconSize * mShowCompleteIconSize + mInterval * (mShowCompleteIconSize + 2);
        } else if (list.size() == 5) {
            width = mIconSize * mShowCompleteIconSize + mInterval * (mShowCompleteIconSize + 1);
        } else {
            width = (mIconSize + mInterval) * list.size() - mInterval;
        }
        return width;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        //先绘制被压着的两个

        dstRect.set(getBounds().left, getBounds().top, getBounds().left + mIconSize, getBounds().bottom);
        if (list.size() >= mShowCompleteIconSize + 2) {
            Bitmap s = mIconMap.get(String.valueOf(mShowCompleteIconSize + 1));
            if (s == null || s.isRecycled()) {
                s = defaultIcon;
            }
            canvas.save();
            canvas.translate(getBounds().width() - mIconSize, 0);
            mPaint.setShader(new BitmapShader(s, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            canvas.drawCircle(dstRect.centerX(), dstRect.centerY(), mIconSize / 2, mPaint);

            dstRect.offset(-mInterval, 0);
            Bitmap s5 = mIconMap.get(String.valueOf(mShowCompleteIconSize));
            if (s5 == null || s5.isRecycled()) {
                s5 = defaultIcon;
            }
            mPaint.setShader(new BitmapShader(s5, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            canvas.drawCircle(dstRect.centerX(), dstRect.centerY(), mIconSize / 2, mPaint);
            canvas.restore();
        } else if (list.size() == mShowCompleteIconSize + 1) {
            canvas.save();
            canvas.translate(getBounds().width() - mIconSize, 0);
            Bitmap s = mIconMap.get(String.valueOf(mShowCompleteIconSize));
            if (s == null || s.isRecycled()) {
                s = defaultIcon;
            }
            mPaint.setShader(new BitmapShader(s, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            canvas.drawCircle(dstRect.centerX(), dstRect.centerY(), mIconSize / 2, mPaint);
            canvas.restore();
        }


        dstRect.set(getBounds().left, getBounds().top, getBounds().left + mIconSize, getBounds().bottom);
        canvas.save();
        for (int i = 0; i < Math.min(mShowCompleteIconSize, list.size()); i++) {
            Bitmap s = mIconMap.get(String.valueOf(i));
            if (s == null || s.isRecycled()) {
                s = defaultIcon;
            }
            mPaint.setShader(new BitmapShader(s, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            canvas.drawCircle(dstRect.centerX(), dstRect.centerY(), mIconSize / 2, mPaint);
            canvas.translate((mIconSize + mInterval), 0);
        }
        canvas.restore();

    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }


    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        int height = bottom - top;
        if (height > 0) {
            mIconSize = height;
        }
        int width = 0;
        if (list.size() >= 6) {
            width = mIconSize * mShowCompleteIconSize + mInterval * (mShowCompleteIconSize + 2);
        } else if (list.size() == 5) {
            width = mIconSize * mShowCompleteIconSize + mInterval * (mShowCompleteIconSize + 1);
        } else {
            width = (mIconSize + mInterval) * list.size() - mInterval;
        }
        super.setBounds(left, top, left + width, top + mIconSize);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSPARENT;
    }

}
