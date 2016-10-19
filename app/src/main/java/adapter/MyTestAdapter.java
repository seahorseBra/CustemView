package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.custemview.R;

import java.util.List;

/**
 * Created by zchao on 2016/10/19.
 */

public class MyTestAdapter extends BaseAdapter<String, MyTestViewHolder> {
    public MyTestAdapter(Context context, List list) {
        super(context, list);
    }


    @Override
    protected MyTestViewHolder createView(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_test_item, parent, false);
        return new MyTestViewHolder(view);
    }
}
