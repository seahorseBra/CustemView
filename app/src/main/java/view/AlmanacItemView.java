package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.custemview.R;


/**
 * Created by zchao on 2016/3/17.
 * 自定义view，匹配黄历详情界面冲煞等
 */
public class AlmanacItemView extends LinearLayout {
    private static final String RANK_ORIENTION_VERTICAL = "vertical";
    private static final String RANK_ORIENTION_HORIZONTAL = "horizontal";
    private static final int MAXLINS_NOLIMIT = -1;
    private static final int IMAGE_NORES = -1;
    private ImageView mTitleImage;
    private TextView mContentTV;
    private float interval = 0;//图片与内容之间的间隔

    private int textColor = Color.WHITE;
    private int imageRes = IMAGE_NORES;
    private int maxLine = MAXLINS_NOLIMIT;
    private String rank_orientation = RANK_ORIENTION_HORIZONTAL;//图片与内容默认排列方向
    private String content_orientation = RANK_ORIENTION_HORIZONTAL;//文字内容默认排列方向
    public AlmanacItemView(Context context) {
        this(context, null);
    }

    public AlmanacItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        if (rank_orientation.equals(RANK_ORIENTION_VERTICAL)) {
            setOrientation(VERTICAL);
            setGravity(Gravity.CENTER_HORIZONTAL);
        } else {
            setOrientation(HORIZONTAL);
            setGravity(Gravity.CENTER_VERTICAL);
        }

        mTitleImage = new ImageView(context);
        mContentTV = new TextView(context);

        if (maxLine != MAXLINS_NOLIMIT) {
            mContentTV.setMaxLines(maxLine);
        }
        if (interval != 0) {
            if(rank_orientation == RANK_ORIENTION_HORIZONTAL)mContentTV.setPadding((int) interval, 0, 0, 0);
            if(rank_orientation == RANK_ORIENTION_VERTICAL)mContentTV.setPadding(0, (int) interval, 0, 0);
        }

        mContentTV.setGravity(Gravity.CENTER);
        mContentTV.setTextColor(textColor);

        LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams params1 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams params2 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams params3 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        if (content_orientation.equals(RANK_ORIENTION_VERTICAL)) {
            mContentTV.setSingleLine(false);
            mContentTV.setMaxEms(1);
        }
        addView(mTitleImage, params);
        addView(mContentTV, params);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.AlmanacItemView);
        rank_orientation = typedArray.getString(R.styleable.AlmanacItemView_rank_orientation);
        if (rank_orientation == null) {
            rank_orientation = RANK_ORIENTION_HORIZONTAL;
        }
        content_orientation = typedArray.getString(R.styleable.AlmanacItemView_content_orientation);
        if (content_orientation == null) {
            content_orientation = RANK_ORIENTION_HORIZONTAL;
        }
        textColor = typedArray.getColor(R.styleable.AlmanacItemView_contentTextColor, Color.WHITE);
        imageRes = typedArray.getResourceId(R.styleable.AlmanacItemView_imageRes, 0);
        maxLine = typedArray.getInteger(R.styleable.AlmanacItemView_maxLine, -1);
        interval = typedArray.getDimensionPixelSize(R.styleable.AlmanacItemView_interval, 0);
    }

    public void setDate(int imageRes, String content) {
        if (imageRes != IMAGE_NORES)mTitleImage.setImageResource(imageRes);
        if (!TextUtils.isEmpty(content)) {
            mContentTV.setText(content);
        }
    }
}
