package com.example.administrator.custemview;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import adapter.MyTestAdapter;


public class FileManagerActivity extends BaseActivity {

    private RecyclerView mFileList;
    private MyTestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_manager);
        mFileList = (RecyclerView) findViewById(R.id.file_system_list);
        inite();
    }

    private void inite() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        ArrayList<String> list = new ArrayList<String>();
        list.add("egaweg");
        list.add("egaweg");
        list.add("egaweg");
        list.add("egaweg");
        list.add("egaweg");
        list.add("egaweg");
        list.add("egaweg");
        list.add("egaweg");
        list.add("egaweg");
        list.add("egaweg");
        list.add("egaweg");
        list.add("egaweg");
        mFileList.setLayoutManager(manager);
        adapter = new MyTestAdapter(this, list);
        mFileList.setAdapter(adapter);
    }

}
