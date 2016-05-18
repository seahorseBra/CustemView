package com.example.administrator.custemview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.PointF;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

/**
 * 动画
 */
public class AnimationActivity extends BaseActivity {

    private static final String TAG = "AnimationActivity";
    private ImageView mImage;
    private ImageView mCenterIV;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Slide());
            getWindow().setExitTransition(new Slide());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        mImage = (ImageView)findViewById(R.id.activity_animation_iv);
        mCenterIV = (ImageView) findViewById(R.id.center_line);


        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                rotateyAnimRun(mImage);
//                paowuxian(mImage);
                transViewAnimator(mImage);
            }
        });
    }

    /**
     * ValueAnimator高级应用
     * @param view
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void transViewAnimator(final View view){
        view.setClickable(false);
        final float y1 = view.getY();
//        Log.d(TAG, "transViewAnimator() called with: " + "y1 = [" + y1 + "]");
        ValueAnimator anim = new ValueAnimator();
        anim.setDuration(5000);
        anim.setObjectValues(new PointF(0, y1));
        anim.setInterpolator(new LinearInterpolator());
        anim.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                PointF point = new PointF();
                Log.d(TAG, "evaluate() called with: " + "fraction = [" + fraction + "], startValue = [" + startValue + "], endValue = [" + endValue + "]");
                point.x = fraction * 600;
                point.y = (float) (Math.sin(4 * Math.PI * fraction) * 100) + y1;
//                point.y = (y1 > 0) ?  y1 - fraction * 200 : fraction * 200;
                return point;
            }
        });
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF f = (PointF) animation.getAnimatedValue();
                view.setX(f.x);
                view.setY(f.y);
//                Log.d(TAG, "onAnimationUpdate() called with: " + "animation = [" + view.getY() + "]" + f.x +"|" + f.y + "|" + (float) Math.sin(f.x));
            }
        });
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setClickable(true);
            }
        });
    }

    /**
     * ObjectAnimator翻转动画
     * @param view
     */
    public void rotateyAnimRun(View view)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ObjectAnimator
                    .ofFloat(view, "rotationX", 0.0F, 180.0F, 720f)
                    .setDuration(3000)
                    .start();
        }
    }

    private Animation getAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, mImage.getTop(), mImage.getTop() == 0 ? mCenterIV.getTop() - mImage.getHeight() : 0);
        translateAnimation.setDuration(1000);
        translateAnimation.setFillAfter(true);
        translateAnimation.setInterpolator(new AccelerateInterpolator());
        return translateAnimation;
    }


    /**
     *抛物线动画
     */
    public void paowuxian(final View view){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            ValueAnimator valueAnimator = new ValueAnimator();
            valueAnimator.setDuration(1000);
            valueAnimator.setObjectValues(new PointF(0, 0));
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
                // fraction = t / duration
                @Override
                public PointF evaluate(float fraction, PointF startValue,
                                       PointF endValue) {
                    // x方向200px/s ，则y方向0.5 * 10 * t
                    PointF point = new PointF();
                    point.x = 20 * fraction * 3;
                    point.y = Math.min(0.5f * 20 * (fraction * 3) * (fraction * 3), mCenterIV.getTop() - mImage.getTop());
                    return point;
                }
            });


            valueAnimator.start();
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
            {
                @Override
                public void onAnimationUpdate(ValueAnimator animation)
                {
                    Log.d(TAG, "onAnimationUpdate() called with: " + "animation = [" + animation + "]");
                    PointF point = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                        point = (PointF) animation.getAnimatedValue();
                        view.setX(point.x);
                        view.setY(point.y);
                        Log.d(TAG, "onAnimationUpdate() called with: " + "getY = [" + view.getY() + "]" + view.getTop());
                    }

                }
            });
        }
    }
}
