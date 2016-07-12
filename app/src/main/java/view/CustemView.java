package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zchao on 2016/6/30.
 */
public class CustemView extends View {
    public CustemView(Context context) {
        this(context, null);
    }

    public CustemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
