package adapter;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zchao on 2016/10/18.
 */

public abstract class HeaderAndFooterAdapter<T, V extends BaseViewHolder> extends BaseAdapter<T, V> {
    private static final int TYPE_HEADER = -1;
    private static final int TYPE_FOOTER = -2;

    public HeaderAndFooterAdapter(Context context, List list) {
        super(context, list);
    }

    protected boolean isHeaderEnabled() {
        return false;
    }

    protected boolean isFooterEnabled(){
        return false;
    }

    @Override
    protected abstract V createView(ViewGroup parent);

    protected abstract V createHeader (Context context);

    protected abstract V createFooter(Context context);

    @Override
    public V onCreateViewHolder(ViewGroup parent, int viewType) throws IllegalStateException{
        if (viewType == TYPE_HEADER) {
            V header = createHeader(context);
            if (header != null) {
                return header;
            } else {
                throw new IllegalStateException("if you make isHeaderEnabled() return true, the createHeader() return could not be null");
            }
        }
        if (viewType == TYPE_FOOTER) {
            V footer = createFooter(context);
            if (footer != null) {
                return footer;
            } else {
                throw new IllegalStateException("if you make isFooterEnabled() return true, the createFooter() return could not be null");
            }
        }
        return super.onCreateViewHolder(parent, viewType);
    }


    @Override
    public int getItemCount() {
        int count = 0;
        if (list != null) {
            count += list.size();
        }
        if (isFooterEnabled()) {
            count += 1;
        }
        if (isHeaderEnabled()) {
            count += 1;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && isHeaderEnabled()) {
            return TYPE_HEADER;
        }
        if (position == getItemCount() && isFooterEnabled()) {
            return TYPE_FOOTER;
        }

        return super.getItemViewType(position);
    }
}
