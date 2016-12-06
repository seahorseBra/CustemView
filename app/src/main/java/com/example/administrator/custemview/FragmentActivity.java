package com.example.administrator.custemview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import fragment.BaseFragment;

public class FragmentActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.content)
    ViewPager content;
    @Bind(R.id.button_one)
    Button buttonOne;
    @Bind(R.id.button_second)
    Button buttonSecond;
    @Bind(R.id.button_third)
    Button buttonThird;
    @Bind(R.id.button_fourth)
    Button buttonFourth;
    private FragmentManager fm;
    private HashMap<Integer, Fragment> listFragment = new HashMap<>();
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        ButterKnife.bind(this);
        fm = getSupportFragmentManager();
        initePager();

        buttonOne.setOnClickListener(this);
        buttonSecond.setOnClickListener(this);
        buttonThird.setOnClickListener(this);
        buttonFourth.setOnClickListener(this);
    }

    private void initePager() {
        adapter = new MyPagerAdapter(fm);
        content.setAdapter(adapter);
        content.setOffscreenPageLimit(0);
        addallFragment();
        adapter.addFragment(fragmentList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_one:
                content.setCurrentItem(0, true);
                break;
            case R.id.button_second:
                content.setCurrentItem(1, true);
                break;
            case R.id.button_third:
                content.setCurrentItem(2, true);
                break;
            case R.id.button_fourth:
                content.setCurrentItem(3, true);
                break;

        }
    }


    class MyPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> fragments = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        public void addFragment(ArrayList<Fragment> fragments) {
            if (fragments == null || fragments.size() == 0) {
                return;
            }
            this.fragments.clear();
            this.fragments.addAll(fragments);
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }



    private void addallFragment() {
        for (int i = 0; i < 4; i++) {
            BaseFragment baseFragment = new BaseFragment();
            Bundle bundle = new Bundle();
            bundle.putString("word", String.format("这是第%d个页面", i + 1 ));
            baseFragment.setArguments(bundle);
//            listFragment.put(i, baseFragment);
            fragmentList.add(baseFragment);
//            fm.beginTransaction().add(R.id.content, baseFragment).commit();
        }
    }

    private void showFragment(int position) {
        hideAll();
        FragmentTransaction ft = fm.beginTransaction();
        ft.show(listFragment.get(position));
        ft.commit();
    }

    private void hideAll() {
        for (int i = 0; i < listFragment.size(); i++) {
            fm.beginTransaction().hide(listFragment.get(i)).commit();
        }
    }
}
