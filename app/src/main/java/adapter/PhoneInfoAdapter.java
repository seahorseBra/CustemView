package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.custemview.PhoneDetailInfoActivity;
import com.example.administrator.custemview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zchao on 2016/5/17.
 */
public class PhoneInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<PhoneDetailInfoActivity.PhoneInfo> info = new ArrayList();
    private String product = "未知设备";
    private final LayoutInflater inflater;

    public PhoneInfoAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void addInfo(List<PhoneDetailInfoActivity.PhoneInfo> info){
        this.info.addAll(info);
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == 0) {
//            return new HeadViewHolder(inflater.inflate(R.layout.phone_detail_head, parent, false));
//        } else if (viewType == 1) {
            return new DetailHolder(inflater.inflate(R.layout.item_phone_info_detail, parent, false));
//        }
//        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (getItemViewType(position) == 0) {
//            ((HeadViewHolder)holder).mProduct.setText(product);
//        }else {
            ((DetailHolder) holder).mName.setText(info.get(position).name);
            ((DetailHolder) holder).mInfo.setText(info.get(position).info);
//        }
    }

    @Override
    public int getItemCount() {
        return info.size();
    }

    @Override
    public int getItemViewType(int position) {
//        if (position == 0) {
//            return 0;
//        }else
        return 1;
    }

    class DetailHolder extends RecyclerView.ViewHolder {
        TextView mName, mInfo;
        public DetailHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.item_phone_info_detail_name);
            mInfo = (TextView) itemView.findViewById(R.id.item_phone_info_detail_info);
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {
        TextView mProduct;
        public HeadViewHolder(View itemView) {
            super(itemView);
            mProduct = (TextView) itemView.findViewById(R.id.phone_product);
        }
    }
}
