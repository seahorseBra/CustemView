package com.example.administrator.custemview;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;

import view.ImageStackDrawable;

public class ImageDrawActivity extends AppCompatActivity {

    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_draw);
        mImage = (ImageView) findViewById(R.id.img);
        ImageStackDrawable imageStackDrawable = new ImageStackDrawable(this);
        ArrayList<String> list = new ArrayList<>();
        list.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3502343973,1168279496&fm=11&gp=0.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506079577514&di=d11b51459c2bb8528000b0427fbd28a8&imgtype=0&src=http%3A%2F%2Fimg0.ph.126.net%2FXjXl3KcowmXdE1pcsFVe8g%3D%3D%2F1067353111787095545.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506079577514&di=61c7a9453105db85a9a6562340f83289&imgtype=0&src=http%3A%2F%2Fwww.laozhq.cn%2FUploadFile%2F2013-2%2F20132274451175515.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506079577513&di=e9365b3711c87d4d09efa2bd1c003e99&imgtype=0&src=http%3A%2F%2Fimg.daimg.com%2Fuploads%2Fallimg%2F170214%2F3-1F214233558.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506079577513&di=f943a6354adb4d12435067eba565d113&imgtype=0&src=http%3A%2F%2Fpic32.photophoto.cn%2F20140812%2F0035035784895932_b.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506079577509&di=138132430fff63d013e771cffe1e973f&imgtype=0&src=http%3A%2F%2Fd.image.i4.cn%2Fi4web%2Fimage%2Fupload%2F20170301%2F1488334069597013125.jpg");
        imageStackDrawable.setData(list);
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RectShape());
        shapeDrawable.setIntrinsicHeight(100);
        shapeDrawable.setIntrinsicWidth(100);
        mImage.setImageDrawable(imageStackDrawable);
    }
}
