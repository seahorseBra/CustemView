package littlegridview;

import android.content.Context;

/**
 * Created by zchao on 2017/3/22.
 * desc: 测算卡片列表适配器基类
 * version:
 */

public abstract class MeasureViewAdapter<T extends LittleGridView.LittleGridViewBaseHolder> {
    public Context mContext;
    private Refresh refresh;
    private boolean isOpen = false;

    public MeasureViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 在此方法中生成工具的holder，必须是{@link LittleGridView.LittleGridViewBaseHolder}的子类
     * @param context
     * @return
     */
    public abstract T onCreateView(Context context);

    /**
     * 此方法中返回默认显示view的个数
     * @return
     */
    public int getDefaultCount(){
        return getTotleCount();
    }

    /**
     * 返回总的工具个数
     * @return
     */
    public abstract int getTotleCount();

    /**
     * 设置每行工具个数
     * @return
     */
    public abstract int getSpanCount();

    public boolean isOpen(){
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
        notifyDataSetChanged();
    }

    /**
     * 绑定holder数据
     * @param holder
     * @param position
     */
    public abstract void onBindViewHolder(T holder, int position);

    /**
     * 刷新view，此处只是简单的用了回调接口，并没有使用观察者
     */
    public void notifyDataSetChanged(){
        if (refresh != null) {
            refresh.refreshView();
        }
    }

    public void setRefresh(Refresh refresh) {
        this.refresh = refresh;
    }

    interface Refresh{
        void refreshView();
    }
}
