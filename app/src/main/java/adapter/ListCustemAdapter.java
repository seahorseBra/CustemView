package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.custemview.R;

/**
 * Created by zchao on 2016/5/3.
 */
public class ListCustemAdapter extends RecyclerView.Adapter<ListCustemAdapter.ListViewholder> {
    private Context context;
    private String titleArray[] = {"围观", "很让人", "今天又", "为很高", "王国维", "很让人", "今天又", "为很高", "王国维"};
    private  LayoutInflater layoutInflater;


    public ListCustemAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ListViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListViewholder(layoutInflater.inflate(R.layout.item_listview, parent, false));
    }

    @Override
    public void onBindViewHolder(ListViewholder holder, int position) {
        holder.title.setText(titleArray[position]);
    }

    @Override
    public int getItemCount() {
        return titleArray.length;
    }

    class ListViewholder extends RecyclerView.ViewHolder {

        TextView title;
        private final ImageView image;

        public ListViewholder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_title);
            image = (ImageView) itemView.findViewById(R.id.item_image);
        }
    }
}
