package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
    protected OnItemClickListener clickListener;
    protected OnItemLongClickListener longClickListener;
    private int resID;
    public BaseAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
    }

    protected abstract V createView(ViewGroup parent);

    @Override
    public V onCreateViewHolder(ViewGroup parent, int viewType) {
        V view = createView(parent);
        return view;
    }

    @Override
    public void onBindViewHolder(V holder, final int position) {
        View itemView = holder.itemView;
        if (clickListener != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(position);
                }
            });
        }

        if (longClickListener != null) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    longClickListener.onItemLongClick(position);
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
        notifyDataSetChanged();
    }

    public void addDatas(List<T> list) {
        if (list == null) {
            this.list = list;
        } else {
            list.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void setData(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

}
