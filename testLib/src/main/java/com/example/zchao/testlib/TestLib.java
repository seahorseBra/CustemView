package com.example.zchao.testlib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TestLib extends AppCompatActivity {


    private TextView viewById;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout2);
        viewById = ButterKnife.findById(this, R.id.text223);
        viewById.setText(viewById.getId()+"");
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TestLib.this, RtextActivity.class);
                startActivity(intent);
            }
        });
    }
}
