package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zchao on 2016/10/18.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder{

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    protected abstract void bind(T data);
}
