package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * RecyclerView的Adapter基类；
 * Created by zchao on 2016/10/18.
 */

public abstract class BaseAdapter<T, V extends BaseViewHolder> extends RecyclerView.Adapter<V> {
    protected Context context;
    protected List<T> list;
    protected OnItemClickLisenter clickLisenter;
    protected OnItemLongClickLisenter longClickLisenter;

    public BaseAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
    }

    protected abstract V createView(Context context);


    @Override
    public V onCreateViewHolder(ViewGroup parent, int viewType) {
        V view = createView(context);
        return view;
    }

    @Override
    public void onBindViewHolder(V holder, final int position) {
        View itemView = holder.itemView;
        if (clickLisenter != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickLisenter.onItemClick(position);
                }
            });
        }

        if (longClickLisenter != null) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    longClickLisenter.onItemLongClick(position);
                    return true;
                }
            });
        }
        holder.bind(list.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public void addData(T t) {
        if (list != null) {
            list.add(t);
        }
    }

    public void addDatas(List<T> list) {
        if (list == null) {
            this.list = list;
        } else {
            list.addAll(list);
        }
    }

    public void setData(List<T> list) {
        this.list = list;
    }

    public void setClickLisenter(OnItemClickLisenter clickLisenter) {
        this.clickLisenter = clickLisenter;
    }

    public void setLongClickLisenter(OnItemLongClickLisenter longClickLisenter) {
        this.longClickLisenter = longClickLisenter;
    }

    public interface OnItemClickLisenter {
        void onItemClick(int postion);
    }

    public interface OnItemLongClickLisenter {
        void onItemLongClick(int position);
    }

}
