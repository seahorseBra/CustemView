package com.example.administrator.custemview;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import javaBean.FlowInfo;
import utils.FlowDBManager;
import utils.SQLHelper;

public class SQLTestActivity extends AppCompatActivity {

    @Bind(R.id.insert)
    Button insert;
    @Bind(R.id.query)
    Button query;
    @Bind(R.id.update)
    Button update;
    @Bind(R.id.clear)
    Button clear;
    @Bind(R.id.content)
    TextView content;
    @Bind(R.id.key)
    EditText key;
    @Bind(R.id.seq)
    EditText seq;
    @Bind(R.id.page)
    EditText page;
    @Bind(R.id.contentT)
    EditText contentT;
    private FlowDBManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqltest);
        ButterKnife.bind(this);
        dbManager = new FlowDBManager(this);

    }

    @OnClick(R.id.insert)
    public void insert() {
        dbManager.insert(key.getText().toString(),
                Integer.parseInt(seq.getText().toString().isEmpty()?"1":seq.getText().toString()),
                Integer.parseInt(page.getText().toString().isEmpty()?"1":page.getText().toString()),
                System.currentTimeMillis()/1000,
                100,
                contentT.getText().toString());
    }

    @OnClick(R.id.query)
    public void query() {
        ArrayList<FlowInfo> query = null;
        if (page.getText().toString().isEmpty()) {
            query = (ArrayList<FlowInfo>) dbManager.query();
        } else {
            query = (ArrayList<FlowInfo>) dbManager.query(Integer.parseInt(page.getText().toString()), true);
        }
        if (query == null || query.isEmpty()) {
            content.setText("");
            return;
        }
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < query.size(); i++) {

            sb.append(query.get(i).content);
            sb.append("\n");
        }
        content.setText(sb.toString());
    }

    @OnClick(R.id.update)
    public void update() {
        dbManager.update(key.getText().toString(), 50);
    }

    @OnClick(R.id.clear)
    public void clear() {
//        dbManager.clearDate();
        dbManager.delete(System.currentTimeMillis());
    }
}
