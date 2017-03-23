package littlegridview;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by zchao on 2017/3/23.
 * desc:
 * version:
 */

public class LittleGridHolder extends FrameLayout implements MeasureViewAdapter.Refresh{
    MeasureViewAdapter adapter = null;
    private LittleGridView mLittleGridView;
    private double mViewHeight;

    public LittleGridHolder(@NonNull Context context) {
        this(context, null);
    }

    public LittleGridHolder(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LittleGridHolder(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLittleGridView = new LittleGridView(context, attrs);
        addView(mLittleGridView);
    }

    public void setAdapter(MeasureViewAdapter adapter) {
        this.adapter = adapter;
        adapter.setRefresh(this);
        if (mLittleGridView != null) {
            mLittleGridView.setAdapter(adapter);
        }
        int lines = adapter.getDefaultCount() / adapter.getSpanCount() + (adapter.getDefaultCount() % adapter.getSpanCount() == 0 ? 0 : 1);
        int lineAll = adapter.getTotleCount() / adapter.getSpanCount() + (adapter.getTotleCount() % adapter.getSpanCount() == 0 ? 0 : 1);

        startHeight = mLittleGridView.getHeight() * lines;
        endHeight = mLittleGridView.getHeight() * lineAll;
    }

    boolean isOpen = false;

    @Override
    public void refreshView() {
        if (!(isOpen ^ adapter.isOpen())){
            return;
        }
        int animatorS = 0;
        int animatorE = 0;
        if (isOpen) {
            animatorS = endHeight;
            animatorE = startHeight;
        } else {
            animatorE = endHeight;
            animatorS = startHeight;
        }
        startAnimator(animatorS, animatorE);
    }


    int startHeight = 0;
    int endHeight = 0;
    ValueAnimator mValueAnimator = null;

    private void startAnimator(final int startHeight, final int endHeight){
        if (startHeight == endHeight) {
            return;
        }
        if (mValueAnimator != null && mValueAnimator.isRunning()) {
            mValueAnimator.end();
        }
        mValueAnimator = ValueAnimator.ofFloat(0f, 1f);
        mValueAnimator.setDuration(500);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                if (value == null) {
                    return;
                }
                getLayoutParams().height = (int) ((endHeight - startHeight) * value + startHeight);
                requestLayout();
                int itemHeight = getHeight();
                if (getRootView() != null && mViewHeight == itemHeight && value > 0.5f) {
                    getRootView().requestLayout();
                }
                mViewHeight = itemHeight;
            }
        });
        mValueAnimator.start();
    }
}
