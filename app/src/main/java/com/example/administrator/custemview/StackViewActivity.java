package com.example.administrator.custemview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.StackView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class StackViewActivity extends AppCompatActivity {

    private StackView stackView;
    private int[] imageIds = {R.drawable.item_bg,R.drawable.index,R.drawable.item_bg,R.drawable.index};
    private List<Integer> images = new ArrayList<>();
    private ImageAdapter imageAdapter;
    private TextView textView;
    private Timer down;
    private Timer timerup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack_view);
        stackView = (StackView) findViewById(R.id.stackview);
        textView = (TextView) findViewById(R.id.textview);
        initData();
        imageAdapter = new ImageAdapter(images, this);
        stackView.setAdapter(imageAdapter);
        stackView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText("第"+(position+1)+"个杨幂");
            }
        });
    }
    public void initData(){
        for (int i = 0; i < imageIds.length; i++) {
            images.add(imageIds[i]);
        }
    }
    public void click(View view){
        switch (view.getId()){
            case R.id.btn_down:
                if(timerup!=null){
                    timerup.cancel();
                }
                down = new Timer();
                down.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                stackView.showNext();
                            }
                        });
                    }
                },0,1000);
                break;
            case R.id.btn_up:
                if(down!=null){
                    down.cancel();
                }
                timerup = new Timer();
                timerup.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                stackView.showPrevious();
                            }
                        });
                    }
                },0,1000);
                break;
        }
    }

    class ImageAdapter extends BaseAdapter {
        private List<Integer> mImages;
        private Context mContext;
        public ImageAdapter(List<Integer> mImages,Context context){
            this.mImages = mImages;
            mContext = context;
        }
        @Override
        public int getCount() {
            return mImages.size();
        }

        @Override
        public Object getItem(int position) {
            return mImages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(mImages.get(position));
            return imageView;
        }
    }
}
