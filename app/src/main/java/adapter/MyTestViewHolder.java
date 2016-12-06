package adapter;

import android.view.View;
import android.widget.TextView;

import com.example.administrator.custemview.R;

/**
 * Created by zchao on 2016/10/19.
 */

public class MyTestViewHolder extends BaseViewHolder<String> {

    private final TextView mText;

    public MyTestViewHolder(View itemView) {
        super(itemView);
        mText = (TextView) itemView.findViewById(R.id.text);
    }

    @Override
    protected void bind(String data) {
        mText.setText(data);
    }
}
