package view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.custemview.ClockActivity;
import com.example.administrator.custemview.R;

/**
 * Created by zchao on 2016/11/1.
 */

public class CustemDialog extends Dialog {
    private Context context;
    public CustemDialog(Context context) {
        super(context, R.style.Dialog);
        this.context = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        addContentView(inflate, params);
    }

}
