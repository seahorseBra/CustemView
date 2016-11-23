package com.example.administrator.custemview;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import java.io.File;

public class ScreenShotActivity extends AppCompatActivity {

    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_shot);
        mImage = (ImageView) findViewById(R.id.image);
        Intent intent = getIntent();
        String path = intent.getStringExtra("PATH");
        if(!TextUtils.isEmpty(path)){
            File file = new File(path);
            if (file.exists() && file.isFile()) {
                mImage.setImageURI(Uri.fromFile(file));
            }
        }

    }
}
