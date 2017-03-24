package littlegridview;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.custemview.R;

import java.util.ArrayList;

/**
 * Created by zchao on 2017/3/24.
 * desc:
 * version:
 */

public class TestGridAdapter extends MeasureViewAdapter<TestGridAdapter.MyHolder>{
    ArrayList<String> mList = new ArrayList<>();
    public TestGridAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public MyHolder onCreateView(Context context) {
        return new MyHolder(context);
    }

    @Override
    public int getTotleCount() {
        return mList.size();
    }

    @Override
    public int getSpanCount() {
        return 2;
    }

    @Override
    public int getDefaultCount() {
        if (isOpen()) {
            return getTotleCount();
        } else {
            return 4;
        }
    }

    public void setmList(ArrayList<String> list) {
        if (list == null ) {
            return;
        }
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.bindView(mList.get(position));
    }

    public class MyHolder extends LittleGridView.LittleGridViewBaseHolder<String>{

        private TextView mText;

        public MyHolder(Context context) {
            super(context);
            mText = (TextView) getItemView().findViewById(R.id.text);
        }

        @Override
        public int getLayoutRes() {
            return R.layout.little_grid_item;
        }

        @Override
        public void bindView(String tool) {
            if (!TextUtils.isEmpty(tool)) {
                mText.setText(tool);
            }
        }
    }
}
