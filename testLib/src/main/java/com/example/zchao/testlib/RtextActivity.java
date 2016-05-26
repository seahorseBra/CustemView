package com.example.zchao.testlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RtextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtext);
        TextView viewById = (TextView) findViewById(R.id.text223);
        viewById.setText(viewById.getId()+"");
    }
}
