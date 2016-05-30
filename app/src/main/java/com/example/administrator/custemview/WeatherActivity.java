package com.example.administrator.custemview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

public class WeatherActivity extends AppCompatActivity {

    private RecyclerView mRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        mRv = (RecyclerView)  findViewById(R.id.rv);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRv.setLayoutManager(manager);
        mRv.setAdapter(new MyAdapter(this));

    }


    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private Context context;
        private final LayoutInflater inflater;

        public MyAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;
            if (viewType == 0) {
                view = inflater.inflate(R.layout.item_weather, parent, false);
                return new FirstViewHolder(view);
            } else {
                view = inflater.inflate(R.layout.item_weather_nomal, parent, false);
                return new OtherViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 20;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return 0;
            } else {
                return 1;
            }
        }

        class FirstViewHolder extends RecyclerView.ViewHolder {
            public FirstViewHolder(View itemView) {
                super(itemView);
            }
        }

        class OtherViewHolder extends RecyclerView.ViewHolder {
            public OtherViewHolder(View itemView) {
                super(itemView);
            }
        }

    }
}
