import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;

/**
 * Created by zchao on 2016/6/3.
 */
public class ImageAnimation extends Animation {
    private View view;
    private long duration;
    public ImageAnimation() {

    }

    public ImageAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

}
