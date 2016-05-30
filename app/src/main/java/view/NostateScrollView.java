package view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by zchao on 2016/5/30.
 */
public class NostateScrollView extends ScrollView {
    public NostateScrollView(Context context) {
        this(context, null);
    }

    public NostateScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NostateScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
