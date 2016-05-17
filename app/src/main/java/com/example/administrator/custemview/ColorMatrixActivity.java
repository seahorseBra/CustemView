package com.example.administrator.custemview;

import android.graphics.ColorMatrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * 颜色矩阵练习
 */
public class ColorMatrixActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_matrix);

        ColorMatrix matrix = new ColorMatrix();
    }
}
