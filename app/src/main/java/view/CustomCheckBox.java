package view;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.custemview.R;

/**
 * Created by zchao on 2016/5/5.
 */
public class CustomCheckBox extends RelativeLayout {


    private final ImageView mImage;
    public CustomCheckBox(final Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.custem_checkbox, this);
        mImage = (ImageView) findViewById(R.id.check);
        findViewById(R.id.root).setOnClickListener(new OnClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                int visibil = mImage.getVisibility();
                if (visibil == VISIBLE) {
                    mImage.setAnimation(AnimationUtils.loadAnimation(context, R.anim.check_miss_anim));

                    mImage.setVisibility(INVISIBLE);
                } else if (visibil == INVISIBLE) {
                    mImage.setAnimation(AnimationUtils.loadAnimation(context, R.anim.check_show_anim));
                    mImage.setVisibility(VISIBLE);
                }
            }
        });
    }


    public boolean isChecked() {
        return mImage.getVisibility() == VISIBLE ? true : false;
    }

    public void setChecked(boolean checked){
        if (checked) {
            mImage.setVisibility(VISIBLE);
        }else {
            mImage.setVisibility(INVISIBLE);
        }
    }
}
