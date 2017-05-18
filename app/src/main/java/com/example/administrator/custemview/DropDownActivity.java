package com.example.administrator.custemview;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import view.AutoCarouselView;

public class DropDownActivity extends BaseActivity {

    private static final String TAG = "DropDownActivity";
    @Bind(R.id.auto_carousel_view)
    AutoCarouselView mSwitch;

    private int screenNo = -1;
    private int screenNum;
    private int[] img_ids = new int[]{R.drawable.flower, R.drawable.index,
            R.drawable.item_bg};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_dowm);
        ButterKnife.bind(this);
        screenNum = img_ids.length;

        mSwitch.setAdapter(new AutoCarouselView.CarouselViewAdapter() {
            @Override
            public void bindView(View view) {
                if (screenNo == screenNum - 1) {
                    screenNo = -1;
                }
                if (screenNo < screenNum - 1) {
                    screenNo++;
                    ImageView imgView = (ImageView) view.findViewById(R.id.img);
                    imgView.setImageResource(img_ids[screenNo]);
                }
            }

            @Override
            public int getViewResId() {
                return R.layout.switcher_layout;
            }
        });

        mSwitch.startCarousel();
    }

}
