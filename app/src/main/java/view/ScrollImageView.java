package view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by zchao on 2016/3/31.
 */
public class ScrollImageView extends LinearLayout{

    public ScrollImageView(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean isMotionEventSplittingEnabled() {
        return super.isMotionEventSplittingEnabled();
    }
}
