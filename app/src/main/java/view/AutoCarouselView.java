package view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ViewSwitcher;

import com.example.administrator.custemview.R;

/**
 * Created by zchao on 2017/5/11.
 * desc:
 * version:
 */

public class AutoCarouselView extends ViewSwitcher {
    private int mDelayTime = 5000;               //轮播延迟时间
    private boolean mStart = false;              //是否开始轮播
    private static final int TO_NEXT = 1;        //到下一个页面
    private static final int TO_LAST = 2;        //到上一个页面
    private int whereToGo = TO_NEXT;
    private CarouselViewAdapter mAdapter = null;

    public AutoCarouselView(Context context) {
        this(context, null);

    }

    public AutoCarouselView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setInAnimation(context,
                R.anim.slide_in_left);
        setOutAnimation(context,
                R.anim.slide_out_left);
    }


    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (mStart) {
                handleCarousel(msg.what);
                sendEmptyMessageDelayed(msg.what, mDelayTime);
            }
        }
    };

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeMessages(TO_LAST);
        mHandler.removeMessages(TO_NEXT);
    }

    /**
     * 设置适配器
     * @param mAdapter
     */
    public void setAdapter(final CarouselViewAdapter mAdapter) {
        this.mAdapter = mAdapter;
        ViewFactory factory = new ViewFactory() {
            @Override
            public View makeView() {
                return LayoutInflater.from(getContext()).inflate(mAdapter.getViewResId(), null);
            }
        };
        if (getChildCount() != 0) {
            removeAllViews();
        }
        setFactory(factory);
    }

    /**
     * 开始轮播
     */
    public void startCarousel() {
        mStart = true;
        mHandler.sendEmptyMessage(whereToGo);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    /**
     * 处理轮播逻辑
     *
     * @param goWhere 跳转到哪一个
     */
    private void handleCarousel(int goWhere) {
        if (mAdapter == null) {
            throw new RuntimeException("you must set a CarouselViewAdapter for AutoCarouselView");
        }

        if (goWhere == TO_NEXT) {
            View nextView = getNextView();
            if (nextView != null) {
                mAdapter.bindView(nextView);
            }
            showNext();
        } else if (goWhere == TO_LAST) {
            showPrevious();
        }
    }


    public void setDelayTime(int mDelayTime) {
        this.mDelayTime = mDelayTime;
    }

    public int getDelayTime() {
        return mDelayTime;
    }


    public abstract static class CarouselViewAdapter {

        public abstract void bindView(View view);

        public abstract int getViewResId();
    }
}
