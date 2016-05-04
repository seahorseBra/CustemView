package com.example.administrator.custemview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.TextView;

import Utils.DropDowmScollHelper;
import adapter.ListCustemAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

public class DropDownActivity extends AppCompatActivity {

    private static final String TAG = "DropDownActivity";
    @Bind(R.id.list_rv)
    RecyclerView mListRv;
    @Bind(R.id.title_tv)
    TextView mTitleTv;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_dowm);
        ButterKnife.bind(this);

        final ListCustemAdapter listCustemAdapter = new ListCustemAdapter(this);
        linearLayoutManager = new LinearLayoutManager(this);
        mListRv.setLayoutManager(linearLayoutManager);
        mListRv.setAdapter(listCustemAdapter);


    }


}
