package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by zchao on 2016/10/19.
 */

public class BaseCardItemView<VH, V extends BaseViewHolder<VH>> {
    private Context context;
    private int resID;

    public BaseCardItemView(Context context) {
        this.context = context;
    }

    public V createHolder(int resID) {
        this.resID = resID;
        View itemview = LayoutInflater.from(context).inflate(resID, null);
        BaseViewHolder<Object> viewHolder = new BaseViewHolder<>(itemview);
        return (V) viewHolder;
    }

    public void bindData(VH data) {

    }
}
