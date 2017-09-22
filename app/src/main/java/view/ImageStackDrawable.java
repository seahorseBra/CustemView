package view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.Request;
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
 * desc:
 * version:
 */

public class ImageStackDrawable extends Drawable {
    private final Context mContext;
    private final Paint mPaint;
    ArrayList<String> list = new ArrayList<>();
    HashMap<String, Bitmap> mIconMap = new HashMap<>();
    private int mShowCompleteIconSize = 4;
    private int mIconSize = (int) Utils.dp2Px(20);
    private int mInterval = (int) Utils.dp2Px(5);
    private RectF dstRect = new RectF();
    private RectF srcRect = new RectF();
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
            GlideApp.with(mContext)
                    .asBitmap()
                    .load(list.get(i))
                    .placeholder(R.drawable.default_user_head_img)
                    .error(R.drawable.default_user_head_img)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(false)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            if (resource != null && !resource.isRecycled()) {
                                mIconMap.put(String.valueOf(finalI), resource);
                                invalidateSelf();
                            }
                        }
                    });
        }

    }

    @Override
    public int getIntrinsicHeight() {
        return getBounds().height();
    }

    @Override
    public int getIntrinsicWidth() {
        return getBounds().width();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        //先绘制被压着的两个
        if (list.size() >= mShowCompleteIconSize + 2) {
            Bitmap s = mIconMap.get(String.valueOf(mShowCompleteIconSize + 2));
            if (s == null || s.isRecycled()) {
                s = defaultIcon;
            }
            dstRect.set(getBounds().right - mIconSize, getBounds().top, getBounds().right, getBounds().bottom);
            canvas.drawBitmap(s, null, dstRect, mPaint);

            dstRect.offset(-mInterval, 0);
            Bitmap s5 = mIconMap.get(String.valueOf(mShowCompleteIconSize + 1));
            if (s5 == null || s5.isRecycled()) {
                s5 = defaultIcon;
            }
            canvas.drawBitmap(s5, null, dstRect, mPaint);
        } else if (list.size() == mShowCompleteIconSize + 1) {
            Bitmap s = mIconMap.get(String.valueOf(mShowCompleteIconSize + 1));
            if (s == null || s.isRecycled()) {
                s = defaultIcon;
            }
            dstRect.set(getBounds().right - mIconSize, getBounds().top, getBounds().right, getBounds().bottom);
            canvas.drawBitmap(s, null, dstRect, mPaint);
        }


        dstRect.set(getBounds().left, getBounds().top, getBounds().left + mIconSize, getBounds().bottom);
        for (int i = 0; i < Math.min(mShowCompleteIconSize, list.size()); i++) {
            Bitmap s = mIconMap.get(String.valueOf(i));
            if (s == null || s.isRecycled()) {
                s = defaultIcon;
            }
            canvas.drawBitmap(s, null, dstRect, mPaint);
            dstRect.offset((mIconSize + mInterval), 0);
        }

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
