package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.custemview.R;

/**
 * Created by zchao on 2016/10/19.
 */

public class Agegaweg extends RecyclerView.Adapter<Agegaweg.CViewHolder> {
    private Context context;

    public Agegaweg(Context context) {
        this.context = context;
    }

    @Override
    public CViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_clock, parent, false);
        return new CViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class CViewHolder extends RecyclerView.ViewHolder {

        public CViewHolder(View itemView) {
            super(itemView);
        }
    }

}
