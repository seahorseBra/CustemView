package com.example.administrator.custemview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import littlegridview.LittleGridHolder;
import littlegridview.LittleGridView;
import littlegridview.TestGridAdapter;


public class LittleGridViewActivity extends AppCompatActivity {

    private Button mBtn;
    private LittleGridView mGrid;
    private TestGridAdapter mAdapter;
    boolean isOpen = false;
    public static final ArrayList<String> mList = new ArrayList<>();
    private LinearLayout mRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_little_grid_view);
        mBtn = (Button) findViewById(R.id.btn);
        mGrid = (LittleGridView) findViewById(R.id.grid);
        mRoot = (LinearLayout) findViewById(R.id.root);
        mAdapter = new TestGridAdapter(this);
        mGrid.setAdapter(mAdapter);

        mList.add("阿文哥1");
        mList.add("阿文哥2");
        mList.add("阿文哥3");
        mList.add("阿文哥4");
        mList.add("阿文哥5");
        mList.add("阿文哥6");
        mList.add("阿文哥7");
        mList.add("阿文哥8");

        mAdapter.setmList(mList);
        initTransition();
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpen = !isOpen;
                mAdapter.setOpen(isOpen);
                mBtn.setText(isOpen ? "折叠" : "展开");
            }
        });
    }


    private void initTransition() {
        mLayoutTransition = new LayoutTransition();
        mRoot.setLayoutTransition(mLayoutTransition);
        mLayoutTransition.setStagger(LayoutTransition.CHANGE_APPEARING, 30);
        mLayoutTransition.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 30);
        //设置每个动画持续的时间
        mLayoutTransition.setDuration(300);
        //初始化自定义的动画效果
        customLayoutTransition();
    }


    private LayoutTransition mLayoutTransition;
    public void customLayoutTransition(){

        /**
         * Add Button
         * LayoutTransition.APPEARING
         * 增加一个Button时，设置该Button的动画效果
         */
        ObjectAnimator mAnimatorAppearing = ObjectAnimator.ofFloat(null, "translationY", 0f ,1.0f)
                .setDuration(mLayoutTransition.getDuration(LayoutTransition.APPEARING));
        ObjectAnimator mAnimatorAppearing1 = ObjectAnimator.ofFloat(null, "scaleY", 1.0f ,1.0f)
                .setDuration(mLayoutTransition.getDuration(LayoutTransition.APPEARING));
        //为LayoutTransition设置动画及动画类型
//        mLayoutTransition.setAnimator(LayoutTransition.APPEARING, mAnimatorAppearing);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(mAnimatorAppearing1, mAnimatorAppearing1);
        mLayoutTransition.setAnimator(LayoutTransition.APPEARING, mAnimatorAppearing1);

        /**
         * Delete Button
         * LayoutTransition.DISAPPEARING
         * 当删除一个Button时，设置该Button的动画效果
         */
        ObjectAnimator mObjectAnimatorDisAppearing = ObjectAnimator.ofFloat(null, "rotationX", 0.0f,90.0f)
                .setDuration(mLayoutTransition.getDuration(LayoutTransition.DISAPPEARING));
        mLayoutTransition.setAnimator(LayoutTransition.DISAPPEARING, mObjectAnimatorDisAppearing);
        mObjectAnimatorDisAppearing.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO Auto-generated method stub
                super.onAnimationEnd(animation);
                View view = (View) ((ObjectAnimator) animation).getTarget();
                view.setRotationX(0.0f);
            }
        });
    }

}
