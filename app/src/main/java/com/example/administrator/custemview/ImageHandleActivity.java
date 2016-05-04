package com.example.administrator.custemview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zchao on 2016/3/30.
 */
public class ImageHandleActivity extends AppCompatActivity {

    @Bind(R.id.account)
    EditText account;
    @Bind(R.id.img_nores)
    TextView imgNores;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.select_img)
    Button selectImg;
    @Bind(R.id.start_compress)
    Button startCompress;
    @Bind(R.id.save_img)
    Button saveImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_handle_activity);
        ButterKnife.bind(this);
    }
}
